/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.entity;

import com.jeesite.common.collect.ListUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 字典数据表Entity
 * @author gexu
 * @version 2019-10-30
 */

public class SysDictDataVO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String dictCode;		// 字典编码
	private String dictLabel;		// 字典标签
	private String parentCode;		// 字典标签
	private String dictValue;		// 字典键值
	private String dictType;		// 字典类型
	private Integer treeSort;		// 字典类型
	private Integer treeLeaf;		// 字典类型
	private Integer treeLevel;		// 字典类型
	private String status;		// 字典类型
	private String remarks;		// 字典类型


	private List<SysDictDataVO> childList = ListUtils.newArrayList();



	public static List<SysDictDataVO> convertTree(List<SysDictData> dictList){
		List<SysDictDataVO> treeList0 =  new ArrayList<>();
		List<SysDictDataVO> treeList1 =  new ArrayList<>();
		List<SysDictDataVO> treeList2 =  new ArrayList<>();
		List<SysDictDataVO> treeList3 =  new ArrayList<>();
		List<SysDictDataVO> treeList4 =  new ArrayList<>();
		List<SysDictDataVO> treeList5 =  new ArrayList<>();
		SysDictDataVO lastTree0 = new SysDictDataVO();
		SysDictDataVO lastTree1 = new SysDictDataVO();
		SysDictDataVO lastTree2 = new SysDictDataVO();
		SysDictDataVO lastTree3 = new SysDictDataVO();
		SysDictDataVO lastTree4 = new SysDictDataVO();
		SysDictDataVO lastTree5 = new SysDictDataVO();
		for (int i = 0; i < dictList.size(); i++) {
			SysDictData dictData = dictList.get(i);
			SysDictDataVO dataVO = new SysDictDataVO();
			BeanUtils.copyProperties(dictData,dataVO);
			Integer nodeLevel = dictData.getTreeLevel();
			if(i==0){
				lastTree0 = dataVO;
				treeList0.add(lastTree0);
			}else{
				if(nodeLevel==0){
					lastTree0 = new SysDictDataVO();
					lastTree1 = new SysDictDataVO();
					lastTree2 = new SysDictDataVO();
					lastTree3 = new SysDictDataVO();
					lastTree4 = new SysDictDataVO();
					lastTree5 = new SysDictDataVO();

					treeList1 =  new ArrayList<>();
					treeList2 =  new ArrayList<>();
					treeList3 =  new ArrayList<>();
					treeList4 =  new ArrayList<>();
					treeList5 =  new ArrayList<>();
//                if(entity.getDictCode().e){
//
//                }
					lastTree0 = dataVO;
					treeList0.add(dataVO);
				}else if(nodeLevel==1){
					lastTree1 = new SysDictDataVO();
					lastTree2 = new SysDictDataVO();
					lastTree3 = new SysDictDataVO();
					lastTree4 = new SysDictDataVO();
					lastTree5 = new SysDictDataVO();

					treeList2 =  new ArrayList<>();
					treeList3 =  new ArrayList<>();
					treeList4 =  new ArrayList<>();
					treeList5 =  new ArrayList<>();

					lastTree1 = dataVO;
					treeList1.add(dataVO);
					lastTree0.setChildList(treeList1);
				} else if(nodeLevel==2){
					lastTree2 = new SysDictDataVO();
					lastTree3 = new SysDictDataVO();
					lastTree4 = new SysDictDataVO();
					lastTree5 = new SysDictDataVO();

					treeList3 =  new ArrayList<>();
					treeList4 =  new ArrayList<>();
					treeList5 =  new ArrayList<>();

					lastTree2 = dataVO;
					treeList2.add(dataVO);
					lastTree1.setChildList(treeList2);

				} else if(nodeLevel==3){
					lastTree3 = new SysDictDataVO();
					lastTree4 = new SysDictDataVO();
					lastTree5 = new SysDictDataVO();

					treeList4 =  new ArrayList<>();
					treeList5 =  new ArrayList<>();

					lastTree3 = dataVO;
					treeList3.add(dataVO);
					lastTree2.setChildList(treeList3);
				} else if(nodeLevel==4){
					lastTree4 = new SysDictDataVO();
					lastTree5 = new SysDictDataVO();

					treeList5 =  new ArrayList<>();

					lastTree4 = dataVO;
					treeList4.add(dataVO);
					lastTree3.setChildList(treeList4);
				} else if(nodeLevel==5){
					lastTree5 = new SysDictDataVO();

					lastTree5 = dataVO;
					treeList5.add(dataVO);
					lastTree4.setChildList(treeList5);
				}
			}
		}

		return treeList0;
	}


	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictLabel() {
		return dictLabel;
	}

	public void setDictLabel(String dictLabel) {
		this.dictLabel = dictLabel;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getDictType() {
		return dictType;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public Integer getTreeSort() {
		return treeSort;
	}

	public void setTreeSort(Integer treeSort) {
		this.treeSort = treeSort;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


	public List<SysDictDataVO> getChildList() {
		return childList;
	}

	public void setChildList(List<SysDictDataVO> childList) {
		this.childList = childList;
	}
}