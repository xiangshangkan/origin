package com.xiangshangkan.license;

import com.xiangshangkan.framtest.service.synchdata.ISynchLicenseBusinessDataService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void testGetCityOrArea() throws Exception {
        String companyAddr = "深圳市罗湖区黄贝街道罗芳路通发花园1栋105";
        Map<String,String> res = businessDataService.getCityOrArea(companyAddr);
        System.out.println("城市:"+ res.get("city") +"，区域:" + res.get("cityArea") +"，具体地址:"+ res.get("addr"));
    }
}
