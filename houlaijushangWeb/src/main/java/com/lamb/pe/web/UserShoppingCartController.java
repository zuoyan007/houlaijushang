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
import com.lamb.pe.entity.UserShoppingCart;
import com.lamb.pe.service.UserShoppingCartService;

/**
 * 购物车Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userShoppingCart")
public class UserShoppingCartController extends BaseController {

	@Autowired
	private UserShoppingCartService userShoppingCartService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserShoppingCart get(String shoppingCartId, boolean isNewRecord) {
		return userShoppingCartService.get(shoppingCartId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userShoppingCart:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserShoppingCart userShoppingCart, Model model) {
		model.addAttribute("userShoppingCart", userShoppingCart);
		return "lamb/pe/userShoppingCartList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userShoppingCart:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserShoppingCart> listData(UserShoppingCart userShoppingCart, HttpServletRequest request, HttpServletResponse response) {
		userShoppingCart.setPage(new Page<>(request, response));
		Page<UserShoppingCart> page = userShoppingCartService.findPage(userShoppingCart);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userShoppingCart:view")
	@RequestMapping(value = "form")
	public String form(UserShoppingCart userShoppingCart, Model model) {
		model.addAttribute("userShoppingCart", userShoppingCart);
		return "lamb/pe/userShoppingCartForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userShoppingCart:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserShoppingCart userShoppingCart) {
		userShoppingCartService.save(userShoppingCart);
		return renderResult(Global.TRUE, text("保存购物车成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pe:userShoppingCart:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserShoppingCart userShoppingCart) {
		userShoppingCart.setStatus(UserShoppingCart.STATUS_DISABLE);
		userShoppingCartService.updateStatus(userShoppingCart);
		return renderResult(Global.TRUE, text("停用购物车成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pe:userShoppingCart:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserShoppingCart userShoppingCart) {
		userShoppingCart.setStatus(UserShoppingCart.STATUS_NORMAL);
		userShoppingCartService.updateStatus(userShoppingCart);
		return renderResult(Global.TRUE, text("启用购物车成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userShoppingCart:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserShoppingCart userShoppingCart) {
		userShoppingCartService.delete(userShoppingCart);
		return renderResult(Global.TRUE, text("删除购物车成功！"));
	}
	
}