package com.xiangshangkan;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @ClassName ImportJgCertificateTest
 * @Description 机构证书导入
 * @Author zhouhui
 * @Date 2019/12/13 14:51
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class ImportJgCertificateTest {

    @Test
    public void test1() throws Exception {
        InputStream is = new FileInputStream(new File(""));
        Workbook workbook = new XSSFWorkbook(is);
        Sheet sheet = workbook.getSheetAt(0);
        //获取数据总条数，包括表头
        int rowNum =sheet.getLastRowNum() + 1;
        for (int i = 0; i < rowNum ;i++) {

        }
    }
}
