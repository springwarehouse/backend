package com.example.common.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.example.common.data.convert.SysRoleConvert;
import com.example.common.data.convert.SysUserConvert;
import com.example.common.data.convert.SysUserRoleConvert;
import com.example.common.data.dao.SysRoleMapper;
import com.example.common.data.dao.SysUserMapper;
import com.example.common.data.dao.SysUserRoleMapper;
import com.example.common.data.dto.SysRoleDTO;
import com.example.common.data.dto.SysUserDTO;
import com.example.common.data.dto.SysUserRoleDTO;
import com.example.common.model.SecurityUser;
import com.example.common.data.vo.SysUserReqVO;
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

    private final SysUserMapper sysUserMapper;

    private final SysRoleMapper sysRoleMapper;

    private final SysUserRoleMapper sysUserRoleMapper;

    private final SysUserConvert sysUserConvert;

    private final SysUserRoleConvert sysUserRoleConvert;

    private final SysRoleConvert sysRoleConvert;

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

        SysUserDTO sysUserDTO = sysUserConvert.converterToDTO(sysUserMapper.findByUsernameAndStatus(sysUserReqVO));
        if (Objects.isNull(sysUserDTO))
            throw new UsernameNotFoundException("用户不存在！");
        //角色
        List<SysUserRoleDTO> sysUserRoleDTOS = sysUserRoleConvert.convertToDTO(sysUserRoleMapper.findByUserId(sysUserDTO.getId()));
        //该用户的所有权限（角色）
        List<String> roles = new ArrayList<>();
        for (SysUserRoleDTO sysUserRoleDTO : sysUserRoleDTOS) {
            SysRoleDTO sysRoleDTO = sysRoleConvert.convertToDTO(sysRoleMapper.selectById(sysUserRoleDTO.getRoleId()));
            roles.add(sysRoleDTO.getCode());
        }
        return SecurityUser.builder()
                .userId(sysUserDTO.getUserId())
                .username(sysUserDTO.getUsername())
                .password(sysUserDTO.getPassword())
                //将角色放入authorities中
                .authorities(AuthorityUtils.createAuthorityList(ArrayUtil.toArray(roles, String.class)))
                .build();
    }
}
