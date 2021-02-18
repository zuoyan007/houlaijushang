/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gd.service;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.jeesite.modules.file.utils.FileUploadUtils;
import com.lamb.gd.dao.GoodsDetailDao;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.pe.dao.UserCollectsDao;
import com.lamb.pe.entity.UserCollects;
import com.lamb.util.UserKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品明细表Service
 * @author dizj
 * @version 2021-01-27
 */
@Service
@Transactional(readOnly=true)
public class GoodsDetailService extends CrudService<GoodsDetailDao, GoodsDetail> {
	@Autowired
	private FileUploadService fileUploadService;
	@Autowired
	private UserCollectsDao userCollectsDao;
	@Autowired
	private GoodsDetailDao goodsDetailDao;

	/**
	 * 获取单条数据
	 * @param goodsId
	 * @return
	 */
	@Override
	public GoodsDetail get(String goodsId) {
		GoodsDetail goodsDetail = super.get(goodsId);
		FileUpload selImage = new FileUpload();
		selImage.setBizKey(goodsId);
		goodsDetail.setImageList(fileUploadService.findList(selImage));
		String substring = goodsDetail.getGoodPrice().substring(1);
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
		//将现有价格转换为Double类型保留2为小数
		double goodPrice = Double.parseDouble(myformat.format(Double.parseDouble(substring)));
		//将原价转换为Double类型保留2为小数
		if (!"".equals(goodsDetail.getOriginalCost())&&goodsDetail.getOriginalCost()!=null){
			String substring1 = goodsDetail.getOriginalCost().substring(1);
			double originalCost = Double.parseDouble(myformat.format(Double.parseDouble(substring1)));
			goodsDetail.setDiscount(Double.parseDouble(myformat.format(goodPrice/originalCost)));
		}
		UserCollects userCollects = new UserCollects();
		userCollects.setUserId(UserKit.getUserId());
		userCollects.setGoodsId(goodsId);
		UserCollects byEntity = userCollectsDao.getByEntity(userCollects);
		if(byEntity!=null){
				goodsDetail.setIsCollects("0");
		}else {
			goodsDetail.setIsCollects("1");
		}

		return goodsDetail;
	}
	
	/**
	 * 查询分页数据
	 * @param goodsDetail 查询条件
	 * @return
	 */
	@Override
	public Page<GoodsDetail> findPage(GoodsDetail goodsDetail) {
		Page<GoodsDetail> page = super.findPage(goodsDetail);
		for (GoodsDetail list : page.getList()) {
			FileUpload selImage = new FileUpload();
			selImage.setBizKey(list.getGoodsId());
			list.setImageList(fileUploadService.findList(selImage));
		}
		return page;
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param goodsDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(GoodsDetail goodsDetail) {
		super.save(goodsDetail);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(goodsDetail.getId(), "goodsDetail_image");
	}
	
	/**
	 * 更新状态
	 * @param goodsDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(GoodsDetail goodsDetail) {
		super.updateStatus(goodsDetail);
	}
	
	/**
	 * 删除数据
	 * @param goodsDetail
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(GoodsDetail goodsDetail) {
		super.delete(goodsDetail);
	}



	/**
	 * 猜你喜欢
	 * @param
	 */

	@Transactional(readOnly=false)
	public List<GoodsDetail> findYouLike() {
		List<GoodsDetail> youLike = goodsDetailDao.findYouLike();
		for (GoodsDetail list : youLike) {
			FileUpload selImage = new FileUpload();
			selImage.setBizKey(list.getGoodsId());
			list.setImageList(fileUploadService.findList(selImage));
		}
		return youLike;
	}
}