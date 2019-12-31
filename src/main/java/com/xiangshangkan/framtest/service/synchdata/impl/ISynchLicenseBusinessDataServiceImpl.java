package com.xiangshangkan.framtest.service.synchdata.impl;

import com.xiangshangkan.framtest.auto.dao.CmDictionaryEntityMapper;
import com.xiangshangkan.framtest.auto.dao.LicenseBusinessInfoEntityMapper;
import com.xiangshangkan.framtest.auto.entity.CmDictionaryEntity;
import com.xiangshangkan.framtest.auto.entity.CmDictionaryEntityExample;
import com.xiangshangkan.framtest.auto.entity.LicenseBusinessInfoEntity;
import com.xiangshangkan.framtest.auto.entity.LicenseBusinessInfoEntityExample;
import com.xiangshangkan.framtest.service.synchdata.ISynchLicenseBusinessDataService;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @ClassName ISynchLicenseBusinessDataServiceImpl
 * @Description 同步营业执照正本信息
 * @Author zhouhui
 * @Date 2019/12/31 16:02
 * @Version 1.0
 */
@Service
public class ISynchLicenseBusinessDataServiceImpl implements ISynchLicenseBusinessDataService{

    Logger logger = LoggerFactory.getLogger(ISynchLicenseBusinessDataServiceImpl.class);

    @Autowired
    private CmDictionaryEntityMapper cmDictionaryEntityMapper;
    @Autowired
    private LicenseBusinessInfoEntityMapper licenseBusinessInfoEntityMapper;

    /**
     * @description 查询Dictionary
     * @param
     * @return
     * @author      zhouhui
     * @date        2019/12/31 16:43
    */
    @Override
    public Optional<CmDictionaryEntity> seletDictionaryByDicName(String dicName, String deicText) {
        CmDictionaryEntityExample entityExample = new CmDictionaryEntityExample();
        entityExample.createCriteria().andDicnameEqualTo(dicName).andDictextEqualTo(deicText).andStatusEqualTo("Y");
        return cmDictionaryEntityMapper.selectByExample(entityExample).stream().findFirst();
    }
   /**
    * @description  查询Dictionary
    * @param
    * @return
    * @author      zhouhui
    * @date        2019/12/31 16:59
   */
    @Override
    public Optional<CmDictionaryEntity>  seletDictionaryByDicId(String dicId){
        CmDictionaryEntityExample entityExample = new CmDictionaryEntityExample();
        entityExample.createCriteria().andStatusEqualTo("Y").andDicidEqualTo(dicId);
        return cmDictionaryEntityMapper.selectByExample(entityExample).stream().findFirst();
    }
   /**
    * @description 将公司地址拆分成市，区，具体地址
    * @param
    * @return
    * @author      zhouhui
    * @date        2019/12/31 17:00
   */
   @Override
    public Map<String,String> getCityOrArea (String companyAddr){
        Map<String,String> res = new HashMap<>();
        Assert.isTrue(!StringUtils.isEmpty(companyAddr),"公司地址为空！");
        Assert.isTrue(companyAddr.contains("市") && companyAddr.contains("区"),"取不到地址中的市与区");
        String[] str1 = companyAddr.split("市");
        if (str1.length > 1) {
            res.put("city",str1[0] + "市");
            String[] str2 = str1[1].split("区");
            if (str2.length > 1) {
                res.put("cityArea",str2[0]+"区");
                res.put("addr",str2[1]);
            }
        }
        return res;
    }

    /**
     * @description 获取区域最大存放编号序列
     * @param
     * @return
     * @author      zhouhui
     * @date        2019/12/31 18:23
    */
    private Optional<Integer> getMaxSerial(String provinceId, String cityId, String cityArea) {
        LicenseBusinessInfoEntityExample entityExample = new LicenseBusinessInfoEntityExample();
        entityExample.createCriteria().andProvinceIdEqualTo(provinceId).andCityIdEqualTo(cityId)
                .andCityAreaEqualTo(cityArea);
        entityExample.setOrderByClause("serial desc");
        List<LicenseBusinessInfoEntity> entities = licenseBusinessInfoEntityMapper.selectByExampleWithRowbounds(entityExample,new RowBounds(0,1));
        if (CollectionUtils.isEmpty(entities)) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(entities.get(0).getSerial());
        }
    }
    /**
     * @description 获取区域下一个存放编号序列
     * @param
     * @return
     * @author      zhouhui
     * @date        2019/12/31 18:26
    */
    private Integer getNextSerial(String provinceId, String cityId, String cityArea) {
        Optional optional = getMaxSerial(provinceId,cityId,cityArea);
        if (!optional.isPresent()) {
            return 1;
        } else {
            Integer serial = (Integer) optional.get();
            if (serial >= 999999) {
                return null;
            } else {
                return ++serial;
            }
        }
     }

     @Override
     public void synchBusiness(){
        LicenseBusinessInfoEntityExample entityExample = new LicenseBusinessInfoEntityExample();
        List<LicenseBusinessInfoEntity> soem = licenseBusinessInfoEntityMapper.selectByExample(entityExample);
         soem.forEach(model->{
             String companyAddr = model.getCompanyAddress();
             Map<String,String > cityAreaAddr = null;

             try {
                  cityAreaAddr =  getCityOrArea(companyAddr);
             } catch (IllegalArgumentException e){
                 logger.warn(e.getMessage() + ",[id="+model.getId() + ",companyAddr="+companyAddr+"]");
                 return;
             }
             String cityName = cityAreaAddr.get("city");
             String cityyAreaName = cityAreaAddr.get("cityArea");
             String addr = cityAreaAddr.get("addr");

             Optional<CmDictionaryEntity> optional = seletDictionaryByDicName("city",cityName);
             if (!optional.isPresent()) {
                 logger.warn("未找到城市"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+"]");
                 return;
             }
             String cityId = optional.get().getDicid();

             optional = seletDictionaryByDicId(optional.get().getSubtype());
             if (!optional.isPresent()) {
                 logger.warn("未找到省份"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+"]");
                 return;
             }
             String provinceName = optional.get().getDictext();
             String province = optional.get().getDicid();

             optional = seletDictionaryByDicName("cityarea",cityyAreaName);
             if (!optional.isPresent()) {
                 logger.warn("未找到区"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+"]");
                 return;
             }
             String cityArea = optional.get().getDicid();

             Integer serial =  getNextSerial(province,cityId,cityArea);
             if (Objects.isNull(serial)) {
                 logger.warn("存放编号序列超出范围"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+",serial="+serial+"]");
                 return;
             }
             String storeSerial = cityName + cityyAreaName + serial;
             companyAddr = provinceName + cityName + cityyAreaName + addr;

             model.setCityId(cityId);
             model.setProvinceId(province);
             model.setCityArea(cityArea);
             model.setAddr(addr);
             model.setCompanyAddress(companyAddr);
             model.setSerial(serial);
             model.setStoreSerial(storeSerial);
             int res = licenseBusinessInfoEntityMapper.updateByPrimaryKey(model);
             if (res <= 0) {
                 logger.warn("更新记录失败"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+",serial="+serial+"]");
                 return;
             }
         });

     }
}
