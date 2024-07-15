package com.example.common.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.data.dataobject.SysUser;
import com.example.common.data.vo.SysUserReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
    default SysUser findByUsernameAndStatus(@Param("sysUserReqVO") SysUserReqVO sysUserReqVO) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUserReqVO.getUsername());
        queryWrapper.eq(SysUser::getStatus, sysUserReqVO.getStatus());
        return selectOne(queryWrapper);
    }
}
