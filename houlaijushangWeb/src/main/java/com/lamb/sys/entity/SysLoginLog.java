/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.network.IpUtils;
import com.jeesite.modules.sys.entity.User;
import com.lamb.cons.Dict;
import com.lamb.util.UserKit;
import org.hibernate.validator.constraints.Length;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 登录日志Entity
 * @author gexu
 * @version 2019-10-17
 */
@Table(name="sys_login_log", alias="a", columns={
		@Column(name="login_log_id", attrName="loginLogId", label="主键", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="app_type", attrName="appType", label="软件类型"),
		@Column(name="login_ip", attrName="loginIp", label="登录ip",queryType = QueryType.RIGHT_LIKE),
		@Column(name="device_imei", attrName="deviceImei", label="device_imei",queryType = QueryType.LIKE),
		@Column(name="device_sn", attrName="deviceSn", label="device_sn",queryType = QueryType.LIKE),
		@Column(name="device_mac", attrName="deviceMac", label="设备mac",queryType = QueryType.LIKE),
		@Column(name="device_model", attrName="deviceModel", label="型号"),
		@Column(name="app_version", attrName="appVersion", label="软件版本"),
		@Column(name="os_version", attrName="osVersion", label="os版本"),
		@Column(name="verify_count", attrName="verifyCount", label="verify_count"),
		@Column(name="verify_qrcode", attrName="verifyQrcode", label="verify_qrcode"),
		@Column(name="login_status", attrName="loginStatus", label="状态 10、待辅助验证20、失败。30、成功"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity= BaseEntity.class),

	}, joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= User.class, attrName="user", alias="userName",
				on="a.user_id = userName.user_code",
				columns={
						@Column(name="user_code", label="用户编码", isPK=true),
						@Column(name="user_name", label="姓名")
				}),
	},orderBy="a.create_date DESC"
)
public class SysLoginLog extends DataEntity<SysLoginLog> {
	
	private static final long serialVersionUID = 1L;
	private String loginLogId;		// 主键
	private String userId;		// 用户ID
	private String appType;		// 软件类型：执法app，领导app，执法pc
	private String loginIp;		// 登录ip
	private String deviceImei;		// device_imei
	private String deviceSn;		// device_sn
	private String deviceMac;		// 设备mac
	private String deviceModel;		// 型号
	private String appVersion;		// app版本
	private String osVersion;		// os版本
	private Integer verifyCount;		// verify_count
	private String verifyQrcode;		// verify_qrcode
	private Integer loginStatus;		// 状态 10、待辅助验证20、失败。30、成功
	private List<SysLoginVerify> loginVerifyList = ListUtils.newArrayList();		// 子表列表

	private User user;

	private Date beginTime;
	private Date endTime;

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SysLoginLog() {
		this(null);
	}

	public SysLoginLog(String id){
		super(id);
	}
	
	public SysLoginLog(String userId, Integer loginStatus) {
		super();
		this.userId = userId;
		this.loginStatus = loginStatus;
	}

	public SysLoginLog init(HttpServletRequest request) {
		this.appType = Dict.SysType.App.name();
		this.userId = UserKit.getUserId();
		this.loginIp = IpUtils.getRemoteAddr(request);
		this.deviceImei = request.getHeader("deviceImei");
		this.deviceSn = request.getHeader("deviceSn");
		this.deviceMac = request.getHeader("deviceMac");
		this.deviceModel = request.getHeader("deviceModel");
		this.appVersion = request.getHeader("appVersion");
		this.osVersion = request.getHeader("osVersion");
		this.verifyCount = 0;
		this.loginStatus = Dict.SysLoginLogStatus.success.getCode();
		this.createDate = new Date();
		this.updateDate = new Date();
		this.createBy = this.userId;
		this.updateBy = this.userId;
		return this;
	}
	public String getLoginLogId() {
		return loginLogId;
	}

	public void setLoginLogId(String loginLogId) {
		this.loginLogId = loginLogId;
	}
	
	@Length(min=0, max=64, message="用户ID长度不能超过 64 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=16, message="登录ip长度不能超过 16 个字符")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	
	@Length(min=0, max=64, message="device_imei长度不能超过 64 个字符")
	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	
	@Length(min=0, max=64, message="device_sn长度不能超过 64 个字符")
	public String getDeviceSn() {
		return deviceSn;
	}

	public void setDeviceSn(String deviceSn) {
		this.deviceSn = deviceSn;
	}
	
	@Length(min=0, max=64, message="设备mac长度不能超过 64 个字符")
	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}
	
	@Length(min=0, max=64, message="型号长度不能超过 64 个字符")
	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}
	
	@Length(min=0, max=16, message="app版本长度不能超过 16 个字符")
	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	@Length(min=0, max=16, message="os版本长度不能超过 16 个字符")
	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public Integer getVerifyCount() {
		return verifyCount;
	}

	public void setVerifyCount(Integer verifyCount) {
		this.verifyCount = verifyCount;
	}
	
	@Length(min=0, max=128, message="verify_qrcode长度不能超过 128 个字符")
	public String getVerifyQrcode() {
		return verifyQrcode;
	}

	public void setVerifyQrcode(String verifyQrcode) {
		this.verifyQrcode = verifyQrcode;
	}
	
	@Length(min=0, max=1, message="状态 10、待辅助验证20、失败。30、成功长度不能超过 1 个字符")
	public Integer getLoginStatus() {
		return loginStatus;
	}

	public SysLoginLog setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
		return this;
	}
	
	public List<SysLoginVerify> getLoginVerifyList() {
		return loginVerifyList;
	}

	public void setLoginVerifyList(List<SysLoginVerify> loginVerifyList) {
		this.loginVerifyList = loginVerifyList;
	}
	
	public Date getCreateDate_gt(){
		return sqlMap.getWhere().getValue("create_date", QueryType.GT);
	}

	public SysLoginLog setCreateDate_gt(Date createDate){
		sqlMap.getWhere().and("create_date", QueryType.GT, createDate);
		return this;
	}

	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	/**
	 * 设置指定  时间字段的查询条件。
	 * @param sysLoginLog
	 * @param timeColumnName
	 * @return
	 */
	public SysLoginLog setTimeSql(SysLoginLog sysLoginLog, String timeColumnName) {
		Date beginTime = sysLoginLog.getBeginTime();
		Date endTime = sysLoginLog.getEndTime();
		if (beginTime != null) {
			sysLoginLog.getSqlMap().getWhere().and(timeColumnName, QueryType.GTE, beginTime);
		}
		if (endTime != null) {
			sysLoginLog.getSqlMap().getWhere().and(timeColumnName, QueryType.LTE, endTime);
		}
		return this;
	}

	/**
	 *设备号：模糊查询（包含 设备imei  设备SN 设备MAC）
	 * @param sysLoginLog
	 * @param deviceNum
	 * @return
	 */
	public SysLoginLog setDeviceNumber(SysLoginLog sysLoginLog, String deviceNum){
		sysLoginLog.getSqlMap().getWhere().andBracket("device_mac", QueryType.LIKE,deviceNum).or("device_imei", QueryType.LIKE,deviceNum).or("device_sn", QueryType.LIKE,deviceNum).endBracket();
		return this;
	}
}