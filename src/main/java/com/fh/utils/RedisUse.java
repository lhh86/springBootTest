package com.fh.utils;

import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUse {

    public static void set(String key,String value){
        Jedis jedis = RedisUtil.getJedis();
        jedis.set(key,value);
        RedisUtil.returnJedis(jedis);
    }

    public static void set(String key,String value,int seconds){
        Jedis jedis = RedisUtil.getJedis();
        jedis.setex(key,seconds,value);
        RedisUtil.returnJedis(jedis);
    }

    public static String get(String key){
        Jedis jedis = RedisUtil.getJedis();
        String value=jedis.get(key);
        RedisUtil.returnJedis(jedis);
        return value;
    }

    public static void hset(String key,String filed,String value){
        Jedis jedis = RedisUtil.getJedis();
        Long hset = jedis.hset(key, filed, value);
        RedisUtil.returnJedis(jedis);
    }

    public static long  hlen(String key){
        Jedis jedis = RedisUtil.getJedis();
        Long hlen = jedis.hlen(key);
        RedisUtil.returnJedis(jedis);
        return hlen;
    }

    public static  String hget(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        String hget = jedis.hget(key, filed);
        RedisUtil.returnJedis(jedis);
        return hget;
    }

    public static List<String> hvals(String key){
        Jedis jedis = RedisUtil.getJedis();
        List<String> hvals = jedis.hvals(key);
        RedisUtil.returnJedis(jedis);
        return hvals;
    }

    public static Long hdel(String key,String filed){
        Jedis jedis = RedisUtil.getJedis();
        Long hdel = jedis.hdel(key, filed);
        RedisUtil.returnJedis(jedis);
        return hdel;
    }

    public static String getAreaNames(String key){
        Jedis jedis = RedisUtil.getJedis();
        String getAreaNames=jedis.get(key);
        RedisUtil.returnJedis(jedis);
        return getAreaNames;
    }

    public static boolean exists(String key){
        Jedis jedis = RedisUtil.getJedis();
        Boolean exists = jedis.exists(key);
        RedisUtil.returnJedis(jedis);
        return exists;
    }
}
