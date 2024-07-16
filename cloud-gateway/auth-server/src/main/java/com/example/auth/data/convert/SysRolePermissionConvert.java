package com.example.auth.data.convert;

import com.example.auth.data.dataobject.SysRolePermission;
import com.example.auth.data.dto.SysRolePermissionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysRolePermissionConvert {

    List<SysRolePermissionDTO> convertToDTOS(List<SysRolePermission> rolePermissions);

}
