/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.cp.entity.CouponUser;

/**
 * 优惠卷用户关联表DAO接口
 * @author dizj
 * @version 2021-02-06
 */
@MyBatisDao
public interface CouponUserDao extends CrudDao<CouponUser> {
	
}