package com.xiangshangkan.framtest.web.timer;

import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * @Description: ${description}
 * @Author: Zohar
 * @Date: 2020/6/24 18:41
 * @Version: 1.0
 */
@Component("profitScheduler")
public class ProfitScheduler {

    public void execute(){
        Logger.getGlobal().info("start 执行定时任务");
        Logger.getGlobal().info("end 执行定时任务");
    }
}
