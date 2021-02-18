/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.dao.UserIntegralDao;

/**
 * 用户积分Service
 * @author dizj
 * @version 2021-02-12
 */
@Service
@Transactional(readOnly=true)
public class UserIntegralService extends CrudService<UserIntegralDao, UserIntegral> {
	
	/**
	 * 获取单条数据
	 * @param userIntegral
	 * @return
	 */
	@Override
	public UserIntegral get(UserIntegral userIntegral) {
		return super.get(userIntegral);
	}
	
	/**
	 * 查询分页数据
	 * @param userIntegral 查询条件
	 * @param userIntegral.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserIntegral> findPage(UserIntegral userIntegral) {
		return super.findPage(userIntegral);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userIntegral
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserIntegral userIntegral) {
		super.save(userIntegral);
	}
	
	/**
	 * 更新状态
	 * @param userIntegral
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserIntegral userIntegral) {
		super.updateStatus(userIntegral);
	}
	
	/**
	 * 删除数据
	 * @param userIntegral
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserIntegral userIntegral) {
		super.delete(userIntegral);
	}
	
}