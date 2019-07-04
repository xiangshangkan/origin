package com.xiangshangkan.framtest.web.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: hsc
 * @date: 2018/6/19 14:44
 * @description 文件描述
 */

@RestController
@RequestMapping(value = "/kafka")
public class KafkaController {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Value("nwbs-optimizeNetwork-task")
    private  String optimizeTopic ;

    @Value("nwbs-business-task")
    private String businessTopic;

    @RequestMapping(value = "/producer" , method = RequestMethod.POST)
    public void producer(@RequestBody JSONObject params){
        kafkaTemplate.send(optimizeTopic,params.toJSONString()+"optimizeTopic");
        kafkaTemplate.send(businessTopic,params.toJSONString()+"businessTopic");
        ListenableFuture<SendResult<String, String>> listenableFuture =  kafkaTemplate.sendDefault(params.toJSONString());;
        //发送成功回调
        SuccessCallback<SendResult<String, String>> successCallback = new SuccessCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                //成功业务逻辑
                System.out.println("onSuccess");
            }
        };
        //发送失败回调
        FailureCallback failureCallback = new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                //失败业务逻辑
                System.out.println("onFailure");
            }
        };
        listenableFuture.addCallback(successCallback, failureCallback);
    }
}