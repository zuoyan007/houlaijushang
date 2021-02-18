/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.pe.dao.UserAddressDao;
import com.lamb.pe.entity.UserAddress;
import com.lamb.util.UserKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户地址Service
 * @author dizj
 * @version 2021-02-02
 */
@Service
@Transactional(readOnly=true)
public class UserAddressService extends CrudService<UserAddressDao, UserAddress> {

	@Autowired
	private UserAddressDao userAddressDao;
	/**
	 * 获取单条数据
	 * @param userAddress
	 * @return
	 */
	@Override
	public UserAddress get(UserAddress userAddress) {
		return super.get(userAddress);
	}
	
	/**
	 * 查询分页数据
	 * @param userAddress 查询条件
	 * @param userAddress.page 分页对象
	 * @return
	 */
	@Override
	public Page<UserAddress> findPage(UserAddress userAddress) {
		return super.findPage(userAddress);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userAddress
	 */
	@Transactional(readOnly=false)
	public void save(UserAddress userAddress,int isInsert) {
		if("0".equals(userAddress.getIsDefault())){
			userAddressDao.updateDefault(UserKit.getUserId());
		}
		if (isInsert==0){
			super.insert(userAddress);
		}else if (isInsert==1){
			super.update(userAddress);
		}
	}
	
	/**
	 * 更新状态
	 * @param userAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserAddress userAddress) {
		super.updateStatus(userAddress);
	}
	
	/**
	 * 删除数据
	 * @param userAddress
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserAddress userAddress) {
		super.delete(userAddress);
	}
	
}