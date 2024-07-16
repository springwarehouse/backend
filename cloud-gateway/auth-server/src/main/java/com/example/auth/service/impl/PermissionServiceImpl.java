package com.example.auth.service.impl;

import com.example.auth.data.convert.SysPermissionConvert;
import com.example.auth.data.convert.SysRoleConvert;
import com.example.auth.data.convert.SysRolePermissionConvert;
import com.example.auth.data.dao.SysPermissionRepository;
import com.example.auth.data.dao.SysRolePermissionRepository;
import com.example.auth.data.dao.SysRoleRepository;
import com.example.auth.data.dto.SysPermissionDTO;
import com.example.auth.data.dto.SysRoleDTO;
import com.example.auth.data.dto.SysRolePermissionDTO;
import com.example.auth.data.vo.SysRolePermissionVO;
import com.example.auth.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PermissionServiceImpl implements PermissionService {

    private final SysRoleRepository sysRoleRepository;

    private final SysPermissionRepository sysPermissionRepository;

    private final SysRolePermissionRepository sysRolePermissionRepository;

    private final SysPermissionConvert sysPermissionConvert;

    private final SysRolePermissionConvert sysRolePermissionConvert;

    private final SysRoleConvert sysRoleConvert;

    @Override
    public List<SysRolePermissionVO> listRolePermission() {
        List<SysRolePermissionVO> list=new ArrayList<>();
        List<SysPermissionDTO> sysPermissionDTOS = sysPermissionConvert.convertToDTOS(sysPermissionRepository.findAll());
        for (SysPermissionDTO sysPermissionDTO : sysPermissionDTOS) {
            List<SysRolePermissionDTO> sysRolePermissionDTOS = sysRolePermissionConvert.convertToDTOS(sysRolePermissionRepository.findByPermissionId(sysPermissionDTO.getId()));
            List<SysRoleDTO> sysRoleDTOS = sysRoleConvert.convertToDTOS(sysRolePermissionDTOS.stream().map(k -> sysRoleRepository.findById(k.getRoleId()).get()).collect(Collectors.toList()));
            SysRolePermissionVO vo = SysRolePermissionVO.builder()
                    .permissionId(sysPermissionDTO.getId())
                    .url(sysPermissionDTO.getUrl())
                    .permissionName(sysPermissionDTO.getName())
                    .roles(sysRoleDTOS)
                    .build();
            list.add(vo);
        }
        return list;
    }
}
