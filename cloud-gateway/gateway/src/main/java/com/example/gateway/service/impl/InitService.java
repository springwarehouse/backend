package com.example.gateway.service.impl;

import com.example.common.model.SysConstant;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
@Service
//@Deprecated
public class InitService {

    private final RedisTemplate<String,Object> redisTemplate;

    @PostConstruct
    public void init(){
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/login/info", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/login/admin", Lists.newArrayList("ROLE_admin"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/info", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/order/listByUserId", Lists.newArrayList("ROLE_admin","ROLE_user"));
        redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,"/oauth/logout", Lists.newArrayList("ROLE_admin","ROLE_user"));
    }

}
