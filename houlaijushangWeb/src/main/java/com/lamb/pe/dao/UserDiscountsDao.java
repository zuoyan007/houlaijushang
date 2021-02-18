/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.pe.entity.UserDiscounts;

/**
 * 用户促销优惠卷关联表DAO接口
 * @author dizj
 * @version 2021-02-09
 */
@MyBatisDao
public interface UserDiscountsDao extends CrudDao<UserDiscounts> {
	
}