package com.xiangshangkan.framtest.service.synchdata;

import com.xiangshangkan.framtest.auto.entity.CmDictionaryEntity;

import java.util.Map;
import java.util.Optional;

/**
 * @ClassName ISynchLicenseBusinessData
 * @Description 同步营业执照正本信息
 * @Author zhouhui
 * @Date 2019/12/31 15:58
 * @Version 1.0
 */
public interface ISynchLicenseBusinessDataService {

    Optional<CmDictionaryEntity> seletDictionaryByDicName(String dicName, String deicText) ;

    Optional<CmDictionaryEntity> seletDictionaryByDicId(String dicId);

    public Map<String,String> getCityOrArea (String companyAddr);

    public void synchBusiness();


}
