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
import com.lamb.pe.entity.UserAddress;
import com.lamb.pe.service.UserAddressService;

/**
 * 用户地址Controller
 * @author dizj
 * @version 2021-02-02
 */
@Controller
@RequestMapping(value = "${adminPath}/pe/userAddress")
public class UserAddressController extends BaseController {

	@Autowired
	private UserAddressService userAddressService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public UserAddress get(String addressId, boolean isNewRecord) {
		return userAddressService.get(addressId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("pe:userAddress:view")
	@RequestMapping(value = {"list", ""})
	public String list(UserAddress userAddress, Model model) {
		model.addAttribute("userAddress", userAddress);
		return "lamb/pe/userAddressList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("pe:userAddress:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<UserAddress> listData(UserAddress userAddress, HttpServletRequest request, HttpServletResponse response) {
		userAddress.setPage(new Page<>(request, response));
		Page<UserAddress> page = userAddressService.findPage(userAddress);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("pe:userAddress:view")
	@RequestMapping(value = "form")
	public String form(UserAddress userAddress, Model model) {
		model.addAttribute("userAddress", userAddress);
		return "lamb/pe/userAddressForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("pe:userAddress:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated UserAddress userAddress) {
		userAddressService.save(userAddress);
		return renderResult(Global.TRUE, text("保存用户地址成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("pe:userAddress:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(UserAddress userAddress) {
		userAddress.setStatus(UserAddress.STATUS_DISABLE);
		userAddressService.updateStatus(userAddress);
		return renderResult(Global.TRUE, text("停用用户地址成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("pe:userAddress:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(UserAddress userAddress) {
		userAddress.setStatus(UserAddress.STATUS_NORMAL);
		userAddressService.updateStatus(userAddress);
		return renderResult(Global.TRUE, text("启用用户地址成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("pe:userAddress:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(UserAddress userAddress) {
		userAddressService.delete(userAddress);
		return renderResult(Global.TRUE, text("删除用户地址成功！"));
	}
	
}