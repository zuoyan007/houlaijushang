/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.entity;

import com.jeesite.common.entity.BaseEntity;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.Extend;
import com.jeesite.common.entity.TreeEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.JoinTable;
import com.jeesite.common.mybatis.annotation.JoinTable.Type;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;
import com.jeesite.common.utils.excel.fieldtype.OfficeType;
import com.jeesite.modules.sys.entity.DictType;
import com.lamb.util.sys.SheBaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * 字典数据表Entity
 * @author liuwei
 * @version 2019-10-30
 */
@Table(name="js_sys_dict_data", alias="a", columns={
		@Column(name="dict_code", attrName="dictCode", label="字典编码", isPK=true),
	    @Column(includeEntity= TreeEntity.class),
		@Column(name="dict_label", attrName="dictLabel", label="字典标签"),
		@Column(name="dict_value", attrName="dictValue", label="字典键值"),
		@Column(name="dict_type", attrName="dictType", label="字典类型"),
		@Column(name="is_sys", attrName="isSys", label="系统内置", comment="系统内置（1是 0否）"),
		@Column(name="description", attrName="description", label="字典描述"),
		@Column(name="css_style", attrName="cssStyle", label="css样式", comment="css样式（如：color:red)"),
		@Column(name="css_class", attrName="cssClass", label="css类名", comment="css类名（如：red）"),
		@Column(includeEntity= SheBaseEntity.class),
		@Column(includeEntity= DataEntity.class),
		@Column(includeEntity= BaseEntity.class),
	},joinTable={
		    @JoinTable(type= Type.LEFT_JOIN, entity= DictType.class, alias="o",attrName = "dictTypeBean",
				on="o.DICT_TYPE = a.DICT_TYPE",
				columns={@Column(includeEntity= DictType.class)}),
		}, orderBy=" a.dict_type,a.tree_sorts,a.dict_code "
)
public class SysDictData extends SheBaseEntity<SysDictData> {
	
	private static final long serialVersionUID = 1L;
	private String dictCode;		// 字典编码
	private String parentCode;		// 字典编码
	private String dictLabel;		// 字典标签
	private String dictValue;		// 字典键值
	private String dictType;		// 字典类型
	private Integer treeSort;		// 排序号
	private Integer treeLeaf;
	private Integer treeLevel;
	private DictType dictTypeBean;		// 字典类型实体
	private String isSys;		// 系统内置（1是 0否）
	private String description;		// 字典描述
	private String cssStyle;		// css样式（如：color:red)
	private String cssClass;		// css类名（如：red）
	private Extend extend;		// 扩展字段
	private String treeNames;

	private String keyword;

	private String[] dictValueArray;		// 字典键值

	private String tmpIndustryLevel;  //临时存储行业等级
	private String [] industryCodeArray; //临时字段存储 行业code
	private String [] sonIndustryCodeArray;


	//用于数据字典同步判断时，用，判断是否是app同步数据字典。
	private Integer isAppUse;


	public SysDictData(){
	}

	public SysDictData(String id){
		super(id);
	}

	@Valid
	@ExcelFields({
			@ExcelField(title = "字典名称", attrName = "dictLabel", align = ExcelField.Align.CENTER, sort = 10, fieldType = OfficeType.class),
			@ExcelField(title = "关键词", attrName = "asName", align = ExcelField.Align.RIGHT, sort = 20),
			@ExcelField(title = "字典类型", attrName = "dictType", align = ExcelField.Align.RIGHT, sort = 30),
			@ExcelField(title = "更新时间", attrName = "updateDate", align = ExcelField.Align.RIGHT, sort = 40),
	})




	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	
	@NotBlank(message="字典标签不能为空")
	@Length(min=0, max=100, message="字典标签长度不能超过 100 个字符")
	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}
	
	@NotBlank(message="字典键值不能为空")
	@Length(min=0, max=100, message="字典键值长度不能超过 100 个字符")
	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	
	@NotBlank(message="字典类型不能为空")
	@Length(min=0, max=100, message="字典类型长度不能超过 100 个字符")
	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	
	@NotBlank(message="系统内置不能为空")
	@Length(min=0, max=1, message="系统内置长度不能超过 1 个字符")
	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}
	
	@Length(min=0, max=500, message="字典描述长度不能超过 500 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Length(min=0, max=500, message="css样式长度不能超过 500 个字符")
	public String getCssStyle() {
		return cssStyle;
	}

	public void setCssStyle(String cssStyle) {
		this.cssStyle = cssStyle;
	}
	
	@Length(min=0, max=500, message="css类名长度不能超过 500 个字符")
	public String getCssClass() {
		return cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	
	public Extend getExtend() {
		return extend;
	}

	public void setExtend(Extend extend) {
		this.extend = extend;
	}


	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public DictType getDictTypeBean() {
		return dictTypeBean;
	}

	public void setDictTypeBean(DictType dictTypeBean) {
		this.dictTypeBean = dictTypeBean;
	}

	public String[] getDictValueArray() {
		return dictValueArray;
	}

	public void setDictValueArray(String[] dictValueArray) {
		this.dictValueArray = dictValueArray;
	}

	public Integer getTreeSort() {
		return treeSort;
	}

	public void setTreeSort(Integer treeSort) {
		this.treeSort = treeSort;
	}

	public Integer getIsAppUse() {
		return isAppUse;
	}

	public void setIsAppUse(Integer isAppUse) {
		this.isAppUse = isAppUse;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getTmpIndustryLevel() {
		return tmpIndustryLevel;
	}

	public void setTmpIndustryLevel(String tmpIndustryLevel) {
		this.tmpIndustryLevel = tmpIndustryLevel;
	}

	public String[] getIndustryCodeArray() {
		return industryCodeArray;
	}

	public void setIndustryCodeArray(String[] industryCodeArray) {
		this.industryCodeArray = industryCodeArray;
	}

	public String getTreeNames() {
		return treeNames;
	}

	public void setTreeNames(String treeNames) {
		this.treeNames = treeNames;
	}

	public String[] getSonIndustryCodeArray() {
		return sonIndustryCodeArray;
	}

	public void setSonIndustryCodeArray(String[] sonIndustryCodeArray) {
		this.sonIndustryCodeArray = sonIndustryCodeArray;
	}

	public Integer getTreeLeaf() {
		return treeLeaf;
	}

	public void setTreeLeaf(Integer treeLeaf) {
		this.treeLeaf = treeLeaf;
	}

	public Integer getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(Integer treeLevel) {
		this.treeLevel = treeLevel;
	}

}