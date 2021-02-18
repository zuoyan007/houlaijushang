/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.pe.entity.UserIntegral;

/**
 * 用户积分DAO接口
 * @author dizj
 * @version 2021-02-12
 */
@MyBatisDao
public interface UserIntegralDao extends CrudDao<UserIntegral> {
	
}