/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.cp.entity.CouponType;
import com.lamb.cp.dao.CouponTypeDao;

/**
 * 优惠卷Service
 * @author dizj
 * @version 2021-02-04
 */
@Service
@Transactional(readOnly=true)
public class CouponTypeService extends CrudService<CouponTypeDao, CouponType> {
	
	/**
	 * 获取单条数据
	 * @param couponType
	 * @return
	 */
	@Override
	public CouponType get(CouponType couponType) {
		return super.get(couponType);
	}
	
	/**
	 * 查询分页数据
	 * @param couponType 查询条件
	 * @param couponType.page 分页对象
	 * @return
	 */
	@Override
	public Page<CouponType> findPage(CouponType couponType) {
		return super.findPage(couponType);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param couponType
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(CouponType couponType) {
		super.save(couponType);
	}
	
	/**
	 * 更新状态
	 * @param couponType
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(CouponType couponType) {
		super.updateStatus(couponType);
	}
	
	/**
	 * 删除数据
	 * @param couponType
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(CouponType couponType) {
		super.delete(couponType);
	}
	
}