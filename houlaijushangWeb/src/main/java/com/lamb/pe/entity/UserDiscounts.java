/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.entity;

import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户促销优惠卷关联表Entity
 * @author dizj
 * @version 2021-02-09
 */
@Table(name="user_discounts", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="goods_id", attrName="goodsId", label="商品id"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserDiscounts extends DataEntity<UserDiscounts> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String goodsId;		// 商品id
	
	public UserDiscounts() {
		this(null);
	}

	public UserDiscounts(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="商品id长度不能超过 32 个字符")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
}