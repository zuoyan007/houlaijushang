/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.gd.service.GoodsDetailService;
import com.lamb.gp.dao.GoodsGrouponDao;
import com.lamb.gp.entity.GoodsGroupon;
import com.lamb.od.entity.UserChildOrder;
import com.lamb.od.entity.UserParentOrder;
import com.lamb.od.service.UserParentOrderService;
import com.lamb.pe.dao.UserIntegralDao;
import com.lamb.pe.dao.UserPrestoreDao;
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.entity.UserPrestore;
import com.lamb.util.UserKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户预存Service
 * @author dizj
 * @version 2021-02-11
 */
@Service
@Transactional(readOnly=true)
public class UserPrestoreService extends CrudService<UserPrestoreDao, UserPrestore> {
	@Autowired
    private UserPrestoreDao userPrestoreDao;
	@Autowired
	private UserParentOrderService userParentOrderService;
	@Autowired
	private GoodsGrouponDao goodsGrouponDao;
	@Autowired
	private GoodsDetailService goodsDetailService;
	@Autowired
	private UserIntegralDao userIntegralDao;




	/**
	 * 获取单条数据
	 * @param userPrestore
	 * @return
	 */
	@Override
	public UserPrestore get(UserPrestore userPrestore) {
		return super.get(userPrestore);
	}
	
	/**
	 * 查询分页数据
	 * @param userPrestore 查询条件
	 * @return
	 */
	@Override
	public Page<UserPrestore> findPage(UserPrestore userPrestore) {
		return super.findPage(userPrestore);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userPrestore
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserPrestore userPrestore) {
		super.save(userPrestore);
	}
	
	/**
	 * 更新状态
	 * @param userPrestore
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserPrestore userPrestore) {
		super.updateStatus(userPrestore);
	}
	
	/**
	 * 删除数据
	 * @param userPrestore
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserPrestore userPrestore) {
		super.delete(userPrestore);
	}


	/**
	 * 预存支付
	 * @param label
	 * @param encryptPrice
	 * @param parentOrderId
	 */
	@Transactional(readOnly=false)
	public boolean prestore(String label,double encryptPrice, String parentOrderId ,String grouponId) {
		int integralSum = 0;
		UserPrestore userPrestore = new UserPrestore();
		userPrestore.setUserId(UserKit.getUserId());
		//更新预存金额
		userPrestore = userPrestoreDao.getByEntity(userPrestore);
		if (userPrestore.getPrestoreSum()>encryptPrice){
			userPrestore.setPrestoreSum(userPrestore.getPrestoreSum()-encryptPrice);
		}else {
			return false;
		}
		userPrestoreDao.update(userPrestore);
		//更新订单状态
		UserParentOrder userParentOrder = userParentOrderService.get(parentOrderId);

		userParentOrder.setGoodsState("20");
		for (UserChildOrder userChildOrder : userParentOrder.getUserChildOrderList()) {
			userChildOrder.setGoodsState("20");
			GoodsDetail goodsDetail = goodsDetailService.get(userChildOrder.getGoodsId());
			//将现有价格转换为Double类型保留2为小数
			int i = Integer.parseInt(goodsDetail.getIntegral());
			int j = (int) Double.parseDouble(userChildOrder.getGoodNumber());
			integralSum = integralSum + ( i*j);
		}
		userParentOrderService.update(userParentOrder);
		//更新用户积分
		UserIntegral byEntity = userIntegralDao.getByEntity(new UserIntegral().setUserId(UserKit.getUserId()));
		if (byEntity!=null){
			byEntity.setIntegralAccount(byEntity.getIntegralAccount()+integralSum);
			byEntity.setIntegralNow(byEntity.getIntegralNow()+integralSum);
			userIntegralDao.update(byEntity);
		}else {
			byEntity = new UserIntegral();
			byEntity.setUserId(UserKit.getUserId());
			byEntity.setUserName(UserKit.getUser().getUserName());
			byEntity.setIntegralAccount((long) integralSum);
			byEntity.setIntegralNow((long) integralSum);
			userIntegralDao.insert(byEntity);
		}

		//更新团购人数
		if ("3".equals(label)){
			GoodsGroupon goodsGroupon = new GoodsGroupon();
			goodsGroupon.setGrouponId(grouponId);
			goodsGroupon = goodsGrouponDao.get(goodsGroupon);
			goodsGroupon.setGrouponNowNumber(goodsGroupon.getGrouponNowNumber()+1);
			goodsGrouponDao.update(goodsGroupon);
		}
		return true;
	}
}