package com.example.common.data.convert;

import com.example.common.data.dataobject.SysRole;
import com.example.common.data.dto.SysRoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysRoleConvert {
    SysRoleDTO convertToDTO(SysRole sysRole);
}
