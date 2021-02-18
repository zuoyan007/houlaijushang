/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.od.service;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.cp.dao.CouponUserDao;
import com.lamb.cp.entity.CouponUser;
import com.lamb.gd.dao.GoodsDetailDao;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.od.dao.UserChildOrderDao;
import com.lamb.od.dao.UserParentOrderDao;
import com.lamb.od.entity.CreateOrder;
import com.lamb.od.entity.UserChildOrder;
import com.lamb.od.entity.UserParentOrder;
import com.lamb.pe.dao.UserIntegralDao;
import com.lamb.pe.dao.UserPrestoreDao;
import com.lamb.pe.dao.UserShoppingCartDao;
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.entity.UserPrestore;
import com.lamb.pe.entity.UserShoppingCart;
import com.lamb.util.UserKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户主订单Service
 * @author dizj
 * @version 2021-02-04
 */
@Service
@Transactional(readOnly=true)
public class UserParentOrderService extends CrudService<UserParentOrderDao, UserParentOrder> {
	
	@Autowired
	private UserChildOrderDao userChildOrderDao;
	@Autowired
	private CreateOrderService createOrderService;
	@Autowired
	private GoodsDetailDao goodsDetailDao;
	@Autowired
	private CouponUserDao couponUserDao;
	@Autowired
	private UserShoppingCartDao userShoppingCartDao;
	@Autowired
	private UserPrestoreDao UserPrestoreDao;
	@Autowired
	private UserIntegralDao UserIntegralDao;






	
	/**
	 * 获取单条数据
	 * @param userParentOrder
	 * @return
	 */
	@Override
	public UserParentOrder get(UserParentOrder userParentOrder) {
		UserParentOrder entity = super.get(userParentOrder);
		if (entity != null){
			UserChildOrder userChildOrder = new UserChildOrder(entity);
			userChildOrder.setStatus(UserChildOrder.STATUS_NORMAL);
			entity.setUserChildOrderList(userChildOrderDao.findList(userChildOrder));
		}
		return entity;
	}
	
	/**
	 * 查询分页数据
	 * @param userParentOrder 查询条件
	 * @param
	 * @return
	 */
	@Override
	public Page<UserParentOrder> findPage(UserParentOrder userParentOrder) {
		return super.findPage(userParentOrder);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param userParentOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(UserParentOrder userParentOrder) {
		super.save(userParentOrder);
		// 保存 UserParentOrder子表
		for (UserChildOrder userChildOrder : userParentOrder.getUserChildOrderList()){
			if (!UserChildOrder.STATUS_DELETE.equals(userChildOrder.getStatus())){
				userChildOrder.setParentOrderId(userParentOrder);
				if (userChildOrder.getIsNewRecord()){
					userChildOrderDao.insert(userChildOrder);
				}else{
					userChildOrderDao.update(userChildOrder);
				}
			}else{
				userChildOrderDao.delete(userChildOrder);
			}
		}
	}
	
	/**
	 * 更新状态
	 * @param userParentOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(UserParentOrder userParentOrder) {
		super.updateStatus(userParentOrder);
	}
	
	/**
	 * 删除数据
	 * @param userParentOrder
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(UserParentOrder userParentOrder) {
		super.delete(userParentOrder);
		UserChildOrder userChildOrder = new UserChildOrder();
		userChildOrder.setParentOrderId(userParentOrder);
		userChildOrderDao.deleteByEntity(userChildOrder);
	}


	/**
	 * 创建订单
	 * @param orderList
	 * @param couponId
	 */
	@Transactional(readOnly=false)
	public String CreateToSave(String orderList, String couponId , UserParentOrder userParentOrder) {
		List<CreateOrder> createOrders = JSON.parseArray(orderList, CreateOrder.class);
		//支付总价
		double goodsPrice = 0;
		//优惠总价
		double goodsSpecial = 0;
		//暂存子订单的list
		ArrayList<UserChildOrder> list = new ArrayList<>();
		//遍历传过来的实体获取里面的商品id和数量已经该商品的商品促销
		for (CreateOrder createOrder : createOrders) {
			GoodsDetail goodsDetail = goodsDetailDao.get(new GoodsDetail().setGoodsId(createOrder.getGoodsId()));
			String substring = goodsDetail.getGoodPrice().substring(1);
			java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
			//将现有价格转换为Double类型保留2为小数
			double goodPrice = Double.parseDouble(myformat.format(Double.parseDouble(substring)));
			double Price =goodPrice*(createOrder.getGoodsNumber());
			double special = createOrderService.checkUserDiscounts(UserKit.getUserId(), createOrder.getGoodsId(), Price, goodsDetail.getDiscounts());
			//将商品属性暂存到子订单的list中
			UserChildOrder userChildOrder = new UserChildOrder();
			userChildOrder.setChildOrderId(createOrder.getChildOrderId());
			userChildOrder.setGoodNumber(createOrder.getGoodsNumber()+"");
			userChildOrder.setGoodsId(createOrder.getGoodsId());
			userChildOrder.setGoodPrice(goodPrice+"");
			userChildOrder.setCouponPrice(special+"");
			userChildOrder.setPayment((Price-special)+"");
			list.add(userChildOrder);
			//支付总价
			goodsPrice+=(Price-special);
			//优惠总价
			goodsSpecial+=special;
		}
		CouponUser byEntity = new CouponUser();
		//如果选择使用优惠卷查询优惠卷是否存在
		if (!"".equals(couponId)){
			CouponUser couponUser = new CouponUser();
			couponUser.setUserId(userParentOrder.getUserId());
			couponUser.setCouponId(couponId);
			byEntity = couponUserDao.getByEntity(couponUser);
		}
		if (byEntity!=null&&!"".equals(couponId)){
			//如果优惠卷存在
			if (byEntity.getCouponNumber()>1){
				//判断优惠卷张数如果大于一张则减1
				byEntity.setCouponNumber(byEntity.getCouponNumber()-1);
				couponUserDao.update(byEntity);
			}else {
				//只有一张则删除优惠卷
				couponUserDao.delete(byEntity);
			}
			//支付总价等于总价减去优惠卷价格
			goodsPrice =goodsPrice -byEntity.getCouponType().getCouponCut();
			//优惠总价等于优惠总价加优惠卷价格
			goodsSpecial += byEntity.getCouponType().getCouponCut();
			userParentOrder.setTotalPaid(goodsPrice+"");
			userParentOrder.setPayment(goodsPrice+"");
			userParentOrder.setCouponPrice(goodsSpecial+"");
			userParentOrder.setGoodsState("10");
			//插入主订单表中
			super.insert(userParentOrder);
			//至取到小数点第二位
			java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
			//更新子表的优惠
			for (UserChildOrder userChildOrder : list) {
				//将现有价格转换为Double类型保留2为小数
				double GoodPrice = Double.parseDouble(myformat.format(Double.parseDouble(userChildOrder.getGoodPrice())));
				double CouponPrice = Double.parseDouble(myformat.format(Double.parseDouble(userChildOrder.getCouponPrice())));
				double GoodNumber = Double.parseDouble(myformat.format(Double.parseDouble(userChildOrder.getGoodNumber())));
				double cost = GoodPrice * GoodNumber;
				//子订单中商品优惠总价按比例算出
				CouponPrice +=(Double.parseDouble(myformat.format(byEntity.getCouponType().getCouponCut()/cost)));
				userChildOrder.setCouponPrice(CouponPrice+"");
				//子订单中商品支付总价
				userChildOrder.setPayment((Double.parseDouble(myformat.format(cost-CouponPrice)))+"");
				//关联主订单表
				userChildOrder.setParentOrderId(userParentOrder);
				//状态未付款
				userChildOrder.setGoodsState("10");
				//子订单表插入
				userChildOrderDao.insert(userChildOrder);
				UserShoppingCart userShoppingCart = new UserShoppingCart();
				userShoppingCart.setUserId(UserKit.getUserId());
				userShoppingCart.setGoodsId(userChildOrder.getGoodsId());
				//购物车删除该商品
				userShoppingCartDao.deleteByEntity(userShoppingCart);
			}
		}else {
			//主订单表插入
			userParentOrder.setTotalPaid(goodsPrice+"");
			userParentOrder.setPayment(goodsPrice+"");
			userParentOrder.setCouponPrice(goodsSpecial+"");
			userParentOrder.setGoodsState("10");
			super.insert(userParentOrder);
			//遍历插入子商品删除购物车
			for (UserChildOrder userChildOrder : list) {
				userChildOrder.setParentOrderId(userParentOrder);
				userChildOrder.setGoodsState("10");
				userChildOrderDao.insert(userChildOrder);
				UserShoppingCart userShoppingCart = new UserShoppingCart();
				userShoppingCart.setUserId(UserKit.getUserId());
				userShoppingCart.setGoodsId(userChildOrder.getGoodsId());
				userShoppingCartDao.deleteByEntity(userShoppingCart);
			}
		}
		String encryptPrice = DesUtils.encode(goodsPrice+"", Global.getConfig("shiro.loginSubmit.secretKey"));
		return encryptPrice;
	}



	/**
	 * 确认收货
	 * @param
	 */
	@Transactional(readOnly=false)
	public void take(String parentOrderId) {
		UserParentOrder userParentOrder = super.get(parentOrderId);
		for (UserChildOrder userChildOrder : userParentOrder.getUserChildOrderList()) {
			userChildOrder.setGoodsState("30");
			userChildOrderDao.update(userChildOrder);
		}
		userParentOrder.setGoodsState("30");
		super.update(userParentOrder);
	}

	/**
	 * 取消订单
	 * @param
	 */
	@Transactional(readOnly=false)
	public void cancel(String parentOrderId) {
		UserParentOrder userParentOrder = new UserParentOrder().setParentOrderId(parentOrderId);
		UserChildOrder userChildOrder = new UserChildOrder();
		super.delete( new UserParentOrder().setParentOrderId(parentOrderId));
		userChildOrderDao.delete(userChildOrder.setParentOrderId(userParentOrder));
	}

	/**
	 * 退款
	 * @param
	 */
	@Transactional(readOnly=false)
	public void refund(String parentOrderId) {
		double IntegralSum = 0;
		UserParentOrder userParentOrder = super.get(parentOrderId);
		double OrderPay = Double.parseDouble(userParentOrder.getTotalPaid());
		UserPrestore userPrestore = new UserPrestore();
		userPrestore.setUserId(UserKit.getUserId());
		UserPrestore byEntity = UserPrestoreDao.getByEntity(userPrestore);
		byEntity.setPrestoreSum( byEntity.getPrestoreSum()+OrderPay);
		UserPrestoreDao.update(byEntity);
		for (UserChildOrder userChildOrder : userParentOrder.getUserChildOrderList()) {
			userChildOrder.setGoodsState("50");
			userChildOrderDao.update(userChildOrder);
			GoodsDetail goodsDetail = goodsDetailDao.get(new GoodsDetail(userChildOrder.getGoodsId()));
			double Integral = Double.parseDouble(goodsDetail.getIntegral());
			double Number = Double.parseDouble(userChildOrder.getGoodNumber());
			IntegralSum += Integral*Number;
		}
		UserIntegral userIntegral = new UserIntegral();
		UserIntegral byEntity1 = UserIntegralDao.getByEntity(userIntegral.setUserId(UserKit.getUserId()));
		long l = Long.parseLong(IntegralSum + "");
		byEntity1.setIntegralAccount(byEntity1.getIntegralAccount()-l);
		byEntity1.setIntegralNow(byEntity1.getIntegralNow()-l);
		UserIntegralDao.update(byEntity1);
		userParentOrder.setGoodsState("50");
		super.update(userParentOrder);
	}

}