package com.example.auth.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.example.auth.data.convert.SysUserConvert;
import com.example.auth.data.convert.SysUserRoleConvert;
import com.example.auth.data.dao.SysRoleRepository;
import com.example.auth.data.dao.SysUserRepository;
import com.example.auth.data.dao.SysUserRoleRepository;
import com.example.auth.data.dto.SysUserDTO;
import com.example.auth.data.dto.SysUserRoleDTO;
import com.example.common.model.SecurityUser;
import com.example.auth.data.vo.SysUserReqVO;
import com.example.common.model.SysConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.*;

/**
 * 从数据库中根据用户名查询用户的详细信息，包括权限
 * 数据库设计：角色、用户、权限、角色<->权限、用户<->角色 总共五张表，遵循RBAC设计
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class JwtTokenUserDetailsService implements UserDetailsService {

    private final SysUserRepository sysUserRepository;

    private final SysRoleRepository sysRoleRepository;

    private final SysUserRoleRepository sysUserRoleRepository;

    private final SysUserConvert sysUserConvert;

    private final SysUserRoleConvert sysUserRoleConvert;

    private final Validator validator;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserReqVO sysUserReqVO = new SysUserReqVO();
        sysUserReqVO.setUsername(username);
        sysUserReqVO.setStatus(1);
        Set<ConstraintViolation<SysUserReqVO>> validate = validator.validate(sysUserReqVO);
        if (!validate.isEmpty()) {
            throw new ConstraintViolationException(validate);
        }

        SysUserDTO sysUserDTO = sysUserConvert.converterToDTO(sysUserRepository.findByUsernameAndStatus(sysUserReqVO.getUsername(), sysUserReqVO.getStatus()));
        if (Objects.isNull(sysUserDTO))
            throw new UsernameNotFoundException("用户不存在！");
        //角色
        List<SysUserRoleDTO> sysUserRoleDTOS = sysUserRoleConvert.convertToDTOS(sysUserRoleRepository.findByUserId(sysUserDTO.getId()));
        //该用户的所有权限（角色）
        List<String> roles=new ArrayList<>();
        for (SysUserRoleDTO sysUserRoleDTO : sysUserRoleDTOS) {
            sysRoleRepository.findById(sysUserRoleDTO.getRoleId()).ifPresent(o-> roles.add(SysConstant.ROLE_PREFIX+o.getCode()));
        }
        return SecurityUser.builder()
                .userId(sysUserDTO.getUserId())
                .username(sysUserDTO.getUsername())
                .password(sysUserDTO.getPassword())
                //将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles,String.class)))
                .build();
    }
}
