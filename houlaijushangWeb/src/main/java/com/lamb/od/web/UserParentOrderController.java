/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.od.web;

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
import com.lamb.od.entity.UserParentOrder;
import com.lamb.od.service.UserParentOrderService;

/**
 * 用户主订单Controller
 * @author dizj
 * @version 2021-02-04
 */
@Controller
@RequestMapping(value = "${adminPath}/od/userParentOrder")
public class UserParentOrderController extends BaseController {

	@Autowired
	private UserParentOrderService userParentOrderService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserParentOrder get(String parentOrderId, boolean isNewRecord) {
		return userParentOrderService.get(parentOrderId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("od:userParentOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserParentOrder userParentOrder, Model model) {
		model.addAttribute("userParentOrder", userParentOrder);
		return "lamb/od/userParentOrderList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("od:userParentOrder:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserParentOrder> listData(UserParentOrder userParentOrder, HttpServletRequest request, HttpServletResponse response) {
		userParentOrder.setPage(new Page<>(request, response));
		Page<UserParentOrder> page = userParentOrderService.findPage(userParentOrder);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("od:userParentOrder:view")
	@RequestMapping(value = "form")
	public String form(UserParentOrder userParentOrder, Model model) {
		model.addAttribute("userParentOrder", userParentOrder);
		return "lamb/od/userParentOrderForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("od:userParentOrder:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserParentOrder userParentOrder) {
		userParentOrderService.save(userParentOrder);
		return renderResult(Global.TRUE, text("保存用户主订单成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("od:userParentOrder:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserParentOrder userParentOrder) {
		userParentOrder.setStatus(UserParentOrder.STATUS_DISABLE);
		userParentOrderService.updateStatus(userParentOrder);
		return renderResult(Global.TRUE, text("停用用户主订单成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("od:userParentOrder:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserParentOrder userParentOrder) {
		userParentOrder.setStatus(UserParentOrder.STATUS_NORMAL);
		userParentOrderService.updateStatus(userParentOrder);
		return renderResult(Global.TRUE, text("启用用户主订单成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("od:userParentOrder:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserParentOrder userParentOrder) {
		userParentOrderService.delete(userParentOrder);
		return renderResult(Global.TRUE, text("删除用户主订单成功！"));
	}
	
}