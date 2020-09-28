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
public class RedisTestDemo extends AbstractTest{
    //定义jedis连接池
    @Autowired
    private JedisPool jedisPool;

    @Test
    public void  testJedisClient() throws Exception {
        Jedis jedis = null;
        //获取redis实例
        jedis = jedisPool.getResource();
        jedis.set("idpath","i love you baby");
        jedis.get("idpath");
        jedis.get("dotno");
        System.out.println(jedis.get("idpath"));
        System.out.println(jedis.get("dotno"));
        System.out.println(jedis.get("path"));
        //取值
        String name = jedis.get("name");
        //输出控制台
        System.out.println(name);

    }
}
