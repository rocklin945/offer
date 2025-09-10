package com.rocklin.offer.model.dto.request;

import com.rocklin.offer.model.dto.response.ResumeResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 新增简历请求 DTO
 */
@Data
public class ResumeUpdateRequest {
    @NotNull(message = "简历ID不能为空")
    private Long id;

    private String name;
    private String phone;
    private String wechat;
    private String email;
    private String gender;
    private String birthday;
    private String address;
    private String idNumber;

    // 央国企特有字段
    private String govCurrentResidence;
    private String govRegisteredResidence;
    private String govNativePlace;
    private String govGaokaoPlace;
    private String govPoliticalStatus;
    private String govEmergencyName;
    private String govEmergencyPhone;
    private String govEmergencyRelation;

    // 银行特有字段
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

    // List 类型（直接对象，入库时会转 JSON）
    private List<ResumeResponse.EducationExperience> eduExperiences;
    private List<ResumeResponse.Internship> privateInternship;
    private List<ResumeResponse.CampusPractice> govCampusPractice;
    private List<ResumeResponse.WorkExperience> govWorkExperience;
    private List<ResumeResponse.ProjectExperience> privateProjectExperience;
    private List<ResumeResponse.SchoolExperience> bankSchoolExperience;
    private List<ResumeResponse.LanguageLevel> languageLevel;
    private List<String> honors;
    private List<String> certificates;
    private List<ResumeResponse.SkillLevel> skillLevel;
    private List<ResumeResponse.Paper> privatePaper;
    private List<String> privateCompetition;
    private List<ResumeResponse.FamilyInfo> familyInfo;
    private List<ResumeResponse.ProfessionalCert> bankProfessionalCert;
    private List<String> bankComputerSkills;
    private List<ResumeResponse.TrainingExperience> bankTrainingExperience;
    private List<ResumeResponse.RewardPunishment> bankRewardsPunishments;

    // 期望与自我评价
    private String bankSalaryExpectation;
    private String bankExpectedLocation;
    private String selfEvaluation;
}
