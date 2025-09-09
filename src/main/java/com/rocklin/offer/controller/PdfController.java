package com.rocklin.offer.controller;

import cn.hutool.core.lang.UUID;
import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.PageAccessLimit;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.utils.PdfToImageUtil;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.service.MaterialService;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.rocklin.offer.common.constants.Constants.*;

@Tag(name = "PDF操作", description = "PDF操作接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf")
public class PdfController {

    private final UserService userService;
    private final MaterialService materialService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Operation(summary = "上传 PDF 文件", description = "上传 PDF 文件")
    @PostMapping("/upload")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public ResponseEntity<String> uploadPdf(@RequestParam(FILE) MultipartFile file, @RequestParam String category,
                                           @RequestParam(defaultValue = "120") int dpi,
                                           @RequestParam(defaultValue = "0.7") float quality) {
        try {
            if (!file.getOriginalFilename().endsWith(PDF_SUFFIX)) {
                return ResponseEntity.badRequest().body("只允许上传 PDF 文件");
            }

            // 生成 bookId
            String bookId = UUID.randomUUID().toString();

            // 处理 uploadDir（支持相对路径 & 绝对路径）
            File baseDir = new File(uploadDir);
            if (!baseDir.isAbsolute()) {
                baseDir = new File(System.getProperty(USER_DIR), uploadDir);
            }

            File bookDir = new File(baseDir, bookId);
            if (!bookDir.exists()) {
                bookDir.mkdirs();
            }

            // 保存原始 PDF
            File pdfFile = new File(bookDir, ORIGIN_PDF);
            file.transferTo(pdfFile);

            // 转换 PDF → 图片，并获取总页数
            int totalPages = PdfToImageUtil.convert(pdfFile, bookDir, dpi, quality);

            // 转换完成后删除原始 PDF
            if (pdfFile.exists()) {
                pdfFile.delete();
            }

            // 处理文件名：去掉后缀
            String fileName = file.getOriginalFilename();
            if (fileName != null && fileName.contains(DOT)) {
                fileName = fileName.substring(0, fileName.lastIndexOf(DOT));
            }
            
            // 计算转换后图片的总大小（MB）
            long totalImageSize = 0;
            File[] imageFiles = bookDir.listFiles((dir, name) -> name.endsWith(PDF_PAGE_SUFFIX));
            if (imageFiles != null) {
                for (File imageFile : imageFiles) {
                    totalImageSize += imageFile.length();
                }
            }
            Long fileSizeMB = totalImageSize;
            
            // 向material表插入数据
            materialService.addMaterial(bookId, fileName, category, fileSizeMB, totalPages);

            return ResponseEntity.ok("上传成功，bookId=" + bookId);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("上传失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取 PDF 页面", description = "获取指定页面的图片")
    @GetMapping("/preview/{bookId}/page/{pageNum}")
    @PageAccessLimit(maxPageForUser = 6, maxPageForAnonymous = 3)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 1000)
    public ResponseEntity<FileSystemResource> getPage(@PathVariable String bookId, @PathVariable int pageNum) {
        // 处理 uploadDir（支持相对路径 & 绝对路径）
        File baseDir = new File(uploadDir);
        if (!baseDir.isAbsolute()) {
            baseDir = new File(System.getProperty(USER_DIR), uploadDir);
        }

        File bookDir = new File(baseDir, bookId);
        File file = new File(bookDir, PDF_PAGE_PREFIX + pageNum + PDF_PAGE_SUFFIX);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }
        materialService.incrementViewCount(bookId);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // 返回图片
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, CONTENT_DISPOSITION + file.getName())
                // 缓存3天
                .cacheControl(CacheControl.maxAge(3, TimeUnit.DAYS).cachePrivate())
                // 支持 304 Not Modified
                .lastModified(file.lastModified())
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(file));

    }
}
