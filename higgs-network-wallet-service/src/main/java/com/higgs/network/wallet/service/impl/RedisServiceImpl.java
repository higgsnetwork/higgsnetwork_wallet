package com.higgs.network.wallet.service.impl;

import com.higgs.network.wallet.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;

@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    protected RedisTemplate<Serializable, Serializable> redisTemplate;

    public RedisServiceImpl(){}

    public Map<Object,Object> getSpringSession(String key){
        Map<Object,Object> resultMap= redisTemplate.opsForHash().entries(key);
        return resultMap;
    }

}
