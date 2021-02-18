package com.lamb.util.sys;

import com.jeesite.common.entity.Page;

/**
 * 分页工具类
 */
public class PageKit {

    public static Page copy(Page<?> s, Page<?> t){
        t.setPageSize(s.getPageSize());
        t.setCount(s.getCount());
        t.setPageNo(s.getPageNo());
        return t;
    }
}
