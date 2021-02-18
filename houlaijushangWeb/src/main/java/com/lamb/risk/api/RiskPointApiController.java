
package com.lamb.risk.api;

import com.jeesite.common.lang.StringUtils;
import com.lamb.risk.entity.RiskPoint;
import com.lamb.risk.service.RiskPointService;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * 可视化一张图Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/cm/cmCustContact")
public class RiskPointApiController extends BaseApiController {

	@Autowired
	private RiskPointService RiskPointService;


	/**
	 * 详情
	 */
	@RequestMapping(value = "view")
	@ResponseBody
	public ResultApp view(String riskPointId) {
		if (StringUtils.isAnyBlank(riskPointId)) {
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		return ResultApp.success(RiskPointService.view(riskPointId));
	}


	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list(String riskPointType) {
		RiskPoint riskPoint = new RiskPoint();
		riskPoint.setRiskPointType(riskPointType);
		List<RiskPoint> list = RiskPointService.findList(riskPoint);

		return ResultApp.success(list);
	}

}