package com.example.auth.data.vo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class SysUserReqVO {

    @NotEmpty(message = "用户名称不能为空")
    private String username;

    private Integer status;
}
