package com.rocklin.offer.util;

import com.rocklin.offer.entity.JobInfo;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * HTML招聘信息解析工具类
 * 用于解析包含招聘信息的HTML内容
 * 
 * @author rocklin
 * @date 2025-08-24
 */
public class JobInfoParser {

    private static final Logger logger = LoggerFactory.getLogger(JobInfoParser.class);

    /**
     * 解析HTML字符串，提取招聘信息列表
     * 
     * @param htmlContent HTML内容
     * @return 招聘信息列表
     */
    public static List<JobInfo> parseJobInfoFromHtml(String htmlContent) {
        List<JobInfo> jobInfoList = new ArrayList<>();

        if (htmlContent == null || htmlContent.trim().isEmpty()) {
            logger.warn("HTML内容为空");
            return jobInfoList;
        }

        try {
            logger.debug("开始解析HTML内容，长度: {} 字符", htmlContent.length());
            logger.debug("HTML内容前200字符: {}", htmlContent.substring(0, Math.min(200, htmlContent.length())));
            
            // 如果HTML内容不包含<html>标签，包装为完整的HTML文档
            String processedHtml = htmlContent;
            if (!htmlContent.trim().toLowerCase().startsWith("<html")) {
                processedHtml = "<html><body><table>" + htmlContent + "</table></body></html>";
                logger.debug("已包装HTML内容为完整文档");
            }
            
            Document doc = Jsoup.parse(processedHtml);
            Elements rows = doc.select("tr[data-id]");

            logger.info("找到{}条招聘信息记录", rows.size());
            
            if (rows.size() == 0) {
                logger.warn("未找到tr[data-id]元素，尝试查找所有tr元素");
                Elements allTrs = doc.select("tr");
                logger.info("HTML中总共找到{}个tr元素", allTrs.size());
                
                for (int i = 0; i < Math.min(3, allTrs.size()); i++) {
                    Element tr = allTrs.get(i);
                    logger.debug("第{}个tr元素: {}", i+1, tr.outerHtml().substring(0, Math.min(100, tr.outerHtml().length())));
                    if (tr.hasAttr("data-id")) {
                        logger.debug("第{}个tr元素有data-id属性: {}", i+1, tr.attr("data-id"));
                    }
                }
            }

            for (Element row : rows) {
                JobInfo jobInfo = parseJobInfoFromRow(row);
                if (jobInfo != null) {
                    jobInfoList.add(jobInfo);
                }
            }

        } catch (Exception e) {
            logger.error("解析HTML时发生错误", e);
        }

        return jobInfoList;
    }

    /**
     * 从单个表格行元素解析招聘信息
     * 
     * @param row 表格行元素
     * @return 招聘信息对象
     */
    private static JobInfo parseJobInfoFromRow(Element row) {
        try {
            JobInfo jobInfo = new JobInfo();

            // 获取数据ID
            String dataId = row.attr("data-id");
            jobInfo.setDataId(dataId);

            // 解析各个字段
            Elements tds = row.select("td");
            if (tds.size() < 10) {
                logger.warn("表格行数据不完整，跳过此记录，dataId: {}", dataId);
                return null;
            }

            // 公司名称
            Element companyElement = row.select(".crt-col-company").first();
            if (companyElement != null) {
                jobInfo.setCompanyName(cleanText(companyElement.text()));
            }

            // 公司类型
            Element typeElement = row.select(".crt-col-type .crt-badge").first();
            if (typeElement != null) {
                String typeText = typeElement.text();
                jobInfo.setCompanyType(cleanText(typeText));
            }

            // 所属行业 - 通常是第三个td元素，需要根据实际HTML结构调整
            if (tds.size() > 2) {
                String industryText = tds.get(2).text();
                if (!industryText.equals(jobInfo.getCompanyName())) {
                    jobInfo.setIndustry(cleanText(industryText));
                }
            }

            // 招聘类型
            Element recruitmentElement = row.select(".crt-col-recruitment-type .crt-badge").first();
            if (recruitmentElement != null) {
                jobInfo.setRecruitmentType(cleanText(recruitmentElement.text()));
            }

            // 工作地点
            Element locationElement = row.select(".crt-col-location").first();
            if (locationElement != null) {
                String rawLocation = cleanText(locationElement.text());
                //String formattedLocation = formatLocation(rawLocation);
                jobInfo.setLocation(rawLocation);
            }

            // 目标人群
            Element targetElement = row.select(".crt-col-target .crt-badge").first();
            if (targetElement != null) {
                jobInfo.setTargetGroup(cleanText(targetElement.text()));
            }

            // 岗位信息
            Element positionElement = row.select(".crt-col-position .crt-position-tag").first();
            if (positionElement != null) {
                String rawPositions = cleanText(positionElement.text());
                String formattedPositions = formatPositions(rawPositions);
                jobInfo.setPositions(formattedPositions);
            }

            // 投递状态
            Element statusElement = row.select(".crt-col-status .crt-badge").first();
            if (statusElement != null) {
                jobInfo.setStatus(cleanText(statusElement.text()));
            }

            // 更新时间
            Element updateTimeElement = row.select(".crt-col-update-time").first();
            if (updateTimeElement != null) {
                jobInfo.setUpdateTime(cleanText(updateTimeElement.text()));
            }

            // 截止时间
            Element deadlineElement = row.select(".crt-col-deadline").first();
            if (deadlineElement != null) {
                jobInfo.setDeadline(cleanText(deadlineElement.text()));
            }

            // 投递链接
            Element linkElement = row.select(".crt-col-links a.crt-link").first();
            if (linkElement != null) {
                jobInfo.setApplicationLink(linkElement.attr("href"));
            }

            // 公告链接
            Element noticeElement = row.select(".crt-col-notice a.crt-notice-link").first();
            if (noticeElement != null) {
                jobInfo.setNoticeLink(noticeElement.attr("href"));
            }

            // 内推信息
            Element referralElement = row.select(".crt-col-referral").first();
            if (referralElement != null) {
                String referralText = cleanText(referralElement.text());
                jobInfo.setReferral(referralText.equals("-") ? "" : referralText);
            }

            // 备注信息
            Element notesElement = row.select(".crt-col-notes").first();
            if (notesElement != null) {
                String notesText = cleanText(notesElement.text());
                jobInfo.setNotes(notesText.equals("-") ? "" : notesText);
            }

            return jobInfo;

        } catch (Exception e) {
            logger.error("解析单行数据时发生错误", e);
            return null;
        }
    }

    /**
     * 清理文本内容，去除多余空格和特殊字符
     * 
     * @param text 原始文本
     * @return 清理后的文本
     */
    private static String cleanText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        return text.trim().replaceAll("\\s+", " ");
    }

    /**
     * 验证招聘信息的必要字段是否完整
     * 
     * @param jobInfo 招聘信息对象
     * @return 是否有效
     */
    public static boolean isValidJobInfo(JobInfo jobInfo) {
        if (jobInfo == null) {
            return false;
        }

        return jobInfo.getCompanyName() != null && !jobInfo.getCompanyName().trim().isEmpty() &&
                jobInfo.getPositions() != null && !jobInfo.getPositions().trim().isEmpty();
    }

    /**
     * 格式化工作地点，两个字两个字进行划分，中间加空格
     * 
     * @param location 原始地点字符串
     * @return 格式化后的地点字符串
     */
    private static String formatLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            return "";
        }
        
        // 先去除多余的空格，但保留单个空格作为分隔符
        String cleanLocation = location.trim().replaceAll("\\s+", " ");
        
        StringBuilder result = new StringBuilder();
        String[] parts = cleanLocation.split(" ");
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.isEmpty()) {
                continue;
            }
            
            if (result.length() > 0) {
                result.append(" ");
            }
            
            // 对每个部分进行两个字一组的划分
            result.append(formatLocationPart(part));
        }
        
        return result.toString();
    }
    
    /**
     * 对单个地点部分进行两个字一组的划分
     */
    private static String formatLocationPart(String part) {
        if (part.length() <= 2) {
            return part;
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < part.length(); i += 2) {
            if (result.length() > 0) {
                result.append(" ");
            }

            if (i + 1 < part.length()) {
                result.append(part.substring(i, i + 2));
            } else {
                result.append(part.substring(i));
            }
        }
        
        return result.toString();
    }

    /**
     * 格式化岗位信息，按照"、"号、空格或者"xxxx类"的格式进行划分后加空格
     * 
     * @param positions 原始岗位字符串
     * @return 格式化后的岗位字符串
     */
    private static String formatPositions(String positions) {
        if (positions == null || positions.trim().isEmpty()) {
            return "";
        }
        
        String cleanPositions = positions.trim();
        logger.debug("格式化岗位信息 - 原始: {}", cleanPositions);
        
        // 先按照"、"号分割
        String[] parts = cleanPositions.split("、");
        List<String> allPositions = new ArrayList<>();
        
        for (String part : parts) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }
            
            // 再按照空格分割
            String[] spaceParts = part.split("\\s+");
            for (String spacePart : spaceParts) {
                spacePart = spacePart.trim();
                if (spacePart.isEmpty()) {
                    continue;
                }
                
                // 按照"技术类"、"产品类"、"运营类"、"设计类"等格式分割
                List<String> classParts = splitByJobCategory(spacePart);
                logger.debug("分割 '{}' 得到: {}", spacePart, classParts);
                allPositions.addAll(classParts);
            }
        }
        
        // 去重并连接
        String result = allPositions.stream()
                .distinct()
                .filter(pos -> !pos.trim().isEmpty())
                .collect(Collectors.joining(" "));
        
        logger.debug("格式化岗位信息 - 结果: {}", result);
        return result;
    }
    
    /**
     * 按照职业类别（技术类、产品类、运营类、设计类等）分割字符串
     */
    private static List<String> splitByJobCategory(String text) {
        List<String> result = new ArrayList<>();
        
        // 先尝试按照"类"字进行分割（针对连续的专业类别）
        if (text.contains("类")) {
            // 使用简单的字符串分割方式
            // 先按照"类"字分割，然后再给每个部分加上"类"
            String[] parts = text.split("类");
            
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i].trim();
                if (part.isEmpty()) {
                    continue;
                }
                
                if (i < parts.length - 1) {
                    // 不是最后一个部分，说明后面有"类"字
                    // 需要找到最后一个词加上"类"
                    String[] words = part.split("\\s+");
                    if (words.length > 0) {
                        // 添加前面的词
                        for (int j = 0; j < words.length - 1; j++) {
                            if (!words[j].trim().isEmpty()) {
                                List<String> subParts = splitNonCategoryText(words[j].trim());
                                result.addAll(subParts);
                            }
                        }
                        // 最后一个词加上"类"
                        String lastWord = words[words.length - 1].trim();
                        if (!lastWord.isEmpty()) {
                            result.add(lastWord + "类");
                        }
                    }
                } else {
                    // 最后一个部分，直接处理
                    if (!part.isEmpty()) {
                        List<String> subParts = splitNonCategoryText(part);
                        result.addAll(subParts);
                    }
                }
            }
        }
        
        // 如果没有找到任何类别，对整个文本进行分割
        if (result.isEmpty()) {
            List<String> subParts = splitNonCategoryText(text.trim());
            result.addAll(subParts);
        }
        
        return result;
    }
    
    /**
     * 对非类别文本进行分割（按照常见的分隔符和岗位关键词）
     */
    private static List<String> splitNonCategoryText(String text) {
        List<String> result = new ArrayList<>();
        
        // 首先按照常见分隔符分割
        String[] separators = {"、", " ", "，", ",", "；", ";"};
        
        String[] parts = {text};
        for (String separator : separators) {
            List<String> newParts = new ArrayList<>();
            for (String part : parts) {
                String[] subParts = part.split(Pattern.quote(separator));
                for (String subPart : subParts) {
                    String trimmed = subPart.trim();
                    if (!trimmed.isEmpty()) {
                        newParts.add(trimmed);
                    }
                }
            }
            parts = newParts.toArray(new String[0]);
        }
        
        // 对每个部分进行岗位关键词分割
        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                List<String> jobParts = splitByJobKeywords(part.trim());
                result.addAll(jobParts);
            }
        }
        
        return result;
    }
    
    /**
     * 根据岗位关键词进行智能分割
     * 例如："研发工程师商务工程师运营工程师" -> ["研发工程师", "商务工程师", "运营工程师"]
     */
    private static List<String> splitByJobKeywords(String text) {
        List<String> result = new ArrayList<>();
        
        // 先尝试按照"师"字进行分割（针对连续的岗位名称）
        if (text.contains("师")) {
            // 使用简单的字符串分割方式
            String[] parts = text.split("师");
            
            for (int i = 0; i < parts.length; i++) {
                String part = parts[i].trim();
                if (part.isEmpty()) {
                    continue;
                }
                
                if (i < parts.length - 1) {
                    // 不是最后一个部分，说明后面有"师"字
                    // 需要找到最后一个词加上"师"
                    String[] words = part.split("\\s+");
                    if (words.length > 0) {
                        // 添加前面的词
                        for (int j = 0; j < words.length - 1; j++) {
                            if (!words[j].trim().isEmpty()) {
                                result.add(words[j].trim());
                            }
                        }
                        // 最后一个词加上"师"
                        String lastWord = words[words.length - 1].trim();
                        if (!lastWord.isEmpty()) {
                            result.add(lastWord + "师");
                        }
                    }
                } else {
                    // 最后一个部分，直接添加
                    if (!part.isEmpty()) {
                        result.add(part);
                    }
                }
            }
        }
        
        // 如果没有找到"师"字，尝试其他关键词分割
        if (result.isEmpty()) {
            // 定义常见的岗位关键词
            String[] jobKeywords = {
                "工程师", "经理", "专员", "主管", "总监", "助理", "实习生", "岗", 
                "开发", "设计", "产品", "运营", "市场", "销售", "客服", "人事", "财务", 
                "法务", "行政", "测试", "算法", "数据", "安全", "运维", "架构", "前端", 
                "后端", "全栈", "移动", "游戏", "AI", "机器学习", "深度学习", "大数据",
                "Java", "Python", "前端", "后端", "iOS", "Android", "Web", "APP",
                "UI", "UE", "UX", "视觉", "交互", "平面", "品牌", "创意", "文案",
                "新媒体", "内容", "策划", "推广", "增长", "用户", "社群", "直播",
                "投资", "风控", "审计", "会计", "出纳", "税务", "成本", "预算",
                "招聘", "培训", "薪酬", "绩效", "HRBP", "组织发展", "员工关系"
            };
            
            // 构建正则表达式，匹配岗位模式
            StringBuilder patternBuilder = new StringBuilder();
            patternBuilder.append("(");
            
            // 添加各种岗位模式
            List<String> patterns = new ArrayList<>();
            
            // 模式1: [中文/英文][岗位关键词]
            for (String keyword : jobKeywords) {
                patterns.add("[\\u4e00-\\u9fa5a-zA-Z0-9]*" + Pattern.quote(keyword));
            }
            
            // 模式2: [岗位关键词][中文/英文]
            for (String keyword : jobKeywords) {
                patterns.add(Pattern.quote(keyword) + "[\\u4e00-\\u9fa5a-zA-Z0-9]*");
            }
            
            patternBuilder.append(String.join("|", patterns));
            patternBuilder.append(")");
            
            try {
                Pattern pattern = Pattern.compile(patternBuilder.toString());
                Matcher matcher = pattern.matcher(text);
                
                int lastEnd = 0;
                while (matcher.find()) {
                    // 添加匹配前的内容
                    if (matcher.start() > lastEnd) {
                        String before = text.substring(lastEnd, matcher.start()).trim();
                        if (!before.isEmpty() && before.length() > 1) {
                            result.add(before);
                        }
                    }
                    
                    // 添加匹配的岗位
                    String job = matcher.group(1).trim();
                    if (!job.isEmpty()) {
                        result.add(job);
                    }
                    lastEnd = matcher.end();
                }
                
                // 添加最后的内容
                if (lastEnd < text.length()) {
                    String remaining = text.substring(lastEnd).trim();
                    if (!remaining.isEmpty()) {
                        result.add(remaining);
                    }
                }
                
                // 如果没有匹配到任何岗位，返回原文本
                if (result.isEmpty()) {
                    result.add(text.trim());
                }
                
            } catch (Exception e) {
                // 如果正则表达式出错，返回原文本
                result.add(text.trim());
            }
        }
        
        return result;
    }
}