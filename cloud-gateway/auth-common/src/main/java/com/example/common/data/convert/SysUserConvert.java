package com.example.common.data.convert;

import com.example.common.data.dataobject.SysUser;
import com.example.common.data.dto.SysUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SysUserConvert {
    SysUserDTO converterToDTO(SysUser sysUser);
}
