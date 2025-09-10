package com.rocklin.offer.common.convert;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocklin.offer.model.dto.request.ResumeAddRequest;
import com.rocklin.offer.model.dto.request.ResumeUpdateRequest;
import com.rocklin.offer.model.dto.response.ResumeResponse;
import com.rocklin.offer.model.entity.Resume;

import java.util.List;

public class ResumeConverter {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /** Entity → DTO */
    public static ResumeResponse toResponse(Resume entity) {
        if (entity == null) return null;

        ResumeResponse dto = new ResumeResponse();
        dto.setId(entity.getId());
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setPhone(entity.getPhone());
        dto.setWechat(entity.getWechat());
        dto.setEmail(entity.getEmail());
        dto.setGender(entity.getGender());
        dto.setBirthday(entity.getBirthday());
        dto.setAddress(entity.getAddress());
        dto.setIdNumber(entity.getIdNumber());

        dto.setGovCurrentResidence(entity.getGovCurrentResidence());
        dto.setGovRegisteredResidence(entity.getGovRegisteredResidence());
        dto.setGovNativePlace(entity.getGovNativePlace());
        dto.setGovGaokaoPlace(entity.getGovGaokaoPlace());
        dto.setGovPoliticalStatus(entity.getGovPoliticalStatus());
        dto.setGovEmergencyName(entity.getGovEmergencyName());
        dto.setGovEmergencyPhone(entity.getGovEmergencyPhone());
        dto.setGovEmergencyRelation(entity.getGovEmergencyRelation());

        dto.setBankPoliticalStatus(entity.getBankPoliticalStatus());
        dto.setBankHeight(entity.getBankHeight());
        dto.setBankWeight(entity.getBankWeight());
        dto.setBankHealth(entity.getBankHealth());
        dto.setBankMarriageStatus(entity.getBankMarriageStatus());
        dto.setBankChildStatus(entity.getBankChildStatus());
        dto.setBankInfoChannel(entity.getBankInfoChannel());
        dto.setBankEmergencyName(entity.getBankEmergencyName());
        dto.setBankEmergencyPhone(entity.getBankEmergencyPhone());
        dto.setBankEmergencyRelation(entity.getBankEmergencyRelation());

        // JSON → List
        dto.setEduExperiences(parseJson(entity.getEduExperiences(), new TypeReference<List<ResumeResponse.EducationExperience>>() {}));
        dto.setPrivateInternship(parseJson(entity.getPrivateInternship(), new TypeReference<List<ResumeResponse.Internship>>() {}));
        dto.setGovCampusPractice(parseJson(entity.getGovCampusPractice(), new TypeReference<List<ResumeResponse.CampusPractice>>() {}));
        dto.setGovWorkExperience(parseJson(entity.getGovWorkExperience(), new TypeReference<List<ResumeResponse.WorkExperience>>() {}));
        dto.setPrivateProjectExperience(parseJson(entity.getPrivateProjectExperience(), new TypeReference<List<ResumeResponse.ProjectExperience>>() {}));
        dto.setBankSchoolExperience(parseJson(entity.getBankSchoolExperience(), new TypeReference<List<ResumeResponse.SchoolExperience>>() {}));
        dto.setLanguageLevel(parseJson(entity.getLanguageLevel(), new TypeReference<List<ResumeResponse.LanguageLevel>>() {}));
        dto.setHonors(parseJson(entity.getHonors(), new TypeReference<List<String>>() {}));
        dto.setCertificates(parseJson(entity.getCertificates(), new TypeReference<List<String>>() {}));
        dto.setSkillLevel(parseJson(entity.getSkillLevel(), new TypeReference<List<ResumeResponse.SkillLevel>>() {}));
        dto.setPrivatePaper(parseJson(entity.getPrivatePaper(), new TypeReference<List<ResumeResponse.Paper>>() {}));
        dto.setPrivateCompetition(parseJson(entity.getPrivateCompetition(), new TypeReference<List<String>>() {}));
        dto.setFamilyInfo(parseJson(entity.getFamilyInfo(), new TypeReference<List<ResumeResponse.FamilyInfo>>() {}));
        dto.setBankProfessionalCert(parseJson(entity.getBankProfessionalCert(), new TypeReference<List<ResumeResponse.ProfessionalCert>>() {}));
        dto.setBankComputerSkills(parseJson(entity.getBankComputerSkills(), new TypeReference<List<String>>() {}));
        dto.setBankTrainingExperience(parseJson(entity.getBankTrainingExperience(), new TypeReference<List<ResumeResponse.TrainingExperience>>() {}));
        dto.setBankRewardsPunishments(parseJson(entity.getBankRewardsPunishments(), new TypeReference<List<ResumeResponse.RewardPunishment>>() {}));

        dto.setBankSalaryExpectation(entity.getBankSalaryExpectation());
        dto.setBankExpectedLocation(entity.getBankExpectedLocation());

        dto.setSelfEvaluation(entity.getSelfEvaluation());

        dto.setCreateTime(entity.getCreateTime());
        dto.setUpdateTime(entity.getUpdateTime());

        return dto;
    }

    /** DTO → Entity */
    public static Resume requestToEntity(ResumeAddRequest dto) {
        if (dto == null) return null;

        Resume entity = new Resume();
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setWechat(dto.getWechat());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setAddress(dto.getAddress());
        entity.setIdNumber(dto.getIdNumber());

        entity.setGovCurrentResidence(dto.getGovCurrentResidence());
        entity.setGovRegisteredResidence(dto.getGovRegisteredResidence());
        entity.setGovNativePlace(dto.getGovNativePlace());
        entity.setGovGaokaoPlace(dto.getGovGaokaoPlace());
        entity.setGovPoliticalStatus(dto.getGovPoliticalStatus());
        entity.setGovEmergencyName(dto.getGovEmergencyName());
        entity.setGovEmergencyPhone(dto.getGovEmergencyPhone());
        entity.setGovEmergencyRelation(dto.getGovEmergencyRelation());

        entity.setBankPoliticalStatus(dto.getBankPoliticalStatus());
        entity.setBankHeight(dto.getBankHeight());
        entity.setBankWeight(dto.getBankWeight());
        entity.setBankHealth(dto.getBankHealth());
        entity.setBankMarriageStatus(dto.getBankMarriageStatus());
        entity.setBankChildStatus(dto.getBankChildStatus());
        entity.setBankInfoChannel(dto.getBankInfoChannel());
        entity.setBankEmergencyName(dto.getBankEmergencyName());
        entity.setBankEmergencyPhone(dto.getBankEmergencyPhone());
        entity.setBankEmergencyRelation(dto.getBankEmergencyRelation());

        // List → JSON
        entity.setEduExperiences(writeJson(dto.getEduExperiences()));
        entity.setPrivateInternship(writeJson(dto.getPrivateInternship()));
        entity.setGovCampusPractice(writeJson(dto.getGovCampusPractice()));
        entity.setGovWorkExperience(writeJson(dto.getGovWorkExperience()));
        entity.setPrivateProjectExperience(writeJson(dto.getPrivateProjectExperience()));
        entity.setBankSchoolExperience(writeJson(dto.getBankSchoolExperience()));
        entity.setLanguageLevel(writeJson(dto.getLanguageLevel()));
        entity.setHonors(writeJson(dto.getHonors()));
        entity.setCertificates(writeJson(dto.getCertificates()));
        entity.setSkillLevel(writeJson(dto.getSkillLevel()));
        entity.setPrivatePaper(writeJson(dto.getPrivatePaper()));
        entity.setPrivateCompetition(writeJson(dto.getPrivateCompetition()));
        entity.setFamilyInfo(writeJson(dto.getFamilyInfo()));
        entity.setBankProfessionalCert(writeJson(dto.getBankProfessionalCert()));
        entity.setBankComputerSkills(writeJson(dto.getBankComputerSkills()));
        entity.setBankTrainingExperience(writeJson(dto.getBankTrainingExperience()));
        entity.setBankRewardsPunishments(writeJson(dto.getBankRewardsPunishments()));

        entity.setBankSalaryExpectation(dto.getBankSalaryExpectation());
        entity.setBankExpectedLocation(dto.getBankExpectedLocation());

        entity.setSelfEvaluation(dto.getSelfEvaluation());
        return entity;
    }
    /** DTO → Entity */
    public static Resume requestToEntity(ResumeUpdateRequest dto) {
        if (dto == null) return null;

        Resume entity = new Resume();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setPhone(dto.getPhone());
        entity.setWechat(dto.getWechat());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.getGender());
        entity.setBirthday(dto.getBirthday());
        entity.setAddress(dto.getAddress());
        entity.setIdNumber(dto.getIdNumber());

        entity.setGovCurrentResidence(dto.getGovCurrentResidence());
        entity.setGovRegisteredResidence(dto.getGovRegisteredResidence());
        entity.setGovNativePlace(dto.getGovNativePlace());
        entity.setGovGaokaoPlace(dto.getGovGaokaoPlace());
        entity.setGovPoliticalStatus(dto.getGovPoliticalStatus());
        entity.setGovEmergencyName(dto.getGovEmergencyName());
        entity.setGovEmergencyPhone(dto.getGovEmergencyPhone());
        entity.setGovEmergencyRelation(dto.getGovEmergencyRelation());

        entity.setBankPoliticalStatus(dto.getBankPoliticalStatus());
        entity.setBankHeight(dto.getBankHeight());
        entity.setBankWeight(dto.getBankWeight());
        entity.setBankHealth(dto.getBankHealth());
        entity.setBankMarriageStatus(dto.getBankMarriageStatus());
        entity.setBankChildStatus(dto.getBankChildStatus());
        entity.setBankInfoChannel(dto.getBankInfoChannel());
        entity.setBankEmergencyName(dto.getBankEmergencyName());
        entity.setBankEmergencyPhone(dto.getBankEmergencyPhone());
        entity.setBankEmergencyRelation(dto.getBankEmergencyRelation());

        // List → JSON
        entity.setEduExperiences(writeJson(dto.getEduExperiences()));
        entity.setPrivateInternship(writeJson(dto.getPrivateInternship()));
        entity.setGovCampusPractice(writeJson(dto.getGovCampusPractice()));
        entity.setGovWorkExperience(writeJson(dto.getGovWorkExperience()));
        entity.setPrivateProjectExperience(writeJson(dto.getPrivateProjectExperience()));
        entity.setBankSchoolExperience(writeJson(dto.getBankSchoolExperience()));
        entity.setLanguageLevel(writeJson(dto.getLanguageLevel()));
        entity.setHonors(writeJson(dto.getHonors()));
        entity.setCertificates(writeJson(dto.getCertificates()));
        entity.setSkillLevel(writeJson(dto.getSkillLevel()));
        entity.setPrivatePaper(writeJson(dto.getPrivatePaper()));
        entity.setPrivateCompetition(writeJson(dto.getPrivateCompetition()));
        entity.setFamilyInfo(writeJson(dto.getFamilyInfo()));
        entity.setBankProfessionalCert(writeJson(dto.getBankProfessionalCert()));
        entity.setBankComputerSkills(writeJson(dto.getBankComputerSkills()));
        entity.setBankTrainingExperience(writeJson(dto.getBankTrainingExperience()));
        entity.setBankRewardsPunishments(writeJson(dto.getBankRewardsPunishments()));

        entity.setBankSalaryExpectation(dto.getBankSalaryExpectation());
        entity.setBankExpectedLocation(dto.getBankExpectedLocation());

        entity.setSelfEvaluation(dto.getSelfEvaluation());
        return entity;
    }

    /** JSON → Object */
    private static <T> T parseJson(String json, TypeReference<T> type) {
        try {
            if (json == null || json.isBlank()) return null;
            return objectMapper.readValue(json, type);
        } catch (Exception e) {
            throw new RuntimeException("JSON 解析失败: " + json, e);
        }
    }

    /** Object → JSON */
    private static String writeJson(Object obj) {
        try {
            if (obj == null) return null;
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException("对象转 JSON 失败: " + obj, e);
        }
    }
}
