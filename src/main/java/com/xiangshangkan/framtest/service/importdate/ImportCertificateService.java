package com.xiangshangkan.framtest.service.importdate;

import org.apache.poi.ss.usermodel.Workbook;

/**
 * @ClassName ImportCertificateService
 * @Description 证书导入
 * @Author zhouhui
 * @Date 2019/12/16 11:12
 * @Version 1.0
 */
public interface ImportCertificateService {

    public void importCertificate( Workbook workbook);
}
