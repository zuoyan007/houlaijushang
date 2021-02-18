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
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.service.UserIntegralService;

/**
 * 用户积分Controller
 * @author dizj
 * @version 2021-02-12
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userIntegral")
public class UserIntegralController extends BaseController {

	@Autowired
	private UserIntegralService userIntegralService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserIntegral get(String integralId, boolean isNewRecord) {
		return userIntegralService.get(integralId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userIntegral:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserIntegral userIntegral, Model model) {
		model.addAttribute("userIntegral", userIntegral);
		return "lamb/pe/userIntegralList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userIntegral:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserIntegral> listData(UserIntegral userIntegral, HttpServletRequest request, HttpServletResponse response) {
		userIntegral.setPage(new Page<>(request, response));
		Page<UserIntegral> page = userIntegralService.findPage(userIntegral);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userIntegral:view")
	@RequestMapping(value = "form")
	public String form(UserIntegral userIntegral, Model model) {
		model.addAttribute("userIntegral", userIntegral);
		return "lamb/pe/userIntegralForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userIntegral:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserIntegral userIntegral) {
		userIntegralService.save(userIntegral);
		return renderResult(Global.TRUE, text("保存用户积分成功！"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userIntegral:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserIntegral userIntegral) {
		userIntegralService.delete(userIntegral);
		return renderResult(Global.TRUE, text("删除用户积分成功！"));
	}
	
}