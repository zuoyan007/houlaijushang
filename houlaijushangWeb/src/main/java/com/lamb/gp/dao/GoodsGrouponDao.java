/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.gp.entity.GoodsGroupon;

/**
 * 团购管理DAO接口
 * @author dizj
 * @version 2021-02-07
 */
@MyBatisDao
public interface GoodsGrouponDao extends CrudDao<GoodsGroupon> {
	
}