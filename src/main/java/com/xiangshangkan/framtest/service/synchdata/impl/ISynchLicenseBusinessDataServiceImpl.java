package com.xiangshangkan.framtest.service.synchdata.impl;

import com.xiangshangkan.framtest.auto.dao.CmDictionaryEntityMapper;
import com.xiangshangkan.framtest.auto.dao.LicenseBusinessInfoEntityMapper;
import com.xiangshangkan.framtest.auto.entity.CmDictionaryEntity;
import com.xiangshangkan.framtest.auto.entity.CmDictionaryEntityExample;
import com.xiangshangkan.framtest.auto.entity.LicenseBusinessInfoEntity;
import com.xiangshangkan.framtest.auto.entity.LicenseBusinessInfoEntityExample;
import com.xiangshangkan.framtest.service.synchdata.ISynchLicenseBusinessDataService;
import com.xiangshangkan.framtest.util.ImportPOIUtil;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Supplier;

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
        Assert.isTrue(companyAddr.contains("市") ,"取不到地址中的市与区");
       Assert.isTrue(companyAddr.contains("区") || companyAddr.contains("镇") ,"取不到地址中的镇与区");
       if (companyAddr.contains("省")) {
           String[] str = companyAddr.split("省");
           companyAddr = str[1];
       }
        String[] str1 = companyAddr.split("市");
        if (str1.length > 1) {
            res.put("city",str1[0] + "市");

            String[] str2 ;
            int fla = 0;
            if (companyAddr.contains("区")) {
                str2 = str1[1].split("区");
                fla= 1;
            } else {
                str2 = str1[1].split("镇");
                fla=2;
            }
            if (str2.length > 1) {
                res.put("cityArea",str2[0]+ (fla ==1 ?"区" :"镇"));
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
    /**
     * @description 组装存放编号
     * @param
     * @return
     * @author      zhouhui
     * @date        2020/1/2 20:02
    */
    public String getStoreSerial(String cityName,String cityAreaName,Integer serial) {
        if (org.apache.commons.lang3.StringUtils.isBlank(cityName) || org.apache.commons.lang3.StringUtils.isBlank(cityAreaName) || Objects.isNull(serial)) {
            return null;
        }
        return cityName+cityAreaName + "-" + String.format("%1$06d", serial);
    }


     @Override
     public void synchBusiness(){
        LicenseBusinessInfoEntityExample entityExample = new LicenseBusinessInfoEntityExample();
         entityExample.createCriteria().andCityIdIsNull();
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
             if (StringUtils.isEmpty(cityName) || StringUtils.isEmpty(cityyAreaName) || StringUtils.isEmpty(addr)) {
                 System.out.println(cityName+cityyAreaName+addr);
                 return;
             }

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

            /* Integer serial =  getNextSerial(province,cityId,cityArea);
             if (Objects.isNull(serial)) {
                 logger.warn("存放编号序列超出范围"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+",serial="+serial+"]");
                 return;
             }
             String storeSerial = getStoreSerial(cityName,cityyAreaName,serial);
             if (Objects.nonNull(model.getSerial()) || !StringUtils.isEmpty(model.getStoreSerial())) {
                 model.setSerial(serial);
                 model.setStoreSerial(storeSerial);
             }*/
             companyAddr = provinceName + cityName + cityyAreaName + addr;

             model.setCityId(cityId);
             model.setProvinceId(province);
             model.setCityArea(cityArea);
             model.setAddr(addr);
             model.setCompanyAddress(companyAddr);

             int res = licenseBusinessInfoEntityMapper.updateByPrimaryKey(model);
             if (res <= 0) {
                 logger.warn("更新记录失败"+ ",[id="+model.getId() + ",companyAddr="+companyAddr+",serial="+"]");
                 return;
             }
         });
     }

     /**
      * @description 导入存放编号数据
      * @param
      * @return
      * @author      zhouhui
      * @date        2020/1/2 20:13
     */
     @Override
     public void importStoreSerial(Workbook workbook){
         Sheet sheet = workbook.getSheet("Sheet1");
         //获取数据总条数，包括表头
         int rowNum =sheet.getLastRowNum() + 1;
         for (int i =1; i< rowNum; i++) {
             Row row = sheet.getRow(i);
             Cell cell = row.createCell(4);
             Cell resCell = row.createCell(5);
             String cityName = ImportPOIUtil.getCellValue(row.getCell(0)) ;
             String cityAreaName = ImportPOIUtil.getCellValue(row.getCell(1));
             String serial = ImportPOIUtil.getCellValue(row.getCell(2));
             String company = ImportPOIUtil.getCellValue(row.getCell(3));

             if (StringUtils.isEmpty(cityName) || StringUtils.isEmpty(cityAreaName) || StringUtils.isEmpty(serial) || StringUtils.isEmpty(company)) {
                cell.setCellValue("数据不完整");
                 resCell.setCellValue("fail");
                continue;
             }
             company = company.trim();
             LicenseBusinessInfoEntityExample entityExample = new LicenseBusinessInfoEntityExample();
             entityExample.createCriteria().andCompanyEqualTo(company);
             entityExample.setOrderByClause("id desc");
             List<LicenseBusinessInfoEntity> entities =licenseBusinessInfoEntityMapper.selectByExample(entityExample);
             if (CollectionUtils.isEmpty(entities)) {
                 cell.setCellValue("未查询到数据");
                 resCell.setCellValue("fail");
                 continue;
             }
             LicenseBusinessInfoEntity entity = entities.get(0);

             Map<String,String > cityAreaAddr = null;
             try {
                 cityAreaAddr =  getCityOrArea(entity.getCompanyAddress());
             } catch (IllegalArgumentException e){
                 e.printStackTrace();
             }
             String addr = "";
             if (!Objects.isNull(cityAreaAddr) && cityAreaAddr.containsKey("addr")){
                 addr = cityAreaAddr.get("addr");
             }

             Optional<CmDictionaryEntity> optional = seletDictionaryByDicName("city",cityName);
             if (!optional.isPresent()) {
                 cell.setCellValue("未找到城市");
                 resCell.setCellValue("fail");
                 continue;
             }
             String cityId = optional.get().getDicid();
             entity.setCityId(cityId);

             optional = seletDictionaryByDicId(optional.get().getSubtype());
             if (!optional.isPresent()) {
                 cell.setCellValue("未找到省份");
                 resCell.setCellValue("fail");
                 continue;
             }
             String provinceName = optional.get().getDictext();
             String province = optional.get().getDicid();
             entity.setProvinceId(province);

            /* optional = seletDictionaryByDicName("cityarea",cityAreaName);
             if (!optional.isPresent()) {
                 cell.setCellValue("未找到区");
                 resCell.setCellValue("fail");
                 continue;
             }*/
            /* String cityArea = optional.get().getDicid();
             entity.setCityArea(cityArea);*/
             entity.setSerial(Integer.valueOf(serial));
             entity.setStoreSerial(getStoreSerial(cityName,cityAreaName,Integer.valueOf(serial)));
           /*  entity.setAddr(addr);*/
            /* String companyAddr = provinceName + cityName + cityAreaName+addr;
             entity.setCompanyAddress(companyAddr);*/
             int res =  licenseBusinessInfoEntityMapper.updateByPrimaryKey(entity);
             if (res <= 0) {
                 resCell.setCellValue("fail");
                 cell.setCellValue("保存数据失败！");
             }
             resCell.setCellValue("success");
         }
     }
}
