/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.dao;

import com.jeesite.common.dao.TreeDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.sys.entity.DictType;
import com.lamb.sys.entity.SysDictData;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

/**
 * 字典数据表DAO接口
 * @author liuwei
 * @version 2019-10-30
 */
@MyBatisDao
public interface SysDictDataDao extends TreeDao<SysDictData> {
	/**
	 * 数据同步专用
	 * @param sysDictData
	 * @return
	 */
	List<SysDictData> syncList(SysDictData sysDictData);

	List<DictType> syncType(DictType DictType);

	/**
	 * 同步 数据有变化的树
	 * @return
	 */
	List<DictType> syncTreeType(Map<String,Object> map);

	List<SysDictData> checkPointCodeByIndustryCode(@Param("SysDictData")SysDictData sysDictData, @Param("useType")String useType);

    List<SysDictData> checkSonDictData(SysDictData sysDictData);
}