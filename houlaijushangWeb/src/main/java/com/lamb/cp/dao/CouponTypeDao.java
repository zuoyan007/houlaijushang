/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.cp.entity.CouponType;

/**
 * 优惠卷DAO接口
 * @author dizj
 * @version 2021-02-04
 */
@MyBatisDao
public interface CouponTypeDao extends CrudDao<CouponType> {
	
}