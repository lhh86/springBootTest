package com.fh.utils;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//redis连接池
public class RedisUtil {
    //在静态块中初始化
    private static JedisPool jedisPool;

    private RedisUtil(){

    }
    //static是静态块
    static {
        //创建redis池的配置
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(3);
        jedisPoolConfig.setMaxIdle(2);
        jedisPoolConfig.setMaxIdle(1);
        jedisPoolConfig.setMaxWaitMillis(30000);
        //初始化redis连接池
        jedisPool = new JedisPool(jedisPoolConfig,"192.168.116.135",6379);

    }
    //从池中去拿链接
    public static Jedis getJedis(){
      return jedisPool.getResource();
    };
    //链接用完还给池中
    public static void  returnJedis(Jedis jedis){
        jedisPool.returnResource(jedis);
    }
}
