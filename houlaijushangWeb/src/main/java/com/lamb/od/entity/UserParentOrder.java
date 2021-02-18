/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.od.entity;

import com.lamb.gd.entity.GoodsDetail;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;
import java.util.List;
import com.jeesite.common.collect.ListUtils;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户主订单Entity
 * @author dizj
 * @version 2021-02-04
 */
@Table(name="user_parent_order", alias="a", columns={
		@Column(name="parent_order_id", attrName="parentOrderId", label="主订单id", isPK=true),
		@Column(name="deal_number", attrName="dealNumber", label="交易流水号"),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="username", attrName="username", label="联系人"),
		@Column(name="usermobile", attrName="usermobile", label="联系电话"),
		@Column(name="order_address", attrName="orderAddress", label="联系人地址"),
		@Column(name="order_doorplate", attrName="orderDoorplate", label="门牌号"),
		@Column(name="goods_price", attrName="goodsPrice", label="商品总价格"),
		@Column(name="coupon_price", attrName="couponPrice", label="商品总优惠价格"),
		@Column(name="total_paid", attrName="totalPaid", label="实际总付款"),
		@Column(name="goods_state", attrName="goodsState", label="商品状态", comment="商品状态(待付款，待收货，待评价，售后)"),
		@Column(name="payment", attrName="payment", label="实际付款"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserParentOrder extends DataEntity<UserParentOrder> {
	
	private static final long serialVersionUID = 1L;
	private String parentOrderId;		// 主订单id
	private String dealNumber;		// 交易流水号
	private String userId;		// 用户id
	private String username;		// 联系人
	private String usermobile;		// 联系电话
	private String orderAddress;		// 联系人地址
	private String orderDoorplate;		// 门牌号
	private String goodsPrice;		// 商品总价格
	private String couponPrice;		// 商品总优惠价格
	private String totalPaid;		// 实际总付款
	private String goodsState;		// 商品状态(待付款，待收货，待评价，售后 , 退款完成)
	private String payment;		// 实际付款
	private List<UserChildOrder> userChildOrderList = ListUtils.newArrayList();		// 子表列表
	private String encryptPrice;    //加总付款金额
	
	public UserParentOrder() {
		this(null);
	}

	public UserParentOrder(String id){
		super(id);
	}
	
	public String getParentOrderId() {
		return parentOrderId;
	}

	public UserParentOrder setParentOrderId(String parentOrderId) {
		this.parentOrderId = parentOrderId;
		return this;
	}
	
	@Length(min=0, max=64, message="交易流水号长度不能超过 64 个字符")
	public String getDealNumber() {
		return dealNumber;
	}

	public void setDealNumber(String dealNumber) {
		this.dealNumber = dealNumber;
	}
	
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="联系人长度不能超过 32 个字符")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=0, max=32, message="联系电话长度不能超过 32 个字符")
	public String getUsermobile() {
		return usermobile;
	}

	public void setUsermobile(String usermobile) {
		this.usermobile = usermobile;
	}
	
	@Length(min=0, max=300, message="联系人地址长度不能超过 300 个字符")
	public String getOrderAddress() {
		return orderAddress;
	}

	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	
	@Length(min=0, max=300, message="门牌号长度不能超过 300 个字符")
	public String getOrderDoorplate() {
		return orderDoorplate;
	}

	public void setOrderDoorplate(String orderDoorplate) {
		this.orderDoorplate = orderDoorplate;
	}
	
	@Length(min=0, max=32, message="商品总价格长度不能超过 32 个字符")
	public String getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	
	@Length(min=0, max=32, message="商品总优惠价格长度不能超过 32 个字符")
	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	
	@Length(min=0, max=32, message="实际总付款长度不能超过 32 个字符")
	public String getTotalPaid() {
		return totalPaid;
	}

	public void setTotalPaid(String totalPaid) {
		this.totalPaid = totalPaid;
	}
	
	@Length(min=0, max=32, message="商品状态长度不能超过 32 个字符")
	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}
	
	@Length(min=0, max=32, message="实际付款长度不能超过 32 个字符")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	public List<UserChildOrder> getUserChildOrderList() {
		return userChildOrderList;
	}

	public void setUserChildOrderList(List<UserChildOrder> userChildOrderList) {
		this.userChildOrderList = userChildOrderList;
	}

	public String getEncryptPrice() {
		return encryptPrice;
	}

	public void setEncryptPrice(String encryptPrice) {
		this.encryptPrice = encryptPrice;
	}
}