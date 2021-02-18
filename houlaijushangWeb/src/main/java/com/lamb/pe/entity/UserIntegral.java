/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.entity;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 用户积分Entity
 * @author dizj
 * @version 2021-02-12
 */
@Table(name="user_integral", alias="a", columns={
		@Column(name="integral_id", attrName="integralId", label="积分id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="user_name", attrName="userName", label="用户姓名", queryType=QueryType.LIKE),
		@Column(name="integral_account", attrName="integralAccount", label="累计积分"),
		@Column(name="integral_now", attrName="integralNow", label="目前积分"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserIntegral extends DataEntity<UserIntegral> {
	
	private static final long serialVersionUID = 1L;
	private String integralId;		// 积分id
	private String userId;		// 用户id
	private String userName;		// 用户姓名
	private Long integralAccount;		// 累计积分
	private Long integralNow;		// 目前积分
	
	public UserIntegral() {
		this(null);
	}

	public UserIntegral(String id){
		super(id);
	}
	
	public String getIntegralId() {
		return integralId;
	}

	public void setIntegralId(String integralId) {
		this.integralId = integralId;
	}
	
	@NotBlank(message="用户id不能为空")
	@Length(min=0, max=32, message="用户id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public UserIntegral setUserId(String userId) {
		this.userId = userId;
		return this;
	}
	
	@Length(min=0, max=32, message="用户姓名长度不能超过 32 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public Long getIntegralAccount() {
		return integralAccount;
	}

	public void setIntegralAccount(Long integralAccount) {
		this.integralAccount = integralAccount;
	}
	
	public Long getIntegralNow() {
		return integralNow;
	}

	public void setIntegralNow(Long integralNow) {
		this.integralNow = integralNow;
	}
	
}