package com.example.system.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.system.core.page.PageParam;
import com.example.system.core.page.PageResult;

import java.util.List;

/**
 * 自定义分页工具
 */
public class PageUtil {

    /**
     * 获取分页对象
     */
    public static Page<?> getPage(PageParam pageParam) {
        int pageNum = pageParam.getPageNum() != null ? pageParam.getPageNum() : 1;
        int pageSize = pageParam.getPageSize() != null ? pageParam.getPageSize() : 10;
        return new Page<>(pageNum, pageSize);
    }

    /**
     * 转换为 PageResult 对象
     */
    public static <E> PageResult<E> getPageResult(Page<?> page, List<E> sourceList) {
        PageResult<E> pageResult = new PageResult<>();
        pageResult.setPageNum(page.getCurrent());
        pageResult.setPageSize(page.getSize());
        pageResult.setTotal(page.getTotal());
        pageResult.setPages(page.getPages());
        pageResult.setList(sourceList);
        return pageResult;
    }


}
