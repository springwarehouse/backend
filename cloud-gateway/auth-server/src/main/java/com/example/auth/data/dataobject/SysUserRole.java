package com.example.auth.data.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 用户角色实体类
 */
@Data
@Entity
@Table(name = "sys_user_role")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUserRole  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增主键
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "role_id")
    private Long roleId;
}
