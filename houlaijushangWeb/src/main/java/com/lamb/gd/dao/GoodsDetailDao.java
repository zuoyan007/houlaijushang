/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gd.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.gd.entity.GoodsDetail;

import java.util.List;

/**
 * 商品明细表DAO接口
 * @author dizj
 * @version 2021-01-27
 */
@MyBatisDao
public interface GoodsDetailDao extends CrudDao<GoodsDetail> {

    public List<GoodsDetail> findYouLike();
	
}