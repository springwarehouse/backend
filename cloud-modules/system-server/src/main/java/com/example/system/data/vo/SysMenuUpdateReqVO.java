package com.example.system.data.vo;

import lombok.Data;

@Data
public class SysMenuUpdateReqVO {

    /** 菜单ID */
    private Long id;

    /** 菜单名称 */
    private String menuName;

    /** 父菜单ID */
    private Long parentId;

    /** 显示顺序 */
    private Integer orderNum;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 类型（M目录 C菜单 F按钮） */
    private String menuType;

}
