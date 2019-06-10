package com.higgs.network.wallet.service;

import java.util.Map;

public interface RedisService {
    Map<Object,Object> getSpringSession(String key);
}
