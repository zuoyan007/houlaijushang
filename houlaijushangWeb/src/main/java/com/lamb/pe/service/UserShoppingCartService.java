/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.pe.entity.UserShoppingCart;
import com.lamb.pe.dao.UserShoppingCartDao;

/**
 * 购物车Service
 * @author dizj
 * @version 2021-02-01
 */
@Service
@Transactional(readOnly=true)
public class UserShoppingCartService extends CrudService<UserShoppingCartDao, UserShoppingCart> {
	
	/**
	 * 获取单条数据
	 * @param userShoppingCart
	 * @return
	 */
	@Override
	public UserShoppingCart get(UserShoppingCart userShoppingCart) {
		return super.get(userShoppingCart);
	}
	
	/**
	 * 查询分页数据
	 * @param userShoppingCart 查询条件
	 * @param userShoppingCart.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserShoppingCart> findPage(UserShoppingCart userShoppingCart) {
		return super.findPage(userShoppingCart);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userShoppingCart
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserShoppingCart userShoppingCart) {
		super.save(userShoppingCart);
	}
	
	/**
	 * 更新状态
	 * @param userShoppingCart
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserShoppingCart userShoppingCart) {
		super.updateStatus(userShoppingCart);
	}
	
	/**
	 * 删除数据
	 * @param userShoppingCart
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserShoppingCart userShoppingCart) {
		super.delete(userShoppingCart);
	}
	
}