/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.pe.entity.UserCollects;
import com.lamb.pe.dao.UserCollectsDao;

/**
 * 用户收藏Service
 * @author dizj
 * @version 2021-02-01
 */
@Service
@Transactional(readOnly=true)
public class UserCollectsService extends CrudService<UserCollectsDao, UserCollects> {
	
	/**
	 * 获取单条数据
	 * @param userCollects
	 * @return
	 */
	@Override
	public UserCollects get(UserCollects userCollects) {
		return super.get(userCollects);
	}
	
	/**
	 * 查询分页数据
	 * @param userCollects 查询条件
	 * @param userCollects.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserCollects> findPage(UserCollects userCollects) {
		return super.findPage(userCollects);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userCollects
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserCollects userCollects) {
		super.save(userCollects);
	}
	
	/**
	 * 更新状态
	 * @param userCollects
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserCollects userCollects) {
		super.updateStatus(userCollects);
	}
	
	/**
	 * 删除数据
	 * @param userCollects
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserCollects userCollects) {
		super.delete(userCollects);
	}
	
}