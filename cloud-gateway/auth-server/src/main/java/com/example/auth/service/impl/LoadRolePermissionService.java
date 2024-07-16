package com.example.auth.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.example.auth.data.dto.SysRoleDTO;
import com.example.auth.data.vo.SysRolePermissionVO;
import com.example.auth.service.PermissionService;
import com.example.common.model.SysConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class LoadRolePermissionService {

    private final RedisTemplate<String,Object> redisTemplate;

    private final PermissionService permissionService;

    @PostConstruct
    public void init(){
        //获取所有的权限
        List<SysRolePermissionVO> list = permissionService.listRolePermission();
        list.parallelStream().peek(k->{
            List<String> roles=new ArrayList<>();
            if (CollectionUtil.isNotEmpty(k.getRoles())){
                for (SysRoleDTO sysRoleDTO : k.getRoles()) {
                    roles.add(SysConstant.ROLE_PREFIX+sysRoleDTO.getCode());
                }
            }
            //放入Redis中
            redisTemplate.opsForHash().put(SysConstant.OAUTH_URLS,k.getUrl(), roles);
        }).collect(Collectors.toList());
    }
}
