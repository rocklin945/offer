package com.rocklin.offer.model.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Resume {
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

    // JSON 存储字段
    private String eduExperiences;           // 教育经历
    private String privateInternship;        // 民企：实习经历
    private String govCampusPractice;        // 央国企：校园实践经历
    private String govWorkExperience;        // 央国企：工作经历
    private String privateProjectExperience; // 民企：项目经历
    private String bankSchoolExperience;     // 银行：在校经历
    private String languageLevel;            // 语言水平
    private String honors;                   // 荣誉
    private String certificates;             // 证书
    private String skillLevel;               // 技能水平
    private String privatePaper;             // 民企：论文
    private String privateCompetition;       // 民企：竞赛
    private String familyInfo;               // 家庭情况
    private String bankSalaryExpectation;    // 银行：期望薪酬
    private String bankExpectedLocation;     // 银行：期望工作地点
    private String bankProfessionalCert;     // 银行：职业资格认证
    private String bankComputerSkills;       // 银行：计算机技能
    private String bankTrainingExperience;   // 银行：培训经历
    private String bankRewardsPunishments;   // 银行：奖惩情况

    // 自我评价
    private String selfEvaluation;

    // 时间
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
