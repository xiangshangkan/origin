package com.xiangshangkan.framtest.service.importSome;

import com.xiangshangkan.framtest.auto.dao.study.NhrQualificationEntityMapper;
import com.xiangshangkan.framtest.auto.entity.study.NhrQualificationEntity;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * @ClassName ImportNhrQualification
 * @Description TODO
 * @Author zhouhui
 * @Date 2019/3/11 15:52
 * @Version 1.0
 */
@Service("importNhrQualification")
public class ImportNhrQualification {

    @Autowired
    private NhrQualificationEntityMapper mapper;

    /**
     * 批量插入证书信息
     */
    @Transactional(value = "transactionManager", rollbackFor = Exception.class)
    public void indertNhrqualification() throws FileNotFoundException, ParseException {

        InputStream inputStream = new FileInputStream(new File("C://Users//Administrator//Desktop//专业证书.xlsx"));
        XSSFWorkbook workbook = null;
        try {
           workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        for(int i =1; i< 354; i++) {
            XSSFRow row = sheet.getRow(i);
            NhrQualificationEntity entity = new NhrQualificationEntity();
            entity.setWorkerId(row.getCell(0).getStringCellValue());
            entity.setType(new Byte("7"));
            entity.setName("助理证");
            entity.setNumber(row.getCell(3).getStringCellValue());
            String some = row.getCell(4).getStringCellValue();
            entity.setIssuTime(sdf.parse(some));
            some = row.getCell(5).getStringCellValue();
            entity.setBeginTime(sdf.parse(some));
            some = row.getCell(6).getStringCellValue();
            entity.setEndTime(sdf.parse(some));
            some = row.getCell(7).getStringCellValue();
            entity.setContinuingEducationTime(sdf.parse(some));
            entity.setCityId("010003");
            this.mapper.insert(entity);
        }

    }



}
