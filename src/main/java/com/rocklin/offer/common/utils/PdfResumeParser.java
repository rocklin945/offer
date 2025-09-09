package com.rocklin.offer.common.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PDF简历解析工具类
 */
public class PdfResumeParser {

    /**
     * 解析PDF文件内容并提取简历信息
     */
    public static Map<String, String> parsePdfContent(MultipartFile file) throws IOException {
        Map<String, String> result = new HashMap<>();
        
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document);
            
            // 提取姓名
            String name = extractName(text);
            if (name != null) {
                result.put("name", name);
            }
            
            // 提取电话
            String phone = extractPhone(text);
            if (phone != null) {
                result.put("phone", phone);
            }
            
            // 提取邮箱
            String email = extractEmail(text);
            if (email != null) {
                result.put("email", email);
            }
            
            // 提取完整文本内容
            result.put("fullText", text);
        }
        
        return result;
    }

    /**
     * 从文本中提取姓名
     */
    public static String extractName(String text) {
        // 简单的姓名提取逻辑，实际应用中可能需要更复杂的规则
        Pattern pattern = Pattern.compile("[\\u4e00-\\u9fa5]{2,4}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从文本中提取手机号
     */
    public static String extractPhone(String text) {
        Pattern pattern = Pattern.compile("1[3-9]\\d{9}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 从文本中提取邮箱
     */
    public static String extractEmail(String text) {
        Pattern pattern = Pattern.compile("\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}\\b");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }

    /**
     * 验证是否为PDF文件
     */
    public static boolean isPdfFile(MultipartFile file) {
        return file != null && 
               !file.isEmpty() && 
               "application/pdf".equals(file.getContentType());
    }
}