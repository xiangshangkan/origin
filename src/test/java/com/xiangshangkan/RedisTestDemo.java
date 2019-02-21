package com.xiangshangkan;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author : zhouhui
 * @date : 2019/2/21 15:07
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext.xml")
public class RedisTestDemo {
    //定义jedis连接池
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void  testJedisClient() throws Exception {
        Jedis jedis = null;
        //获取redis实例
        jedis = jedisPool.getResource();
        //取值
        String name = jedis.get("name");
        //输出控制台
        System.out.println(name);

    }
}
