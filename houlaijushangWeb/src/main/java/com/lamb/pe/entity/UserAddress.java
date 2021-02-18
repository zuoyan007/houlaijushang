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
 * 用户地址Entity
 * @author dizj
 * @version 2021-02-02
 */
@Table(name="user_address", alias="a", columns={
		@Column(name="address_id", attrName="addressId", label="地址id", isPK=true),
		@Column(name="user_id", attrName="userId", label="客户"),
		@Column(name="user_name", attrName="userName", label="联系人", queryType=QueryType.LIKE),
		@Column(name="user_mobile", attrName="userMobile", label="联系人电话"),
		@Column(name="user_address", attrName="userAddress", label="联系人地址"),
		@Column(name="user_doorplate", attrName="userDoorplate", label="门牌号"),
		@Column(name="is_default", attrName="isDefault", label="是否默认地址      字典数据", comment="是否默认地址      字典数据(sys_yes_no)"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserAddress extends DataEntity<UserAddress> {
	
	private static final long serialVersionUID = 1L;
	private String addressId;		// 地址id
	private String userId;		// 客户
	private String userName;		// 联系人
	private String userMobile;		// 联系人电话
	private String userAddress;		// 联系人地址
		private String userDoorplate;		// 门牌号
	private String isDefault;		// 是否默认地址      字典数据(sys_yes_no)
	
	public UserAddress() {
		this(null);
	}

	public UserAddress(String id){
		super(id);

	}
	
	public String getAddressId() {
		return addressId;
	}

	public UserAddress setAddressId(String addressId) {
		this.addressId = addressId;
		return this;
	}
	
	@Length(min=0, max=32, message="客户长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=32, message="联系人长度不能超过 32 个字符")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Length(min=0, max=200, message="联系人电话长度不能超过 200 个字符")
	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}
	
	@Length(min=0, max=300, message="联系人地址长度不能超过 300 个字符")
	public String getUserAddress() {
		return userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	
	@Length(min=0, max=300, message="门牌号长度不能超过 300 个字符")
	public String getUserDoorplate() {
		return userDoorplate;
	}

	public void setUserDoorplate(String userDoorplate) {
		this.userDoorplate = userDoorplate;
	}
	
	@Length(min=0, max=1, message="是否默认地址      字典数据长度不能超过 1 个字符")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}