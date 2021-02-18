/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gd.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.codec.EncodeUtils;
import com.lamb.cp.entity.CouponType;
import com.lamb.util.sys.StringKit;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.gd.service.GoodsDetailService;

/**
 * 商品明细表Controller
 * @author dizj
 * @version 2021-01-27
 */
@Controller
@RequestMapping(value = "${adminPath}/gd/goodsDetail")
public class GoodsDetailController extends BaseController {

	@Autowired
	private GoodsDetailService goodsDetailService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public GoodsDetail get(String goodsId, boolean isNewRecord) {
		GoodsDetail goodsDetail = goodsDetailService.get(goodsId, isNewRecord);
		return goodsDetail;
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("gd:goodsDetail:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsDetail goodsDetail, Model model) {
		model.addAttribute("goodsDetail", goodsDetail);
		return "lamb/gd/goodsDetailList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("gd:goodsDetail:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<GoodsDetail> listData(GoodsDetail goodsDetail, HttpServletRequest request, HttpServletResponse response) {
		goodsDetail.setPage(new Page<>(request, response));
		Page<GoodsDetail> page = goodsDetailService.findPage(goodsDetail);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("gd:goodsDetail:view")
	@RequestMapping(value = "form")
	public String form(GoodsDetail goodsDetail, Model model) {
		model.addAttribute("goodsDetail", goodsDetail);
		return "lamb/gd/goodsDetailForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("gd:goodsDetail:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated GoodsDetail goodsDetail) {
		goodsDetailService.save(goodsDetail);
		return renderResult(Global.TRUE, text("保存商品明细表成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("gd:goodsDetail:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(GoodsDetail goodsDetail) {
		goodsDetailService.delete(goodsDetail); 
		return renderResult(Global.TRUE, text("删除商品明细表成功！"));
	}



	@RequestMapping(value = "goodsDetailSelect")
	public String smContractSelect(GoodsDetail goodsDetail, String selectData, String selectStr, Model model) {
		String selectStrJson = EncodeUtils.decodeUrl(selectData);
		if(StringKit.isNotBlank(selectStr)){
			selectStrJson = EncodeUtils.decodeUrl(selectStr);
		}
		//如果自定义数据不为空则为修改时传入
		model.addAttribute("selectData", selectStrJson);
		model.addAttribute("GoodsDetail",goodsDetail);
		return "lamb/gd/goodsDetailSelect";
	}
	
}