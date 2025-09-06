package com.rocklin.offer.controller;

import cn.hutool.core.lang.UUID;
import com.rocklin.offer.common.annotation.AuthCheck;
import com.rocklin.offer.common.annotation.SlidingWindowRateLimit;
import com.rocklin.offer.common.enums.UserRoleEnum;
import com.rocklin.offer.common.utils.PdfToImageUtil;
import com.rocklin.offer.model.dto.response.UserLoginResponse;
import com.rocklin.offer.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static com.rocklin.offer.common.constants.Constants.*;

@Tag(name = "PDF操作", description = "PDF操作接口")
@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf")
public class PdfController {

    private final UserService userService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Operation(summary = "上传 PDF 文件", description = "上传 PDF 文件")
    @PostMapping("/upload")
    @AuthCheck(enableRole = UserRoleEnum.ADMIN)
    @SlidingWindowRateLimit(windowInSeconds = 10, maxCount = 3)
    public ResponseEntity<String> uploadPdf(@RequestParam(FILE) MultipartFile file) {
        try {
            if (!file.getOriginalFilename().endsWith(PDF_SUFFIX)) {
                return ResponseEntity.badRequest().body("只允许上传 PDF 文件");
            }

            // 生成 bookId
            String bookId = UUID.randomUUID().toString();

            // 处理 uploadDir（支持相对路径 & 绝对路径）
            File baseDir = new File(uploadDir);
            if (!baseDir.isAbsolute()) {
                baseDir = new File(System.getProperty("user.dir"), uploadDir);
            }

            File bookDir = new File(baseDir, bookId);
            if (!bookDir.exists()) {
                bookDir.mkdirs();
            }

            // 保存原始 PDF
            File pdfFile = new File(bookDir, ORIGIN_PDF);
            file.transferTo(pdfFile);

            // 转换 PDF → 图片
            PdfToImageUtil.convert(pdfFile, bookDir);

            // 转换完成后删除原始 PDF
            if (pdfFile.exists()) {
                pdfFile.delete();
            }

            return ResponseEntity.ok("上传成功，bookId=" + bookId);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("上传失败: " + e.getMessage());
        }
    }

    @Operation(summary = "获取 PDF 页面", description = "获取指定页面的图片")
    @GetMapping("/preview/{bookId}/page/{pageNum}")
    @SlidingWindowRateLimit(windowInSeconds = 2, maxCount = 3)
    public ResponseEntity<FileSystemResource> getPage(@PathVariable String bookId, @PathVariable int pageNum) {
        // 权限控制
        UserLoginResponse currentUser = userService.getCurrentUser();
        if (UserRoleEnum.USER.getValue().equals(currentUser.getUserRole()) && pageNum > 1) {
            return ResponseEntity.status(403).body(null); // 非会员禁止访问
        }

        // 处理 uploadDir（支持相对路径 & 绝对路径）
        File baseDir = new File(uploadDir);
        if (!baseDir.isAbsolute()) {
            baseDir = new File(System.getProperty("user.dir"), uploadDir);
        }

        File bookDir = new File(baseDir, bookId);
        File file = new File(bookDir, PDF_PAGE_PREFIX + pageNum + PDF_PAGE_SUFFIX);

        if (!file.exists()) {
            return ResponseEntity.notFound().build();
        }

        // 返回图片
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, CONTENT_DISPOSITION + file.getName())
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(file));
    }
}
