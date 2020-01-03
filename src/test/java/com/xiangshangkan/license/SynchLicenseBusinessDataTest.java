package com.xiangshangkan.license;

import com.xiangshangkan.framtest.service.synchdata.ISynchLicenseBusinessDataService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName SynchLicenseBusinessDataTest
 * @Description 证书同步测试类
 * @Author zhouhui
 * @Date 2019/12/31 19:19
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class SynchLicenseBusinessDataTest {

    @Autowired
    private ISynchLicenseBusinessDataService businessDataService;


    @Test
    public void testSynchBusiness() throws Exception {
        businessDataService.synchBusiness();
    }

    @Test
    public void testImportStoreSerial() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\证照编号核对.xlsx");
        System.out.println(file.getName());
        InputStream is = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(is);
        try {
            businessDataService.importStoreSerial(workbook);
        } catch (Exception e) {
            e.printStackTrace();
        }
        workbook.write(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\证照编号核对反馈.xlsx")));
    }

    @Test
    public void testGetCityOrArea() throws Exception {
        String companyAddr = "深圳市罗湖区黄贝街道罗芳路通发花园1栋105";
        Map<String,String> res = businessDataService.getCityOrArea(companyAddr);
        System.out.println("城市:"+ res.get("city") +"，区域:" + res.get("cityArea") +"，具体地址:"+ res.get("addr"));
    }
}
