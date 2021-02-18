/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gp.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.modules.file.entity.FileUpload;
import com.lamb.cp.entity.CouponType;
import com.lamb.gd.entity.GoodsDetail;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 团购管理Entity
 * @author dizj
 * @version 2021-02-07
 */
@Table(name="goods_groupon", alias="a", columns={
		@Column(name="groupon_id", attrName="grouponId", label="团购id", isPK=true),
		@Column(name="goods_id", attrName="goodsId", label="商品id"),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="groupon_target", attrName="grouponTarget", label="团购目标人数"),
		@Column(name="groupon_now_number", attrName="grouponNowNumber", label="目前团购人数"),
		@Column(name="is_succeed", attrName="isSucceed", label="是否成团"),
		@Column(name="groupon_end_time", attrName="grouponEndTime", label="团购结束时间"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
		@Column(name="column_16", attrName="column16", label="column_16"),
	},joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= GoodsDetail.class, alias="goodsDetail",attrName = "goodsDetail",
				on="GoodsDetail.goods_id = a.goods_id",
				columns={@Column(includeEntity= GoodsDetail.class)}),
}, orderBy="a.update_date DESC"
)
public class GoodsGroupon extends DataEntity<GoodsGroupon> {
	
	private static final long serialVersionUID = 1L;
	private String grouponId;		// 团购id
	private String goodsId;		// 商品id
	private String goodsName;		// 商品名称
	private Long grouponTarget;		// 团购目标人数
	private Long grouponNowNumber;		// 目前团购人数
	private Long isSucceed;		// 是否成团
	private Date grouponEndTime;		// 团购结束时间
	private String column16;		// column_16
	private List<FileUpload> imageList = ListUtils.newArrayList();
	private GoodsDetail goodsDetail;		// 商品详情实体
	private double percent;             //目前进展


	
	public GoodsGroupon() {
		this(null);
	}

	public GoodsGroupon(String id){
		super(id);
	}
	
	public String getGrouponId() {
		return grouponId;
	}

	public void setGrouponId(String grouponId) {
		this.grouponId = grouponId;
	}
	
	@Length(min=0, max=32, message="商品id长度不能超过 32 个字符")
	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	
	@Length(min=0, max=200, message="商品名称长度不能超过 200 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	public Long getGrouponTarget() {
		return grouponTarget;
	}

	public void setGrouponTarget(Long grouponTarget) {
		this.grouponTarget = grouponTarget;
	}
	
	public Long getGrouponNowNumber() {
		return grouponNowNumber;
	}

	public void setGrouponNowNumber(Long grouponNowNumber) {
		this.grouponNowNumber = grouponNowNumber;
	}
	
	public Long getIsSucceed() {
		return isSucceed;
	}

	public void setIsSucceed(Long isSucceed) {
		this.isSucceed = isSucceed;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getGrouponEndTime() {
		return grouponEndTime;
	}

	public void setGrouponEndTime(Date grouponEndTime) {
		this.grouponEndTime = grouponEndTime;
	}
	
	@Length(min=0, max=10, message="column_16长度不能超过 10 个字符")
	public String getColumn16() {
		return column16;
	}

	public void setColumn16(String column16) {
		this.column16 = column16;
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

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}
}