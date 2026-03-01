package com.rocklin.offer.entity;

/**
 * 招聘信息实体类
 * 包含从HTML解析出的招聘相关信息
 * 
 * @author rocklin
 * @date 2025-08-24
 */
public class JobInfo {

    /**
     * 数据ID
     */
    private String dataId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司类型（如：民企、国企、外企等）
     */
    private String companyType;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 招聘类型（如：秋招、春招等）
     */
    private String recruitmentType;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 目标人群（如：2026年毕业生）
     */
    private String targetGroup;

    /**
     * 岗位信息
     */
    private String positions;

    /**
     * 投递状态
     */
    private String status;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 截止时间
     */
    private String deadline;

    /**
     * 投递链接
     */
    private String applicationLink;

    /**
     * 公告链接
     */
    private String noticeLink;

    /**
     * 内推信息
     */
    private String referral;

    /**
     * 备注信息
     */
    private String notes;

    // 构造方法
    public JobInfo() {
    }

    // Getter 和 Setter 方法
    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getRecruitmentType() {
        return recruitmentType;
    }

    public void setRecruitmentType(String recruitmentType) {
        this.recruitmentType = recruitmentType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getPositions() {
        return positions;
    }

    public void setPositions(String positions) {
        this.positions = positions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public String getApplicationLink() {
        return applicationLink;
    }

    public void setApplicationLink(String applicationLink) {
        this.applicationLink = applicationLink;
    }

    public String getNoticeLink() {
        return noticeLink;
    }

    public void setNoticeLink(String noticeLink) {
        this.noticeLink = noticeLink;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "JobInfo{" +
                "dataId='" + dataId + '\'' +
                ", companyName='" + companyName + '\'' +
                ", companyType='" + companyType + '\'' +
                ", industry='" + industry + '\'' +
                ", recruitmentType='" + recruitmentType + '\'' +
                ", location='" + location + '\'' +
                ", targetGroup='" + targetGroup + '\'' +
                ", positions='" + positions + '\'' +
                ", status='" + status + '\'' +
                ", updateTime='" + updateTime + '\'' +
                ", deadline='" + deadline + '\'' +
                ", applicationLink='" + applicationLink + '\'' +
                ", noticeLink='" + noticeLink + '\'' +
                ", referral='" + referral + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}