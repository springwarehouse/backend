package com.example.system.service;

import com.example.system.core.page.PageResult;
import com.example.system.data.vo.*;

public interface ISysMenuService {

    /**
     * 查询所有菜单
     *
     * @param reqVO
     * @return
     */
    PageResult<SysMenuRespVO> queryMenu(SysMenuReqVO reqVO);

    //PageResult<SysMenuReqVO>

    /**
     * 菜单详情
     *
     * @param detailReqVO 菜单ID
     * @return
     */
    SysMenuDetailRespVO detailMenu(SysMenuDetailReqVO detailReqVO);

    /**
     * 创建菜单
     *
     * @param createReqVO 菜单表单
     * @return
     */
    Long createMenu(SysMenuCreateReqVO createReqVO);

    /**
     * 更新菜单
     *
     * @param updateReqVO 菜单表单
     */
    void updateMenu(SysMenuUpdateReqVO updateReqVO);

    /**
     * 删除菜单
     *
     * @param removeReqVO 菜单ID
     */
    void removeMenu(SysMenuRemoveReqVO removeReqVO);

}
