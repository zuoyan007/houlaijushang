/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.entity;

import javax.validation.constraints.NotBlank;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.sys.entity.DictData;
import com.lamb.cp.entity.CouponType;
import com.lamb.gd.entity.GoodsDetail;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.List;

/**
 * 用户收藏Entity
 * @author dizj
 * @version 2021-02-01
 */
@Table(name="user_collects", alias="a", columns={
		@Column(name="collect_id", attrName="collectId", label="收藏id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="goods_id", attrName="goodsId", label="商品id"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, joinTable={
		@JoinTable(type= JoinTable.Type.LEFT_JOIN, entity= GoodsDetail.class, alias="goodsDetail",attrName = "goodsDetail",
				on="GoodsDetail.goods_id = a.goods_id",
				columns={@Column(includeEntity= GoodsDetail.class)}),
}, orderBy="a.update_date DESC"
)
public class UserCollects extends DataEntity<UserCollects> {
	
	private static final long serialVersionUID = 1L;
	private String collectId;		// 收藏id
	private String userId;		// 用户id
	private String goodsId;		// 商品id
	private GoodsDetail goodsDetail; //商品信息
	private List<FileUpload> imageList = ListUtils.newArrayList();
	
	public UserCollects() {
		this(null);
	}

	public UserCollects(String id){
		super(id);
	}
	
	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	
	@NotBlank(message="用户id不能为空")
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

	public void setGoodsId(String doodsId) {
		this.goodsId = doodsId;
	}

	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public List<FileUpload> getImageList() {
		return imageList;
	}

	public void setImageList(List<FileUpload> imageList) {
		this.imageList = imageList;
	}
}