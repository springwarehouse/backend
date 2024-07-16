package com.example.auth.service;

import com.example.auth.data.vo.SysRolePermissionVO;

import java.util.List;

public interface PermissionService {
    /**
     * 获取所有的url->角色对应关系
     * @return
     */
    List<SysRolePermissionVO> listRolePermission();
}
