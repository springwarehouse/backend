package com.example.system.data.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 菜单表 sys_menu
 */
@Data
@TableName("sys_menu")
public class SysMenu {

    /** 菜单ID */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /** 菜单名称 */
    @TableField("menu_name")
    private String menuName;

    /** 父菜单ID */
    @TableField("parent_id")
    private Long parentId;

    /** 显示顺序 */
    @TableField("order_num")
    private Integer orderNum;

    /** 路由地址 */
    @TableField("path")
    private String path;

    /** 组件路径 */
    @TableField("component")
    private String component;

    /** 类型（M目录 C菜单 F按钮） */
    @TableField("menu_type")
    private String menuType;

    /** 创建时间 */
    @TableField("create_time")
    private Date createTime;

    /** 更新时间 */
    @TableField("update_time")
    private Date updateTime;

}
