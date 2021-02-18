/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.risk.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.sys.entity.DictData;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * risk_pointEntity
 * @author 狄张杰
 * @version 2020-11-30
 */
@Table(name="risk_point", alias="a", columns={
		@Column(name="risk_point_id", attrName="riskPointId", label="主键", isPK=true),
		@Column(name="risk_point_type", attrName="riskPointType", label="风险类型"),
		@Column(name="risk_point_name", attrName="riskPointName", label="风险名称", queryType=QueryType.LIKE),
		@Column(name="address", attrName="address", label="地址"),
		@Column(name="longitude", attrName="longitude", label="经度"),
		@Column(name="latitude", attrName="latitude", label="维度"),
		@Column(name="linkman", attrName="linkman", label="联系人"),
		@Column(name="linkman_phone", attrName="linkmanPhone", label="联系电话"),
		@Column(name="describe", attrName="describe", label="风险描述"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	},joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= DictData.class, alias="DictData" ,attrName="this",
				on="a.risk_point_type= DictData.goods_id and DictData.`dict_type`='risk_point_type'",
				columns={@Column(name="dict_label", attrName="riskPointTypeName", label="风险类型名称"),}),
},  orderBy="a.update_date DESC"
)
public class RiskPoint extends DataEntity<RiskPoint> {
	
	private static final long serialVersionUID = 1L;
	private String riskPointId;		// 主键
	private String riskPointType;		// 风险类型
	private String riskPointName;		// 风险名称
	private String address;		// 地址
	private Double longitude;		// 经度
	private Double latitude;		// 维度
	private String linkman;		// 联系人
	private String linkmanPhone;		// 联系电话
	private String describe;		// 风险描述
	private List<FileUpload> imageList = ListUtils.newArrayList();
	private String riskPointTypeName;

	public RiskPoint() {
		this(null);
	}

	public RiskPoint(String id){
		super(id);
	}
	
	public String getRiskPointId() {
		return riskPointId;
	}

	public void setRiskPointId(String riskPointId) {
		this.riskPointId = riskPointId;
	}
	
	@Length(min=0, max=64, message="风险类型长度不能超过 64 个字符")
	public String getRiskPointType() {
		return riskPointType;
	}

	public void setRiskPointType(String riskPointType) {
		this.riskPointType = riskPointType;
	}
	
	@NotBlank(message="风险名称不能为空")
	@Length(min=0, max=300, message="风险名称长度不能超过 300 个字符")
	public String getRiskPointName() {
		return riskPointName;
	}

	public void setRiskPointName(String riskPointName) {
		this.riskPointName = riskPointName;
	}
	
	@NotBlank(message="地址不能为空")
	@Length(min=0, max=500, message="地址长度不能超过 500 个字符")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@NotBlank(message="联系人不能为空")
	@Length(min=0, max=100, message="联系人长度不能超过 100 个字符")
	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	
	@Length(min=0, max=100, message="联系电话长度不能超过 100 个字符")
	public String getLinkmanPhone() {
		return linkmanPhone;
	}

	public void setLinkmanPhone(String linkmanPhone) {
		this.linkmanPhone = linkmanPhone;
	}
	
	@Length(min=0, max=3000, message="风险描述长度不能超过 3000 个字符")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public List<FileUpload> getImageList() {
		return imageList;
	}

	public void setImageList(List<FileUpload> imageList) {
		this.imageList = imageList;
	}

	public String getRiskPointTypeName() {
		return riskPointTypeName;
	}

	public void setRiskPointTypeName(String riskPointTypeName) {
		this.riskPointTypeName = riskPointTypeName;
	}
}