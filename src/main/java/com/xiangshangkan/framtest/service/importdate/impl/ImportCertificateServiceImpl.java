package com.xiangshangkan.framtest.service.importdate.impl;

import com.xiangshangkan.framtest.auto.dao.CertificateInfoEntityMapper;
import com.xiangshangkan.framtest.auto.dao.CertificateKeepLogEntityMapper;
import com.xiangshangkan.framtest.auto.entity.CertificateInfoEntity;
import com.xiangshangkan.framtest.auto.entity.CertificateKeepLogEntity;
import com.xiangshangkan.framtest.service.importdate.ImportCertificateService;
import com.xiangshangkan.framtest.util.ImportPOIUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @ClassName ImportCertificateServiceImpl
 * @Description 证书导入
 * @Author zhouhui
 * @Date 2019/12/16 11:12
 * @Version 1.0
 */
@Service
public class ImportCertificateServiceImpl implements ImportCertificateService {

    Logger logger = LoggerFactory.getLogger(ImportCertificateService.class);

    @Autowired
    private CertificateInfoEntityMapper certificateInfoEntityMapper;
    @Autowired
    private CertificateKeepLogEntityMapper certificateKeepLogEntityMapper;


    @Transactional(value = "transactionManager" ,rollbackFor = Exception.class)
    @Override
    public void importCertificate( Workbook workbook) {
        Sheet sheet = workbook.getSheet("闲置的证书数据录入");
        //获取数据总条数，包括表头
        int rowNum =sheet.getLastRowNum() + 1;
        Supplier<CertificateInfoEntity> supplier = CertificateInfoEntity::new;
        Supplier<CertificateKeepLogEntity> supplier1 =  CertificateKeepLogEntity::new;
        for (int i = 1; i < rowNum ;i++) {
            CertificateInfoEntity entity = supplier.get();
            Row row = sheet.getRow(i);
            //默认闲置中
            entity.setStatus(2);
            entity.setCreateTime(new Date());
            entity.setPersonName(row.getCell(0).getStringCellValue());
            if (StringUtils.isBlank(entity.getPersonName())) {
                throw new RuntimeException();
            }
            entity.setPersonNumber(row.getCell(1).getStringCellValue());
            if (StringUtils.isBlank(entity.getPersonNumber())) {
                throw new RuntimeException();
            }
            entity.setIdCard(row.getCell(2).getStringCellValue().replace(",",""));
            if (StringUtils.isBlank(entity.getIdCard())) {
                throw new RuntimeException();
            }
            entity.setPersonDept(row.getCell(3).getStringCellValue());
            if (StringUtils.isBlank(entity.getPersonDept())) {
                throw new RuntimeException();
            }
            entity.setPersonDeptNumber(row.getCell(4).getStringCellValue());
            if (StringUtils.isBlank(entity.getPersonDeptNumber())) {
                throw new RuntimeException();
            }
            String sourceName = row.getCell(5).getStringCellValue();
            entity.setSource(getSource(sourceName));
            if (Objects.isNull(entity.getSource())) {
                throw new RuntimeException();
            }
            String typeName = row.getCell(6).getStringCellValue();
            entity.setType(getType(typeName));
            if (Objects.isNull(entity.getType())) {
                throw new RuntimeException();
            }

            entity.setManagementNumber(ImportPOIUtil.getCellValue(row.getCell(7)).trim());
            if (StringUtils.isBlank(entity.getManagementNumber())) {
                logger.error("证书编号"+ entity.getCertificateNumber()+",身份证"+entity.getIdCard());
                throw new RuntimeException();
            }
            entity.setStartKeepTime(getDate2(row.getCell(8).getStringCellValue()));
            if (Objects.isNull(entity.getStartKeepTime())) {
                throw new RuntimeException();
            }
            entity.setExpireTime(getDate2(row.getCell(9).getStringCellValue()));
            if (Objects.isNull(entity.getExpireTime())) {
                continue;
            }
            entity.setContinuingEducationTime(getDate2(row.getCell(10).getStringCellValue()));
            if (Objects.isNull(entity.getContinuingEducationTime())) {
                throw new RuntimeException();
            }
            entity.setCustodianNumber(row.getCell(11).getStringCellValue());
            if (Objects.isNull(entity.getCustodianNumber())) {
                throw new RuntimeException();
            }
            entity.setCustodianDept(row.getCell(12).getStringCellValue());
            if (Objects.isNull(entity.getCustodianDept())) {
                throw new RuntimeException();
            }
            entity.setCusotidanDeptNumber(row.getCell(13).getStringCellValue());
            if (StringUtils.isBlank(entity.getCusotidanDeptNumber())) {
                throw new RuntimeException();
            }
            entity.setCustodian(row.getCell(14).getStringCellValue());
            if (StringUtils.isBlank(entity.getCustodian())) {
                throw new RuntimeException();
            }
            certificateInfoEntityMapper.insert(entity);
            CertificateKeepLogEntity logEntity = supplier1.get();
            logEntity.setCertificateId(entity.getId());
            logEntity.setCreateTime(new Date());
            logEntity.setStartKeepTime(entity.getStartKeepTime());
            logEntity.setEndKeepTime(entity.getEndKeepTime());
            logEntity.setStatus(1);
            certificateKeepLogEntityMapper.insert(logEntity);

        }
    }


    /**
     * 1 外部证书   2 内部证书
     * @param name
     * @return
     */
    private Integer getSource(String name) {
        if ("内部证书".equals(name)) {
            return 2;
        } else {
            return null;
        }
    }

    /**
     * 证书类型: 1 全国经纪人证 2 全国协理证 3 地方证书
     * @param name
     * @return
     */
    private Integer getType(String name) {
        if ("全国经纪人证".equals(name)) {
            return 1;
        } else if ("全国协理证".equals(name)) {
            return 2;
        }
        return null;
    }

    /**
     * 获取日期
     * @param dateText
     * @param format
     * @return
     */
    public Date getDate(String dateText,String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(dateText);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Date getDate2(String dateText){
        Date startKeepTime = getDate(dateText,"yyyy/MM/dd");
        if (Objects.isNull(startKeepTime)) {
            startKeepTime = getDate(dateText,"yyyy-MM-dd");
        }
        return startKeepTime;
    }
}
