package com.lamb.risk.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.lamb.risk.entity.RiskPoint;
import com.lamb.risk.service.RiskPointService;
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
 * risk_pointController
 * @author 狄张杰
 * @version 2020-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/risk/riskPoint")
public class RiskPointController extends BaseController {

	@Autowired
	private RiskPointService riskPointService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public RiskPoint get(String riskPointId, boolean isNewRecord) {
		return riskPointService.get(riskPointId, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("risk:riskPoint:view")
	@RequestMapping(value = {"list", ""})
	public String list(RiskPoint riskPoint, Model model) {
		model.addAttribute("riskPoint", riskPoint);
		return "lamb/risk/riskPointList";
	}

	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("risk:riskPoint:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<RiskPoint> listData(RiskPoint riskPoint, HttpServletRequest request, HttpServletResponse response) {
		riskPoint.setPage(new Page<>(request, response));
		Page<RiskPoint> page = riskPointService.findPage(riskPoint);
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("risk:riskPoint:view")
	@RequestMapping(value = "form")
	public String form(RiskPoint riskPoint, Model model) {
		model.addAttribute("riskPoint", riskPoint);
		return "lamb/risk/riskPointForm";
	}

	/**
	 * 保存数据
	 */
	@RequiresPermissions("risk:riskPoint:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated RiskPoint riskPoint) {
		riskPointService.save(riskPoint);
		return renderResult(Global.TRUE, text("保存风险数据成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequiresPermissions("risk:riskPoint:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(RiskPoint riskPoint) {
		riskPointService.delete(riskPoint);
		return renderResult(Global.TRUE, text("删除风险数据成功！"));
	}
	
}