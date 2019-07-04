package com.xiangshangkan.framtest.service.importSome;

import com.xiangshangkan.framtest.auto.dao.study.NhrQualificationEntityMapper;
import com.xiangshangkan.framtest.auto.entity.study.NhrQualificationEntity;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.text.DecimalFormat;
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

        InputStream inputStream = new FileInputStream(new File("C://Users//Administrator//Desktop//全国证和协理证信息在人事系统批量导入的模板.xlsx"));
        XSSFWorkbook workbook = null;
        try {
           workbook = new XSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for(int i =1; i< 628; i++) {
            XSSFRow row = sheet.getRow(i);
            NhrQualificationEntity entity = new NhrQualificationEntity();
            entity.setWorkerId(getCellValue(row.getCell(0)));
            entity.setType(new Byte("6"));
            entity.setName("全国协理证");
            entity.setNumber(getCellValue(row.getCell(1)));
            String some = getCellValue(row.getCell(2));
            entity.setIssuTime(sdf.parse(some));
            some = getCellValue(row.getCell(3));
            entity.setBeginTime(sdf.parse(some));
            some = getCellValue(row.getCell(4));
            entity.setEndTime(sdf.parse(some));
            some = getCellValue(row.getCell(5));
            entity.setContinuingEducationTime(sdf.parse(some));
            entity.setCityId("010003");
            this.mapper.insert(entity);
        }

    }


    private String getCellValue(Cell cell) {
        if (cell == null || "".equals(cell.toString().trim())) {
            return "";
        }
        CellType cellType = cell.getCellTypeEnum();
        DecimalFormat dft = new DecimalFormat("#.#");
        switch (cellType) {
            case _NONE:
            case BLANK:
            case ERROR:
                return "";
            case STRING:
                return String.valueOf(cell.getStringCellValue()).trim();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue()).trim();
            case FORMULA:
                return String.valueOf(cell.getCellFormula()).trim();
            case NUMERIC:
                return String.valueOf(dft.format(cell.getNumericCellValue())).trim();
            default:
                return "";
        }
    }



}
