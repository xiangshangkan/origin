package com.xiangshangkan;

import com.xiangshangkan.framtest.service.importdate.ImportCertificateService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private ImportCertificateService importCertificateService;



    @Test
    public void test1() throws Exception {
        File file = new File("C:\\Users\\Administrator\\Desktop\\证书导入.xls");
        System.out.println(file.getName());
        InputStream is = new FileInputStream(file);
        Workbook workbook = new HSSFWorkbook(is);
        importCertificateService.importCertificate(workbook);
    }

}
