/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.entity;

import com.jeesite.common.mybatis.annotation.JoinTable;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 优惠卷用户关联表Entity
 * @author dizj
 * @version 2021-02-06
 */
@Table(name="coupon_user", alias="a", columns={
		@Column(name="coupon_user_id", attrName="couponUserId", label="优惠卷用户关联表id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="coupon_id", attrName="couponId", label="优惠卷id"),
		@Column(name="coupon_number", attrName="couponNumber", label="优惠卷数量"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= CouponType.class, alias="couponType",attrName = "couponType",
				on="CouponType.coupon_id = a.coupon_id",
				columns={@Column(includeEntity= CouponType.class)}),
},orderBy="a.update_date DESC"
)
public class CouponUser extends DataEntity<CouponUser> {
	
	private static final long serialVersionUID = 1L;
	private String couponUserId;		// 优惠卷用户关联表id
	private String userId;		// 用户id
	private String couponId;		// 优惠卷id
	private Long couponNumber;		// 优惠卷数量
	private CouponType couponType;		// 优惠卷详情
	private String couponName;		// 优惠卷名称

	
	public CouponUser() {
		this(null);
	}

	public CouponUser(String id){
		super(id);
	}
	
	public String getCouponUserId() {
		return couponUserId;
	}

	public void setCouponUserId(String couponUserId) {
		this.couponUserId = couponUserId;
	}
	
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public CouponUser setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	
	@Length(min=0, max=32, message="优惠卷id长度不能超过 32 个字符")
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
	public Long getCouponNumber() {
		return couponNumber;
	}

	public void setCouponNumber(Long couponNumber) {
		this.couponNumber = couponNumber;
	}

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
}