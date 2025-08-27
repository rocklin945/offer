package com.rocklin.offer;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.rocklin.offer.mapper.JobInfoMapper;
import com.rocklin.offer.model.dto.JobInfoImportDTO;
import com.rocklin.offer.model.entity.JobInfo;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class ExcelExportTest {
    private final JobInfoMapper jobInfoMapper;

    @Test
    public void testExportJobInfo() {
        // 1. 查询数据库数据
        List<JobInfo> entityList = jobInfoMapper.selectAll();

        // 2. 转换成 DTO
        List<JobInfoImportDTO> dataList = entityList.stream().map(e -> {
            JobInfoImportDTO dto = new JobInfoImportDTO();
            dto.setCompanyName(e.getCompanyName());
            dto.setCompanyType(e.getCompanyType());
            dto.setIndustry(e.getIndustry());
            dto.setRecruitType(e.getRecruitType());
            dto.setWorkLocation(e.getWorkLocation());
            dto.setRecruitTarget(e.getRecruitTarget());
            dto.setPositionName(e.getPositionName());

            // 只保留日期部分（清除时间）
            dto.setStartTime(truncateTime(e.getStartTime()));

            dto.setRelatedLink(e.getRelatedLink());
            dto.setAnnouncement(e.getAnnouncement());
            dto.setReferralCode(e.getReferralCode());
            dto.setRemark(e.getRemark());
            return dto;
        }).collect(Collectors.toList());

        if (dataList == null || dataList.isEmpty()) {
            System.out.println("没有数据，导出取消");
            return;
        }

        // 3. 设置导出文件名和 sheet 名
        String fileName = "招聘信息.xlsx";
        String sheetName = "招聘信息";

        // 4. 调用 EasyExcel 导出
        try {
            EasyExcel.write(fileName, JobInfoImportDTO.class)
                    // 自动列宽
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .sheet(sheetName)
                    .doWrite(dataList);
            System.out.println("导出成功：" + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导出失败：" + e.getMessage());
        }
    }

    /**
     * 将 Date 的时间部分清零，只保留日期
     */
    private Date truncateTime(Date date) {
        if (date == null) return null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
