package com.example.auth.data.convert;

import com.example.auth.data.dataobject.SysRole;
import com.example.auth.data.dto.SysRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysRoleConvert {

    SysRoleDTO convertToDTO(SysRole sysRole);

    List<SysRoleDTO> convertToDTOS(List<SysRole> sysRoles);

}
