/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.lamb.pe.entity.UserPrestore;

/**
 * 用户预存DAO接口
 * @author dizj
 * @version 2021-02-11
 */
@MyBatisDao
public interface UserPrestoreDao extends CrudDao<UserPrestore> {
	
}