package com.example.common.data.convert;

import com.example.common.data.dataobject.SysUserRole;
import com.example.common.data.dto.SysUserRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysUserRoleConvert {
    List<SysUserRoleDTO> convertToDTO(List<SysUserRole> sysUserRoles);
}
