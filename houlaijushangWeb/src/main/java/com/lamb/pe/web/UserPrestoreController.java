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
import com.lamb.pe.entity.UserPrestore;
import com.lamb.pe.service.UserPrestoreService;

/**
 * 用户预存Controller
 * @author dizj
 * @version 2021-02-11
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userPrestore")
public class UserPrestoreController extends BaseController {

	@Autowired
	private UserPrestoreService userPrestoreService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserPrestore get(String prestoreId, boolean isNewRecord) {
		return userPrestoreService.get(prestoreId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userPrestore:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserPrestore userPrestore, Model model) {
		model.addAttribute("userPrestore", userPrestore);
		return "lamb/pe/userPrestoreList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userPrestore:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserPrestore> listData(UserPrestore userPrestore, HttpServletRequest request, HttpServletResponse response) {
		userPrestore.setPage(new Page<>(request, response));
		Page<UserPrestore> page = userPrestoreService.findPage(userPrestore);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userPrestore:view")
	@RequestMapping(value = "form")
	public String form(UserPrestore userPrestore, Model model) {
		model.addAttribute("userPrestore", userPrestore);
		return "lamb/pe/userPrestoreForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userPrestore:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserPrestore userPrestore) {
		userPrestoreService.save(userPrestore);
		return renderResult(Global.TRUE, text("保存用户预存成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pe:userPrestore:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserPrestore userPrestore) {
		userPrestore.setStatus(UserPrestore.STATUS_DISABLE);
		userPrestoreService.updateStatus(userPrestore);
		return renderResult(Global.TRUE, text("停用用户预存成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pe:userPrestore:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserPrestore userPrestore) {
		userPrestore.setStatus(UserPrestore.STATUS_NORMAL);
		userPrestoreService.updateStatus(userPrestore);
		return renderResult(Global.TRUE, text("启用用户预存成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userPrestore:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserPrestore userPrestore) {
		userPrestoreService.delete(userPrestore);
		return renderResult(Global.TRUE, text("删除用户预存成功！"));
	}
	
}