package com.wk.springredis.test;/**
 * Created by yhopu-pc2 on 2018/6/29.
 */

import com.wk.springredis.service.RedisTestService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * @author wk
 * @className TestMain
 **/
public class TestMain extends SpringTestCase {
    @Autowired
    private RedisTestService redisTestService;

    @Test
    public void getTimestampTest() throws InterruptedException {
        System.out.println("第一次调用：" + redisTestService.getTimestamp("param"));
        Thread.sleep(2000);
        System.out.println("2秒之后调用：" + redisTestService.getTimestamp("param"));
        Thread.sleep(11000);
        System.out.println("再过11秒之后调用：" + redisTestService.getTimestamp("param"));
        System.out.println("再等待10S，查看redis的该条数据，已经被清空！！");
    }

//    @Autowired
//    public RedisTemplate<Serializable, Object> redisTemplate;
//
//    @Test
//    public void setTest() {
//        //设值
//        redisTemplate.opsForValue().set("key1", "王凯");
//        redisTemplate.opsForValue().set("key2", "哈哈");
//        //取值
//        String key1 = (String) redisTemplate.opsForValue().get("key1");
//        System.out.println(key1);
//    }

}
