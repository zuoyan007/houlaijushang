/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.cp.entity.CouponUser;
import com.lamb.cp.dao.CouponUserDao;

/**
 * 优惠卷用户关联表Service
 * @author dizj
 * @version 2021-02-06
 */
@Service
@Transactional(readOnly=true)
public class CouponUserService extends CrudService<CouponUserDao, CouponUser> {
	
	/**
	 * 获取单条数据
	 * @param couponUser
	 * @return
	 */
	@Override
	public CouponUser get(CouponUser couponUser) {
		return super.get(couponUser);
	}
	
	/**
	 * 查询分页数据
	 * @param couponUser 查询条件
	 * @param couponUser.page 分页对象
	 * @return
	 */
	@Override
	public Page<CouponUser> findPage(CouponUser couponUser) {
		return super.findPage(couponUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param couponUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CouponUser couponUser) {
		super.save(couponUser);
	}
	
	/**
	 * 更新状态
	 * @param couponUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CouponUser couponUser) {
		super.updateStatus(couponUser);
	}
	
	/**
	 * 删除数据
	 * @param couponUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CouponUser couponUser) {
		super.delete(couponUser);
	}
	
}