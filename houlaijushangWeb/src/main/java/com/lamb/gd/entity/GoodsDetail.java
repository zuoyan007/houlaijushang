/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gd.entity;

import com.jeesite.common.collect.ListUtils;
import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.modules.file.entity.FileUpload;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 商品明细表Entity
 * @author dizj
 * @version 2021-01-27
 */
@Table(name="goods_detail", alias="a", columns={
		@Column(name="goods_id", attrName="goodsId", label="商品id", isPK=true),
		@Column(name="goods_name", attrName="goodsName", label="商品名称", queryType=QueryType.LIKE),
		@Column(name="goods_property", attrName="goodsProperty", label="商品属性", comment="商品属性(树形结构数据字典)   字典数据(goods_property)", queryType=QueryType.LIKE),
		@Column(name="sales", attrName="sales", label="销量"),
		@Column(name="repertory", attrName="repertory", label="库存"),
		@Column(name="choose", attrName="choose", label="量词"),
		@Column(name="goods_kind", attrName="goodsKind", label="量词类型", comment="量词类型(尺寸，套装，款式，重量)   字典数据(choose_type)"),
		@Column(name="original_cost", attrName="originalCost", label="原价"),
		@Column(name="discounts", attrName="discounts", label="优惠类型", comment="优惠类型(20减2，50减5，100减10等等)    字典数据(discounts_type)"),
		@Column(name="serve", attrName="serve", label="服务", comment="服务(无理由退换，假一赔十)    字典数据(user_service_type)"),
		@Column(name="integral", attrName="integral", label="积分"),
		@Column(name="good_price", attrName="goodPrice", label="商品价格"),
		@Column(name="is_sale", attrName="isSale", label="是否特价", comment="是否特价(0是，1是否)    字典数据(sys_yes_no)"),
		@Column(includeEntity=DataEntity.class),
		@Column(includeEntity=BaseEntity.class),
	}, orderBy="a.update_date DESC"
)
public class GoodsDetail extends DataEntity<GoodsDetail> {
	
	private static final long serialVersionUID = 1L;
	private String goodsId;		// 商品id
	private String goodsName;		// 商品名称
	private String goodsProperty;		// 商品属性(树形结构数据字典)   字典数据(goods_property)
	private String sales;		// 销量
	private String repertory;		// 库存
	private String choose;		// 量词
	private String goodsKind;		// 量词类型(尺寸，套装，款式，重量)   字典数据(choose_type)
	private String originalCost;		// 商品原价
	private String discounts;		// 优惠类型(20减2，50减5，100减10等等)    字典数据(discounts_type)
	private String serve;		// 服务(无理由退换，假一赔十)    字典数据(user_service_type)
	private String integral;		// 积分
	private String goodPrice;		// 商品价格
	private double discount;		// 商品折扣
	private String isSale;		// 是否特价(0是，1是否)    字典数据(sys_yes_no)
	private String isCollects;   //是否收藏(0是，1是否)
	private String number;        //选择商品数量(临时存储)

	private List<FileUpload> imageList = ListUtils.newArrayList();
	
	public GoodsDetail() {
		this(null);
	}

	public GoodsDetail(String id){
		super(id);
	}
	
	public String getGoodsId() {
		return goodsId;
	}

	public GoodsDetail setGoodsId(String goodsId) {
		this.goodsId = goodsId;
		return this;
	}
	
	@NotBlank(message="商品名称不能为空")
	@Length(min=0, max=128, message="商品名称长度不能超过 128 个字符")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	
	@NotBlank(message="商品属性不能为空")
	@Length(min=0, max=128, message="商品属性长度不能超过 128 个字符")
	public String getGoodsProperty() {
		return goodsProperty;
	}

	public void setGoodsProperty(String goodsProperty) {
		this.goodsProperty = goodsProperty;
	}
	
	@Length(min=0, max=32, message="销量长度不能超过 32 个字符")
	public String getSales() {
		return sales;
	}

	public void setSales(String sales) {
		this.sales = sales;
	}
	
	@Length(min=0, max=32, message="库存长度不能超过 32 个字符")
	public String getRepertory() {
		return repertory;
	}

	public void setRepertory(String repertory) {
		this.repertory = repertory;
	}
	
	@NotBlank(message="量词不能为空")
	@Length(min=0, max=32, message="量词长度不能超过 32 个字符")
	public String getChoose() {
		return choose;
	}

	public void setChoose(String choose) {
		this.choose = choose;
	}
	
	@NotBlank(message="量词类型不能为空")
	@Length(min=0, max=500, message="量词类型长度不能超过 500 个字符")
	public String getGoodsKind() {
		return goodsKind;
	}

	public void setGoodsKind(String goodsKind) {
		this.goodsKind = goodsKind;
	}
	
	@Length(min=0, max=32, message="原价长度不能超过 32 个字符")
	public String getOriginalCost() {
		return originalCost;
	}

	public void setOriginalCost(String goodsColour) {
		this.originalCost = goodsColour;
	}
	
	@Length(min=0, max=300, message="优惠类型长度不能超过 300 个字符")
	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}
	
	@Length(min=0, max=200, message="服务长度不能超过 200 个字符")
	public String getServe() {
		return serve;
	}

	public void setServe(String serve) {
		this.serve = serve;
	}
	
	@Length(min=0, max=32, message="积分长度不能超过 32 个字符")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
	@NotBlank(message="商品价格不能为空")
	@Length(min=0, max=32, message="商品价格长度不能超过 32 个字符")
	public String getGoodPrice() {
		return goodPrice;
	}

	public void setGoodPrice(String goodPrice) {
		this.goodPrice = goodPrice;
	}
	
	@Length(min=0, max=4, message="是否特价长度不能超过 4 个字符")
	public String getIsSale() {
		return isSale;
	}

	public void setIsSale(String isSale) {
		this.isSale = isSale;
	}

	public List<FileUpload> getImageList() {
		return imageList;
	}

	public void setImageList(List<FileUpload> imageList) {
		this.imageList = imageList;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getIsCollects() {
		return isCollects;
	}

	public void setIsCollects(String isCollects) {
		this.isCollects = isCollects;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
}