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
 * 用户预存Entity
 * @author dizj
 * @version 2021-02-11
 */
@Table(name="user_prestore", alias="a", columns={
		@Column(name="prestore_id", attrName="prestoreId", label="预存id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="user_name", attrName="userName", label="用户姓名", queryType=QueryType.LIKE),
		@Column(name="user_mobile", attrName="userMobile", label="用户手机号"),
		@Column(name="prestore_sum", attrName="prestoreSum", label="预存金额"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserPrestore extends DataEntity<UserPrestore> {
	
	private static final long serialVersionUID = 1L;
	private String prestoreId;		// 预存id
	private String userId;		// 用户id
	private String userName;		// 用户姓名
	private String userMobile;		// 用户手机号
	private Double prestoreSum;		// 预存金额
	
	public UserPrestore() {
		this(null);
	}

	public UserPrestore(String id){
		super(id);
	}
	
	public String getPrestoreId() {
		return prestoreId;
	}

	public void setPrestoreId(String prestoreId) {
		this.prestoreId = prestoreId;
	}
	
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="用户姓名长度不能超过 32 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=32, message="用户手机号长度不能超过 32 个字符")
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	public Double getPrestoreSum() {
		return prestoreSum;
	}

	public void setPrestoreSum(Double prestoreSum) {
		this.prestoreSum = prestoreSum;
	}
	
}