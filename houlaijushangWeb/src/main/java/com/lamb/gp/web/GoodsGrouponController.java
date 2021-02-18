/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.gp.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.lamb.gp.entity.GoodsGroupon;
import com.lamb.gp.service.GoodsGrouponService;

/**
 * 团购管理Controller
 * @author dizj
 * @version 2021-02-07
 */
@Controller
@RequestMapping(value = "${adminPath}/gp/goodsGroupon")
public class GoodsGrouponController extends BaseController {

	@Autowired
	private GoodsGrouponService goodsGrouponService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public GoodsGroupon get(String grouponId, boolean isNewRecord) {
		return goodsGrouponService.get(grouponId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("gp:goodsGroupon:view")
	@RequestMapping(value = {"list", ""})
	public String list(GoodsGroupon goodsGroupon, Model model) {
		model.addAttribute("goodsGroupon", goodsGroupon);
		return "lamb/gp/goodsGrouponList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("gp:goodsGroupon:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<GoodsGroupon> listData(GoodsGroupon goodsGroupon, HttpServletRequest request, HttpServletResponse response) {
		goodsGroupon.setPage(new Page<>(request, response));
		Page<GoodsGroupon> page = goodsGrouponService.findPage(goodsGroupon);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("gp:goodsGroupon:view")
	@RequestMapping(value = "form")
	public String form(GoodsGroupon goodsGroupon, Model model) {
		model.addAttribute("goodsGroupon", goodsGroupon);
		return "lamb/gp/goodsGrouponForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("gp:goodsGroupon:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated GoodsGroupon goodsGroupon) {
		goodsGrouponService.save(goodsGroupon);
		return renderResult(Global.TRUE, text("保存团购管理成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("gp:goodsGroupon:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(GoodsGroupon goodsGroupon) {
		goodsGroupon.setStatus(GoodsGroupon.STATUS_DISABLE);
		goodsGrouponService.updateStatus(goodsGroupon);
		return renderResult(Global.TRUE, text("停用团购管理成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("gp:goodsGroupon:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(GoodsGroupon goodsGroupon) {
		goodsGroupon.setStatus(GoodsGroupon.STATUS_NORMAL);
		goodsGrouponService.updateStatus(goodsGroupon);
		return renderResult(Global.TRUE, text("启用团购管理成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("gp:goodsGroupon:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(GoodsGroupon goodsGroupon) {
		goodsGrouponService.delete(goodsGroupon);
		return renderResult(Global.TRUE, text("删除团购管理成功！"));
	}
	
}