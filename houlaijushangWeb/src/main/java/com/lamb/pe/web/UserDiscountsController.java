/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.pe.web;

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
import com.lamb.pe.entity.UserDiscounts;
import com.lamb.pe.service.UserDiscountsService;

/**
 * 用户促销优惠卷关联表Controller
 * @author dizj
 * @version 2021-02-09
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userDiscounts")
public class UserDiscountsController extends BaseController {

	@Autowired
	private UserDiscountsService userDiscountsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserDiscounts get(String id, boolean isNewRecord) {
		return userDiscountsService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userDiscounts:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserDiscounts userDiscounts, Model model) {
		model.addAttribute("userDiscounts", userDiscounts);
		return "lamb/pe/userDiscountsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userDiscounts:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserDiscounts> listData(UserDiscounts userDiscounts, HttpServletRequest request, HttpServletResponse response) {
		userDiscounts.setPage(new Page<>(request, response));
		Page<UserDiscounts> page = userDiscountsService.findPage(userDiscounts);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userDiscounts:view")
	@RequestMapping(value = "form")
	public String form(UserDiscounts userDiscounts, Model model) {
		model.addAttribute("userDiscounts", userDiscounts);
		return "lamb/pe/userDiscountsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userDiscounts:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserDiscounts userDiscounts) {
		userDiscountsService.save(userDiscounts);
		return renderResult(Global.TRUE, text("保存用户促销优惠卷关联表成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pe:userDiscounts:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserDiscounts userDiscounts) {
		userDiscounts.setStatus(UserDiscounts.STATUS_DISABLE);
		userDiscountsService.updateStatus(userDiscounts);
		return renderResult(Global.TRUE, text("停用用户促销优惠卷关联表成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pe:userDiscounts:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserDiscounts userDiscounts) {
		userDiscounts.setStatus(UserDiscounts.STATUS_NORMAL);
		userDiscountsService.updateStatus(userDiscounts);
		return renderResult(Global.TRUE, text("启用用户促销优惠卷关联表成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userDiscounts:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserDiscounts userDiscounts) {
		userDiscountsService.delete(userDiscounts);
		return renderResult(Global.TRUE, text("删除用户促销优惠卷关联表成功！"));
	}
	
}