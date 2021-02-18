/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import org.hibernate.validator.constraints.Length;

/**
 * 登录日志Entity
 * @author gexu
 * @version 2019-10-17
 */
@Table(name="sys_login_verify", alias="a", columns={
		@Column(name="login_log_id", attrName="loginLog.loginLogId", label="登录ip", isPK=true),
		@Column(name="user_id", attrName="userId", label="验证用户id", isPK=true),
		@Column(includeEntity= DataEntity.class),
		@Column(includeEntity= BaseEntity.class),
	}, orderBy="a.user_id ASC"
)
public class SysLoginVerify extends DataEntity<SysLoginVerify> {
	
	private static final long serialVersionUID = 1L;
	private SysLoginLog loginLog;		// 登录ip 父类
	private String userId;		// 验证用户id
	
	public SysLoginVerify() {
		this(null);
	}
	public SysLoginVerify(String id){
		super(id);
	}

	public SysLoginVerify(SysLoginLog loginLog, String userId){
		this.loginLog = loginLog;
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="登录ip长度不能超过 64 个字符")
	public SysLoginLog getLoginLog() {
		return loginLog;
	}

	public SysLoginVerify setLoginLog(SysLoginLog loginLog) {
		this.loginLog = loginLog;
		return this;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}