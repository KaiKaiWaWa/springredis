package com.wk.springredis.utils;/**
 * Created by yhopu-pc2 on 2018/6/28.
 */

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author wk
 * @className MethodCacheInterceptor
 **/
@Component
public class MethodCacheInterceptor implements MethodInterceptor {
    @Autowired
    private RedisTemplate<Serializable,Object> redisTemplate;
    private Long defaultCacheExpireTime = 10L;//设置缓存过期时间，这里设置10s
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        Object value = null;
        String targetName = methodInvocation.getThis().getClass().getName();
        String methodName = methodInvocation.getMethod().getName();

        Object [] arguments = methodInvocation.getArguments();
        String key = getCacheKey(targetName,methodName,arguments);
        if(exists(key)){
            return getCache(key);
        }
        //写入缓存
        value = methodInvocation.proceed();
        if (value != null){
            String tkey = key;
            Object tvalue = value;
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();
        }

        return null;
    }

    /**
     * 创建缓存Key
     * @param targetName
     * @param methodName
     * @param arguments
     * @return
     */
    private String getCacheKey(String targetName,String methodName,Object[] arguments) {
        StringBuffer sbu = new StringBuffer();
        sbu.append(targetName).append("_").append(methodName);
        if ((arguments!=null)&&(arguments.length!=0)){
            for (int i=0;i < arguments.length;i++){
                sbu.append("_").append(arguments[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    public boolean exists(final String key){
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public Object getCache(final String key){
        Object result = null;
        ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
        result = operations.get(key);
        return result;
    }

    public boolean setCache(String key,Object value,Long expireTime){
        boolean result = false;
        try{
            ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
            result = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
