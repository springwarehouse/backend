package com.example.auth.data.convert;

import com.example.auth.data.dataobject.SysPermission;
import com.example.auth.data.dto.SysPermissionDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SysPermissionConvert {

    List<SysPermissionDTO> convertToDTOS(List<SysPermission> permissions);

}
