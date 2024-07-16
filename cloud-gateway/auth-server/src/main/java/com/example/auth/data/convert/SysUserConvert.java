package com.example.auth.data.convert;

import com.example.auth.data.dataobject.SysUser;
import com.example.auth.data.dto.SysUserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface SysUserConvert {

    SysUserDTO converterToDTO(SysUser sysUser);

}
