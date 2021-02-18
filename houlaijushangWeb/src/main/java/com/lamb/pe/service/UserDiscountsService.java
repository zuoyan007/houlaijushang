/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.pe.entity.UserDiscounts;
import com.lamb.pe.dao.UserDiscountsDao;

/**
 * 用户促销优惠卷关联表Service
 * @author dizj
 * @version 2021-02-09
 */
@Service
@Transactional(readOnly=true)
public class UserDiscountsService extends CrudService<UserDiscountsDao, UserDiscounts> {
	@Autowired
	private UserDiscountsDao userDiscountsDao;
	
	/**
	 * 获取单条数据
	 * @param userDiscounts
	 * @return
	 */
	@Override
	public UserDiscounts get(UserDiscounts userDiscounts) {
		return super.get(userDiscounts);
	}
	
	/**
	 * 查询分页数据
	 * @param userDiscounts 查询条件
	 * @return
	 */
	@Override
	public Page<UserDiscounts> findPage(UserDiscounts userDiscounts) {
		return super.findPage(userDiscounts);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userDiscounts
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserDiscounts userDiscounts) {
		if (userDiscountsDao.getByEntity(userDiscounts)==null){
			super.insert(userDiscounts);
		}
	}

	/**
	 * 更新状态
	 * @param userDiscounts
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserDiscounts userDiscounts) {
		super.updateStatus(userDiscounts);
	}
	
	/**
	 * 删除数据
	 * @param userDiscounts
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserDiscounts userDiscounts) {
		super.delete(userDiscounts);
	}
	
}