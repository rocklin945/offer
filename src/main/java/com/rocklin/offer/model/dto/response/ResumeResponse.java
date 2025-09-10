package com.rocklin.offer.model.dto.response;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResumeResponse {
    private Long id;
    private Long userId;

    // 通用基本信息
    private String name;
    private String phone;
    private String wechat;
    private String email;
    private String gender;
    private String birthday;
    private String address;
    private String idNumber;

    // 央国企特有基本信息
    private String govCurrentResidence;
    private String govRegisteredResidence;
    private String govNativePlace;
    private String govGaokaoPlace;
    private String govPoliticalStatus;
    private String govEmergencyName;
    private String govEmergencyPhone;
    private String govEmergencyRelation;

    // 银行特有基本信息
    private String bankPoliticalStatus;
    private String bankHeight;
    private String bankWeight;
    private String bankHealth;
    private String bankMarriageStatus;
    private String bankChildStatus;
    private String bankInfoChannel;
    private String bankEmergencyName;
    private String bankEmergencyPhone;
    private String bankEmergencyRelation;

    // JSON -> List<Object>
    private List<EducationExperience> eduExperiences;
    private List<Internship> privateInternship;
    private List<CampusPractice> govCampusPractice;
    private List<WorkExperience> govWorkExperience;
    private List<ProjectExperience> privateProjectExperience;
    private List<SchoolExperience> bankSchoolExperience;
    private List<LanguageLevel> languageLevel;
    private List<String> honors;
    private List<String> certificates;
    private List<SkillLevel> skillLevel;
    private List<Paper> privatePaper;
    private List<String> privateCompetition;
    private List<FamilyInfo> familyInfo;
    private String bankSalaryExpectation;
    private String bankExpectedLocation;
    private List<ProfessionalCert> bankProfessionalCert;
    private List<String> bankComputerSkills;
    private List<TrainingExperience> bankTrainingExperience;
    private List<RewardPunishment> bankRewardsPunishments;

    // 自我评价
    private String selfEvaluation;

    // 时间
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    // 内部类
    @Data
    public static class EducationExperience {
        private String schoolName;      // 学校名称
        private String collegeName;     // 学院名称
        private String majorName;       // 专业名称
        private String majorDetail;     // 研究方向
        private String degree;          // 学历
        private String period;          // 在校时间
        private String gpa;             // GPA成绩
        private String rank;            // 成绩排名
        private Boolean fullTime;       // 是否全日制
        private String lab;             // 实验室
        private String supervisor;      // 导师
    }

    @Data
    public static class Internship {
        private String company;
        private String position;
        private String startTime;
        private String endTime;
        private String description;
    }

    @Data
    public static class ProjectExperience {
        private String projectName;
        private String role;
        private String projectTime;
        private String projectLink;
        private String description;
    }

    @Data
    public static class CampusPractice {
        private String startTime;
        private String endTime;
        private String description;
    }

    @Data
    public static class WorkExperience {
        private String company;
        private String position;
        private String startTime;
        private String endTime;
        private String duty;
        private String workType;
    }

    @Data
    public static class SchoolExperience {
        private String name;
        private String startTime;
        private String endTime;
        private String description;
    }

    @Data
    public static class LanguageLevel {
        private String language;
        private String level;
        private String exam;
        private String score;
    }

    @Data
    public static class SkillLevel {
        private String skillType;
        private String level;
    }

    @Data
    public static class Paper {
        private String name;
        private String publishChannel;
        private String authorOrder;
        private String impactFactor;
        private String link;
    }

    @Data
    public static class FamilyInfo {
        private String name;
        private String relation;
        private String workplace;
        private String department;
        private String jobTitle;
        private Integer age;
        private String phone;
        private Boolean foreignIdentity;
    }

    @Data
    public static class ProfessionalCert {
        private String name;
        private String issueDate;
        private String issuer;
        private String score;
        private String description;
    }

    @Data
    public static class TrainingExperience {
        private String trainingName;
        private String startTime;
        private String endTime;
        private String institution;
        private String certificate;
        private String description;
    }

    @Data
    public static class RewardPunishment {
        private String type;
        private String level;
        private String name;
        private String date;
        private String unit;
        private String reason;
    }
}
