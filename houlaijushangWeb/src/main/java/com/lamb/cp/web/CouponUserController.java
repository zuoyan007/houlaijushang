/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.lamb.cp.entity.CouponType;
import com.lamb.cp.entity.CouponUser;
import com.lamb.cp.service.CouponTypeService;
import com.lamb.cp.service.CouponUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 优惠卷用户关联表Controller
 * @author dizj
 * @version 2021-02-06
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/couponUser")
public class CouponUserController extends BaseController {

	@Autowired
	private CouponUserService couponUserService;
	@Autowired
	private CouponTypeService couponTypeService;

	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CouponUser get(String couponUserId, boolean isNewRecord) {
		return couponUserService.get(couponUserId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cp:couponUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponUser couponUser, Model model) {
		model.addAttribute("couponUser", couponUser);
		return "lamb/cp/couponUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cp:couponUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CouponUser> listData(CouponUser couponUser, HttpServletRequest request, HttpServletResponse response) {
		couponUser.setPage(new Page<>(request, response));
		Page<CouponUser> page = couponUserService.findPage(couponUser);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cp:couponUser:view")
	@RequestMapping(value = "form")
	public String form(CouponUser couponUser, Model model) {
		model.addAttribute("couponUser", couponUser);
		return "lamb/cp/couponUserForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("cp:couponUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CouponUser couponUser) {
		couponUserService.save(couponUser);
		return renderResult(Global.TRUE, text("保存优惠卷用户关联表成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("cp:couponUser:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(CouponUser couponUser) {
		couponUser.setStatus(CouponUser.STATUS_DISABLE);
		couponUserService.updateStatus(couponUser);
		return renderResult(Global.TRUE, text("停用优惠卷用户关联表成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("cp:couponUser:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(CouponUser couponUser) {
		couponUser.setStatus(CouponUser.STATUS_NORMAL);
		couponUserService.updateStatus(couponUser);
		return renderResult(Global.TRUE, text("启用优惠卷用户关联表成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("cp:couponUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CouponUser couponUser) {
		couponUserService.delete(couponUser);
		return renderResult(Global.TRUE, text("删除优惠卷用户关联表成功！"));
	}
	
}