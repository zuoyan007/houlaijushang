/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.od.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.od.entity.UserParentOrder;

/**
 * 用户主订单DAO接口
 * @author dizj
 * @version 2021-02-04
 */
@MyBatisDao
public interface UserParentOrderDao extends CrudDao<UserParentOrder> {
	
}