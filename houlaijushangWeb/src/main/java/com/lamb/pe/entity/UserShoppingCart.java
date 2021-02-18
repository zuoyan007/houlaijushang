/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.entity;

import com.lamb.gd.entity.GoodsDetail;
import org.hibernate.validator.constraints.Length;
import com.jeesite.common.entity.BaseEntity;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 购物车Entity
 * @author dizj
 * @version 2021-02-01
 */
@Table(name="user_shopping_cart", alias="a", columns={
		@Column(name="shopping_cart_id", attrName="shoppingCartId", label="购物车id", isPK=true),
		@Column(name="user_id", attrName="userId", label="用户id"),
		@Column(name="goods_id", attrName="goodsId", label="商品id"),
		@Column(name="goods_number", attrName="goodsNumber", label="商品数量"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class UserShoppingCart extends DataEntity<UserShoppingCart> {
	
	private static final long serialVersionUID = 1L;
	private String shoppingCartId;		// 购物车id
	private String userId;		// 用户id
	private String goodsId;		// 商品id
	private String goodsNumber;		// 商品数量
	private GoodsDetail goodsDetail;  //商品实体
	private boolean checked;         //是否选中照顾前端临时加的字段
	private String imgUrl;           //图片路径
	public UserShoppingCart() {
		this(null);
	}

	public UserShoppingCart(String id){
		super(id);
	}
	
	public String getShoppingCartId() {
		return shoppingCartId;
	}

	public void setShoppingCartId(String shoppingCartId) {
		this.shoppingCartId = shoppingCartId;
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
	
	@Length(min=0, max=32, message="商品数量长度不能超过 32 个字符")
	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public GoodsDetail getGoodsDetail() {
		return goodsDetail;
	}

	public void setGoodsDetail(GoodsDetail goodsDetail) {
		this.goodsDetail = goodsDetail;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}