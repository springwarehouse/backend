package com.example.auth.data.convert;

import com.example.auth.data.dataobject.SysUserRole;
import com.example.auth.data.dto.SysUserRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysUserRoleConvert {

    List<SysUserRoleDTO> convertToDTOS(List<SysUserRole> sysUserRoles);

}
