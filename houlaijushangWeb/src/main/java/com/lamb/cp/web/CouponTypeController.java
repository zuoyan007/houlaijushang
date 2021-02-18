/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.cp.web;

import com.jeesite.common.codec.EncodeUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.lamb.cp.entity.CouponType;
import com.lamb.cp.service.CouponTypeService;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 优惠卷Controller
 * @author dizj
 * @version 2021-02-04
 */
@Controller
@RequestMapping(value = "${adminPath}/cp/couponType")
public class CouponTypeController extends BaseController {

	@Autowired
	private CouponTypeService couponTypeService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public CouponType get(String couponId, boolean isNewRecord) {
		return couponTypeService.get(couponId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("cp:couponType:view")
	@RequestMapping(value = {"list", ""})
	public String list(CouponType couponType, Model model) {
		model.addAttribute("couponType", couponType);
		return "lamb/cp/couponTypeList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("cp:couponType:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<CouponType> listData(CouponType couponType, HttpServletRequest request, HttpServletResponse response) {
		couponType.setPage(new Page<>(request, response));
		Page<CouponType> page = couponTypeService.findPage(couponType);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("cp:couponType:view")
	@RequestMapping(value = "form")
	public String form(CouponType couponType, Model model) {
		model.addAttribute("couponType", couponType);
		return "lamb/cp/couponTypeForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("cp:couponType:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated CouponType couponType) {
		couponTypeService.save(couponType);
		return renderResult(Global.TRUE, text("保存优惠卷成功！"));
	}
	
	/**
	 * 停用数据
	 */
	@RequiresPermissions("cp:couponType:edit")
	@RequestMapping(value = "disable")
	@ResponseBody
	public String disable(CouponType couponType) {
		couponType.setStatus(CouponType.STATUS_DISABLE);
		couponTypeService.updateStatus(couponType);
		return renderResult(Global.TRUE, text("停用优惠卷成功"));
	}
	
	/**
	 * 启用数据
	 */
	@RequiresPermissions("cp:couponType:edit")
	@RequestMapping(value = "enable")
	@ResponseBody
	public String enable(CouponType couponType) {
		couponType.setStatus(CouponType.STATUS_NORMAL);
		couponTypeService.updateStatus(couponType);
		return renderResult(Global.TRUE, text("启用优惠卷成功"));
	}
	
	/**
	 * 删除数据
	 */
	@RequiresPermissions("cp:couponType:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(CouponType couponType) {
		couponTypeService.delete(couponType);
		return renderResult(Global.TRUE, text("删除优惠卷成功！"));
	}


	@RequestMapping(value = "couponTypeSelect")
	public String smContractSelect(CouponType couponType, String selectData, String selectStr, Model model) {
		String selectStrJson = EncodeUtils.decodeUrl(selectData);
		if(StringKit.isNotBlank(selectStr)){
			selectStrJson = EncodeUtils.decodeUrl(selectStr);
		}
		//如果自定义数据不为空则为修改时传入
		model.addAttribute("selectData", selectStrJson);
		model.addAttribute("CouponType",couponType);
		return "lamb/cp/couponTypeSelect";
	}
}