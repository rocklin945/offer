package com.rocklin.offer.common.utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PDF简历解析工具类
 */
public class PdfResumeParser {

    /**
     * 解析PDF文件内容并提取简历信息，返回指定格式的JSON数据
     */
    public static Map<String, Object> parsePdfResume(MultipartFile file) throws IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);

        Map<String, Object> data = new HashMap<>();

        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            PDFTextStripper textStripper = new PDFTextStripper();
            String text = textStripper.getText(document);

            // 提取基本信息
            data.put("name", extractName(text));
            data.put("phone", extractPhone(text));
            data.put("email", extractEmail(text));
            data.put("wechat", extractWechat(text));
            data.put("gender", extractGender(text));
            data.put("birthday", extractBirthday(text));
            data.put("address", extractAddress(text));
            data.put("selfEvaluation", extractSelfEvaluation(text));

            // 提取数组类型信息
            data.put("eduExperiences", extractEduExperiences(text));
            data.put("privateInternship", extractPrivateInternship(text));
            data.put("privateProjectExperience", extractPrivateProjectExperience(text));
            data.put("skillLevel", extractSkillLevel(text));
            data.put("certificates", extractCertificates(text));
        }

        response.put("data", data);
        response.put("message", "success");

        return response;
    }

    /**
     * 从文本中提取姓名
     */
    public static String extractName(String text) {
        // 查找常见的姓名标识
        String[] patterns = {
                "姓名[\\s:：]*([\\u4e00-\\u9fa5]{2,4})",
                "name[\\s:：]*([\\u4e00-\\u9fa5]{2,4})",
                "Name[\\s:：]*([\\u4e00-\\u9fa5]{2,4})"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }

        // 简单的姓名提取逻辑，作为备选方案
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
     * 从文本中提取微信号
     */
    public static String extractWechat(String text) {
        String[] patterns = {
                "微信[\\s:：]*([A-Za-z0-9_-]{6,20})",
                "wechat[\\s:：]*([A-Za-z0-9_-]{6,20})",
                "Wechat[\\s:：]*([A-Za-z0-9_-]{6,20})"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 从文本中提取性别
     */
    public static String extractGender(String text) {
        String[] patterns = {
                "性别[\\s:：]*([男女])",
                "gender[\\s:：]*([男女])",
                "Gender[\\s:：]*([男女])"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 从文本中提取生日
     */
    public static String extractBirthday(String text) {
        String[] patterns = {
                "出生日期[\\s:：]*(\\d{4}[年/-]\\d{1,2}[月/-]\\d{1,2}[日]?)",
                "生日[\\s:：]*(\\d{4}[年/-]\\d{1,2}[月/-]\\d{1,2}[日]?)",
                "birthday[\\s:：]*(\\d{4}[年/-]\\d{1,2}[月/-]\\d{1,2}[日]?)",
                "Birthday[\\s:：]*(\\d{4}[年/-]\\d{1,2}[月/-]\\d{1,2}[日]?)"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 从文本中提取地址
     */
    public static String extractAddress(String text) {
        String[] patterns = {
                "地址[\\s:：]*([\\u4e00-\\u9fa5]{2,20})",
                "address[\\s:：]*([\\u4e00-\\u9fa5]{2,20})",
                "Address[\\s:：]*([\\u4e00-\\u9fa5]{2,20})"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1);
            }
        }
        return null;
    }

    /**
     * 从文本中提取自我评价
     */
    public static String extractSelfEvaluation(String text) {
        String[] patterns = {
                "自我评价[\\s:：]*([\\s\\S]*?)(?=\\n\\S|$)",
                "自我介绍[\\s:：]*([\\s\\S]*?)(?=\\n\\S|$)",
                "个人评价[\\s:：]*([\\s\\S]*?)(?=\\n\\S|$)"
        };

        for (String patternStr : patterns) {
            Pattern pattern = Pattern.compile(patternStr);
            Matcher matcher = pattern.matcher(text);
            if (matcher.find()) {
                return matcher.group(1).trim();
            }
        }
        return null;
    }

    /**
     * 从文本中提取教育经历
     */
    public static List<Map<String, Object>> extractEduExperiences(String text) {
        List<Map<String, Object>> eduExperiences = new ArrayList<>();

        // 简化的教育经历提取逻辑
        String[] eduKeywords = { "教育背景", "教育经历", "学历", "毕业院校" };
        for (String keyword : eduKeywords) {
            if (text.contains(keyword)) {
                // 提取教育经历段落
                int startIndex = text.indexOf(keyword);
                // 找到下一个标题或文本末尾
                int endIndex = findNextSectionStart(text, startIndex + keyword.length());

                String eduSection = text.substring(startIndex, endIndex);

                // 使用更精确的正则表达式提取教育经历信息
                // 匹配学校、学院、专业、学历、时间等信息
                // 改进的模式，更好地分离专业名称和研究方向
                Pattern pattern = Pattern.compile(
                        "([\\u4e00-\\u9fa5]+(?:大学|学院|学校))\\s*[:：]?\\s*" + // 学校名称
                                "([\\u4e00-\\u9fa5]+(?:学院|系))?\\s*[:：]?\\s*" + // 学院名称（可选）
                                "([\\u4e00-\\u9fa5]+(?:专业))?\\s*[:：]?\\s*" + // 专业名称
                                "(本科|硕士|博士|学士|研究生)?\\s*[:：]?\\s*" + // 学历（可选）
                                "(\\d{4}\\.\\d{1,2}(?:[-–—])\\d{4}\\.\\d{1,2}|至今)?\\s*[:：]?\\s*" + // 时间段（可选）
                                "([^\\n]*)", // 剩余信息（可能包含研究方向等）
                        Pattern.DOTALL);

                Matcher matcher = pattern.matcher(eduSection);
                while (matcher.find()) {
                    Map<String, Object> edu = new HashMap<>();
                    edu.put("schoolName", matcher.group(1) != null ? matcher.group(1).trim() : "");
                    edu.put("collegeName", matcher.group(2) != null ? matcher.group(2).trim() : "");
                    edu.put("majorName", matcher.group(3) != null ? matcher.group(3).replaceAll("专业$", "").trim() : ""); // 移除"专业"后缀
                    edu.put("degree", matcher.group(4) != null ? matcher.group(4).trim() : "");
                    edu.put("period", matcher.group(5) != null ? matcher.group(5).trim() : "");

                    // 处理剩余信息，可能包含研究方向等
                    String remainingInfo = matcher.group(6) != null ? matcher.group(6).trim() : "";
                    // 尝试从剩余信息中提取研究方向
                    edu.put("majorDetail", extractMajorDetail(remainingInfo));

                    edu.put("gpa", "");
                    edu.put("rank", "");
                    edu.put("fullTime", true);
                    edu.put("lab", "");
                    edu.put("supervisor", "");
                    eduExperiences.add(edu);
                }

                // 如果没有匹配到标准格式，尝试简单提取
                if (eduExperiences.isEmpty()) {
                    Pattern schoolPattern = Pattern.compile("([\\u4e00-\\u9fa5]+(?:大学|学院|学校))");
                    Matcher schoolMatcher = schoolPattern.matcher(eduSection);
                    if (schoolMatcher.find()) {
                        Map<String, Object> edu = new HashMap<>();
                        edu.put("schoolName", schoolMatcher.group(1));
                        edu.put("collegeName", "");
                        edu.put("majorName", "");
                        edu.put("majorDetail", "");
                        edu.put("degree", "");
                        edu.put("period", "");
                        edu.put("gpa", "");
                        edu.put("rank", "");
                        edu.put("fullTime", true);
                        edu.put("lab", "");
                        edu.put("supervisor", "");
                        eduExperiences.add(edu);
                    }
                }
                break;
            }
        }

        return eduExperiences;
    }

    /**
     * 从剩余信息中提取专业详细信息/研究方向
     */
    private static String extractMajorDetail(String remainingInfo) {
        if (remainingInfo == null || remainingInfo.trim().isEmpty()) {
            return "";
        }

        // 移除常见的标点符号和前缀
        String cleanedInfo = remainingInfo.trim()
                .replaceAll("^[\\s:：-]*", "") // 移除开头的空格和标点
                .replaceAll("[\\(\\)（）\\[\\]]", "") // 移除括号
                .trim();

        // 如果信息以"研究方向"、"方向"等开头，则移除这些前缀
        if (cleanedInfo.startsWith("研究方向") || cleanedInfo.startsWith("方向")) {
            // 找到冒号或分号后的内容
            int colonIndex = Math.max(cleanedInfo.indexOf(":"), cleanedInfo.indexOf("："));
            if (colonIndex > 0) {
                return cleanedInfo.substring(colonIndex + 1).trim();
            } else {
                // 如果没有冒号，移除前缀
                return cleanedInfo.replaceFirst("^(研究方向|方向)[:：]?\\s*", "").trim();
            }
        }

        return cleanedInfo;
    }

    /**
     * 从文本中提取实习经历
     */
    public static List<Map<String, String>> extractPrivateInternship(String text) {
        List<Map<String, String>> internships = new ArrayList<>();

        // 简化的实习经历提取逻辑
        String[] internshipKeywords = { "实习经历", "实习经验", "实习", "Internship" };
        for (String keyword : internshipKeywords) {
            if (text.contains(keyword)) {
                // 提取实习经历段落
                int startIndex = text.indexOf(keyword);
                // 找到下一个标题或文本末尾
                int endIndex = findNextSectionStart(text, startIndex + keyword.length());

                String internshipSection = text.substring(startIndex, endIndex);

                // 使用正则表达式提取实习经历信息
                // 匹配公司名称和时间段
                Pattern pattern = Pattern.compile(
                        "([\\u4e00-\\u9fa5]+(?:公司|科技|集团|有限))\\s*[:：]?\\s*" +
                                "(\\d{4}\\.\\d{1,2}(?:[-–—])\\d{4}\\.\\d{1,2}|至今)?\\s*[:：]?\\s*" +
                                "([\\u4e00-\\u9fa5]+)?\\s*[:：]?\\s*" +
                                "([^\\n]*)");

                Matcher matcher = pattern.matcher(internshipSection);
                while (matcher.find()) {
                    Map<String, String> internship = new HashMap<>();
                    internship.put("company", matcher.group(1) != null ? matcher.group(1).trim() : "");
                    internship.put("startTime",
                            matcher.group(2) != null ? matcher.group(2).split("[-–—]")[0].trim() : "");
                    internship.put("endTime", matcher.group(2) != null
                            ? (matcher.group(2).contains("至今") ? "至今" : matcher.group(2).split("[-–—]")[1].trim())
                            : "");
                    internship.put("position", matcher.group(3) != null ? matcher.group(3).trim() : "");
                    internship.put("description", matcher.group(4) != null ? matcher.group(4).trim() : "");
                    internships.add(internship);
                }

                // 如果没有匹配到标准格式，尝试简单提取
                if (internships.isEmpty()) {
                    Pattern companyPattern = Pattern.compile("([\\u4e00-\\u9fa5]+(?:公司|科技|集团|有限))");
                    Matcher companyMatcher = companyPattern.matcher(internshipSection);
                    if (companyMatcher.find()) {
                        Map<String, String> internship = new HashMap<>();
                        internship.put("company", companyMatcher.group(1));
                        internship.put("position", "");
                        internship.put("startTime", "");
                        internship.put("endTime", "");
                        internship.put("description", extractDescription(internshipSection, companyMatcher.group(1)));
                        internships.add(internship);
                    }
                }
                break;
            }
        }

        return internships;
    }

    /**
     * 从文本中提取项目经历
     */
    public static List<Map<String, String>> extractPrivateProjectExperience(String text) {
        List<Map<String, String>> projects = new ArrayList<>();

        // 简化的项目经历提取逻辑
        String[] projectKeywords = { "项目经历", "项目经验", "项目", "Project" };
        for (String keyword : projectKeywords) {
            if (text.contains(keyword)) {
                // 提取项目经历段落
                int startIndex = text.indexOf(keyword);
                // 找到下一个标题或文本末尾
                int endIndex = findNextSectionStart(text, startIndex + keyword.length());

                String projectSection = text.substring(startIndex, endIndex);

                // 使用正则表达式提取项目经历信息
                // 匹配项目名称和时间段
                Pattern pattern = Pattern.compile(
                        "项目名称\\s*[:：]?\\s*([\\u4e00-\\u9fa5A-Za-z0-9]+)\\s*[:：]?\\s*" +
                                "(\\d{4}\\.\\d{1,2}(?:[-–—])\\d{4}\\.\\d{1,2}|至今)?\\s*[:：]?\\s*" +
                                "([^\\n]*)");

                Matcher matcher = pattern.matcher(projectSection);
                while (matcher.find()) {
                    Map<String, String> project = new HashMap<>();
                    project.put("projectName", matcher.group(1) != null ? matcher.group(1).trim() : "");
                    project.put("projectTime", matcher.group(2) != null ? matcher.group(2).trim() : "");
                    project.put("role", "");
                    project.put("projectLink", "");
                    project.put("description", matcher.group(3) != null ? matcher.group(3).trim() : "");
                    projects.add(project);
                }

                // 如果没有匹配到标准格式，尝试简单提取
                if (projects.isEmpty()) {
                    Pattern projectPattern = Pattern.compile("项目名称\\s*[:：]?\\s*([\\u4e00-\\u9fa5A-Za-z0-9]+)");
                    Matcher projectMatcher = projectPattern.matcher(projectSection);
                    if (projectMatcher.find()) {
                        Map<String, String> project = new HashMap<>();
                        project.put("projectName", projectMatcher.group(1));
                        project.put("role", "");
                        project.put("projectTime", "");
                        project.put("projectLink", "");
                        project.put("description", extractDescription(projectSection, projectMatcher.group(1)));
                        projects.add(project);
                    }
                }
                break;
            }
        }

        return projects;
    }

    /**
     * 查找下一个章节的开始位置
     */
    private static int findNextSectionStart(String text, int fromIndex) {
        // 常见的章节标题关键词
        String[] sectionKeywords = {
                "教育背景", "教育经历", "学历", "毕业院校",
                "实习经历", "实习经验", "实习", "Internship",
                "项目经历", "项目经验", "项目", "Project",
                "专业技能", "技能", "Skill", "Skills",
                "证书", "资格证书", "Certification",
                "自我评价", "自我介绍", "个人评价"
        };

        int minIndex = text.length();
        for (String keyword : sectionKeywords) {
            int index = text.indexOf(keyword, fromIndex);
            if (index != -1 && index < minIndex) {
                minIndex = index;
            }
        }

        return minIndex;
    }

    /**
     * 从文本中提取描述信息
     */
    private static String extractDescription(String section, String keyword) {
        // 找到关键词的位置
        int keywordIndex = section.indexOf(keyword);
        if (keywordIndex == -1) {
            return "";
        }

        // 从关键词后开始查找描述
        int startIndex = keywordIndex + keyword.length();
        // 查找下一个关键词或段落结束
        int endIndex = findNextSectionStart(section, startIndex);

        if (endIndex <= startIndex) {
            endIndex = section.length();
        }

        String description = section.substring(startIndex, endIndex).trim();
        // 移除常见的前缀符号
        description = description.replaceAll("^[\\s:：-]*", "");
        return description;
    }

    /**
     * 从文本中提取专业技能
     */
    public static List<Map<String, String>> extractSkillLevel(String text) {
        List<Map<String, String>> skills = new ArrayList<>();

        // 简化的技能提取逻辑
        String[] skillKeywords = { "专业技能", "技能", "Skill", "Skills" };
        for (String keyword : skillKeywords) {
            if (text.contains(keyword)) {
                // 提取技能段落
                int startIndex = text.indexOf(keyword);
                // 找到下一个标题或文本末尾
                int endIndex = findNextSectionStart(text, startIndex + keyword.length());

                String skillSection = text.substring(startIndex, endIndex);

                // 按行分割技能内容
                String[] lines = skillSection.split("\n");
                for (String line : lines) {
                    // 跳过标题行
                    if (line.contains(keyword)) {
                        continue;
                    }

                    // 匹配技能格式，支持多种分隔符
                    if (line.contains(":") || line.contains("：") || line.contains("-") || line.contains("–")
                            || line.contains("—")) {
                        Map<String, String> skill = new HashMap<>();
                        // 使用正则表达式安全地分割字符串
                        String[] parts = line.split("[:：]|[–—-]", 2);
                        if (parts.length >= 2) {
                            skill.put("skillType", parts[0].trim());
                            skill.put("level", parts[1].trim());
                            skills.add(skill);
                        } else {
                            // 如果只有一个部分，将其作为技能类型，级别为空
                            skill.put("skillType", parts[0].trim());
                            skill.put("level", "");
                            skills.add(skill);
                        }
                    } else if (line.trim().length() > 0 && !line.trim().equals("技能") && !line.trim().equals("专业技能")) {
                        // 处理没有分隔符的技能行
                        Map<String, String> skill = new HashMap<>();
                        skill.put("skillType", line.trim());
                        skill.put("level", "");
                        skills.add(skill);
                    }
                }

                // 如果没有提取到技能，尝试简单提取
                if (skills.isEmpty()) {
                    String[] skillItems = skillSection.split("\n");
                    for (String item : skillItems) {
                        if (item.contains(":") || item.contains("：")) {
                            Map<String, String> skill = new HashMap<>();
                            // 使用正则表达式安全地分割字符串
                            String[] parts = item.split("[:：]", 2);
                            if (parts.length >= 2) {
                                skill.put("skillType", parts[0].trim());
                                skill.put("level", parts[1].trim());
                                skills.add(skill);
                            }
                        }
                    }
                }
                break;
            }
        }

        return skills;
    }

    /**
     * 从文本中提取证书
     */
    public static List<String> extractCertificates(String text) {
        List<String> certificates = new ArrayList<>();

        // 简化的证书提取逻辑
        String[] certKeywords = { "证书", "资格证书", "Certification" };
        for (String keyword : certKeywords) {
            if (text.contains(keyword)) {
                // 提取证书段落
                int startIndex = text.indexOf(keyword);
                int endIndex = text.indexOf("\n\n", startIndex);
                if (endIndex == -1)
                    endIndex = text.length();

                String certSection = text.substring(startIndex, endIndex);

                // 简单提取证书名称
                Pattern certPattern = Pattern.compile("([\\u4e00-\\u9fa5A-Za-z0-9]+证书|[\\u4e00-\\u9fa5A-Za-z0-9]+认证)");
                Matcher certMatcher = certPattern.matcher(certSection);
                while (certMatcher.find()) {
                    certificates.add(certMatcher.group(1));
                }
                break;
            }
        }

        return certificates;
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