package com.wk.springredis.service.impl;/**
 * Created by yhopu-pc2 on 2018/6/29.
 */

import com.wk.springredis.service.RedisTestService;

/**
 * @author wk
 * @className RedisTestServiceImpl
 **/
public class RedisTestServiceImpl implements RedisTestService {
    @Override
    public String getTimestamp(String parm) {
        System.out.println("进入方法内部了");
        Long timestamp = System.currentTimeMillis();
        return timestamp.toString();
    }
}
