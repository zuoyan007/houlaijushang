/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.od.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.file.entity.FileUpload;
import com.lamb.gd.entity.GoodsDetail;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.List;

/**
 * 用户主订单Entity
 * @author dizj
 * @version 2021-02-04
 */
@Table(name="user_child_order", alias="a", columns={
		@Column(name="child_order_id", attrName="childOrderId", label="子订单id", isPK=true),
		@Column(name="parent_order_id", attrName="parentOrderId.parentOrderId", label="主订单id"),
		@Column(name="goods_id", attrName="goodsId", label="商品id"),
		@Column(name="good_price", attrName="goodPrice", label="商品价格"),
		@Column(name="good_number", attrName="goodNumber", label="商品数量"),
		@Column(name="coupon_price", attrName="couponPrice", label="商品优惠价格"),
		@Column(name="payment", attrName="payment", label="实际付款"),
		@Column(name="goods_state", attrName="goodsState", label="商品状态", comment="商品状态(待付款，待收货，收货，售后，退款完成)"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.create_date ASC"
)
public class UserChildOrder extends DataEntity<UserChildOrder> {
	
	private static final long serialVersionUID = 1L;
	private String childOrderId;		// 子订单id
	private UserParentOrder parentOrderId;		// 主订单id 父类
	private String goodsId;		// 商品id
	private String goodPrice;		// 商品价格
	private String goodNumber;		// 商品数量
	private String couponPrice;		// 商品优惠价格
	private String payment;		// 实际付款
	private String goodsState;		// 商品状态(待付款，待收货，收货，售后，退款完成)
	private List<FileUpload> imageList = ListUtils.newArrayList();
	private GoodsDetail goodsDetail;
	private Integer showNumber;		// 商品数量
	public UserChildOrder() {
		this(null);
	}


	public UserChildOrder(UserParentOrder parentOrderId){
		this.parentOrderId = parentOrderId;
	}
	
	public String getChildOrderId() {
		return childOrderId;
	}

	public void setChildOrderId(String childOrderId) {
		this.childOrderId = childOrderId;
	}
	
	@Length(min=0, max=32, message="主订单id长度不能超过 32 个字符")
	public UserParentOrder getParentOrderId() {
		return parentOrderId;
	}

	public UserChildOrder setParentOrderId(UserParentOrder parentOrderId) {
		this.parentOrderId = parentOrderId;
		return this;
	}
	
	@Length(min=0, max=32, message="商品id长度不能超过 32 个字符")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=32, message="商品价格长度不能超过 32 个字符")
	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	
	@Length(min=0, max=32, message="商品数量长度不能超过 32 个字符")
	public String getGoodNumber() {
		return goodNumber;
	}

	public void setGoodNumber(String goodNumber) {
		this.goodNumber = goodNumber;
	}
	
	@Length(min=0, max=32, message="商品优惠价格长度不能超过 32 个字符")
	public String getCouponPrice() {
		return couponPrice;
	}

	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	
	@Length(min=0, max=32, message="实际付款长度不能超过 32 个字符")
	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}
	
	@Length(min=0, max=32, message="商品状态长度不能超过 32 个字符")
	public String getGoodsState() {
		return goodsState;
	}

	public void setGoodsState(String goodsState) {
		this.goodsState = goodsState;
	}

	public List<FileUpload> getImageList() {
		return imageList;
	}

	public void setImageList(List<FileUpload> imageList) {
		this.imageList = imageList;
	}

	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public Integer getShowNumber() {
		return showNumber;
	}

	public void setShowNumber(Integer showNumber) {
		this.showNumber = showNumber;
	}
}