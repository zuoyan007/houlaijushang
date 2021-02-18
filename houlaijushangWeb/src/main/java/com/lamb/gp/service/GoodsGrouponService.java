/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gp.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.lamb.gp.entity.GoodsGroupon;
import com.lamb.gp.dao.GoodsGrouponDao;

/**
 * 团购管理Service
 * @author dizj
 * @version 2021-02-07
 */
@Service
@Transactional(readOnly=true)
public class GoodsGrouponService extends CrudService<GoodsGrouponDao, GoodsGroupon> {
	
	/**
	 * 获取单条数据
	 * @param goodsGroupon
	 * @return
	 */
	@Override
	public GoodsGroupon get(GoodsGroupon goodsGroupon) {
		return super.get(goodsGroupon);
	}
	
	/**
	 * 查询分页数据
	 * @param goodsGroupon 查询条件
	 * @param goodsGroupon.page 分页对象
	 * @return
	 */
	@Override
	public Page<GoodsGroupon> findPage(GoodsGroupon goodsGroupon) {
		return super.findPage(goodsGroupon);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param goodsGroupon
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GoodsGroupon goodsGroupon) {
		super.save(goodsGroupon);
	}
	
	/**
	 * 更新状态
	 * @param goodsGroupon
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GoodsGroupon goodsGroupon) {
		super.updateStatus(goodsGroupon);
	}
	
	/**
	 * 删除数据
	 * @param goodsGroupon
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GoodsGroupon goodsGroupon) {
		super.delete(goodsGroupon);
	}
	
}