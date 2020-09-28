package com.xiangshangkan;

import com.alibaba.fastjson.JSONObject;
import com.xiangshangkan.framtest.auto.entity.ListenMusicEntity;
import com.xiangshangkan.framtest.service.ListenMusicService;
import com.xiangshangkan.framtest.util.ImportPOIUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName ImportJgCertificateTest
 * @Description
 * @Author zhouhui
 * @Date 2019/12/13 14:51
 * @Version 1.0
 */
public class ImportJgCertificateTest extends AbstractTest{

    @Autowired
    private ListenMusicService listenMusicService;


    @Test
    public void testSynchMusic() throws Exception {
        String filePath = "C:\\Users\\user\\Desktop\\听听内容对接文档.xlsx";
        ImportPOIUtil.importThing(filePath,2,(mo->
            listenMusicService.synchMusic(JSONObject.parseObject(mo,ListenMusicEntity.class))
        ));
    }

}
