/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 优惠卷Entity
 * @author dizj
 * @version 2021-02-04
 */
@Table(name="coupon_type", alias="a", columns={
		@Column(name="coupon_id", attrName="couponId", label="优惠卷id", isPK=true),
		@Column(name="coupon_name", attrName="couponName", label="优惠名称", queryType=QueryType.LIKE),
		@Column(name="coupon_terms", attrName="couponTerms", label="优惠满额"),
		@Column(name="coupon_cut", attrName="couponCut", label="优惠减额"),
		@Column(name="coupon_start_time", attrName="couponStartTime", label="优惠开始时间"),
		@Column(name="coupon_end_time", attrName="couponEndTime", label="优惠结束时间"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class CouponType extends DataEntity<CouponType> {
	
	private static final long serialVersionUID = 1L;
	private String couponId;		// 优惠卷id
	private String couponName;		// 优惠名称
	private Double couponTerms;		// 优惠满额
	private Double couponCut;		// 优惠减额
	private Date couponStartTime;		// 优惠开始时间
	private Date couponEndTime;		// 优惠结束时间
	
	public CouponType() {
		this(null);
	}

	public CouponType(String id){
		super(id);
	}
	
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	
	@Length(min=0, max=128, message="优惠名称长度不能超过 128 个字符")
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	
	@NotNull(message="优惠满额不能为空")
	public Double getCouponTerms() {
		return couponTerms;
	}

	public void setCouponTerms(Double couponTerms) {
		this.couponTerms = couponTerms;
	}
	
	@NotNull(message="优惠减额不能为空")
	public Double getCouponCut() {
		return couponCut;
	}

	public void setCouponCut(Double couponCut) {
		this.couponCut = couponCut;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="优惠开始时间不能为空")
	public Date getCouponStartTime() {
		return couponStartTime;
	}

	public void setCouponStartTime(Date couponStartTime) {
		this.couponStartTime = couponStartTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@NotNull(message="优惠结束时间不能为空")
	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	
}