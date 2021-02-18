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
import com.lamb.pe.entity.UserCollects;
import com.lamb.pe.service.UserCollectsService;

/**
 * 用户收藏Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userCollects")
public class UserCollectsController extends BaseController {

	@Autowired
	private UserCollectsService userCollectsService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserCollects get(String collectId, boolean isNewRecord) {
		return userCollectsService.get(collectId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userCollects:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserCollects userCollects, Model model) {
		model.addAttribute("userCollects", userCollects);
		return "lamb/pe/userCollectsList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userCollects:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserCollects> listData(UserCollects userCollects, HttpServletRequest request, HttpServletResponse response) {
		userCollects.setPage(new Page<>(request, response));
		Page<UserCollects> page = userCollectsService.findPage(userCollects);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userCollects:view")
	@RequestMapping(value = "form")
	public String form(UserCollects userCollects, Model model) {
		model.addAttribute("userCollects", userCollects);
		return "lamb/pe/userCollectsForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userCollects:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserCollects userCollects) {
		userCollectsService.save(userCollects);
		return renderResult(Global.TRUE, text("保存用户收藏成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pe:userCollects:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserCollects userCollects) {
		userCollects.setStatus(UserCollects.STATUS_DISABLE);
		userCollectsService.updateStatus(userCollects);
		return renderResult(Global.TRUE, text("停用用户收藏成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pe:userCollects:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserCollects userCollects) {
		userCollects.setStatus(UserCollects.STATUS_NORMAL);
		userCollectsService.updateStatus(userCollects);
		return renderResult(Global.TRUE, text("启用用户收藏成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userCollects:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserCollects userCollects) {
		userCollectsService.delete(userCollects);
		return renderResult(Global.TRUE, text("删除用户收藏成功！"));
	}
	
}