
package com.lamb.risk.api;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.service.DictDataService;
import com.lamb.cons.Dict;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 风险类型列表Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/sys/riskPointType")
public class RiskPointTypeApiController extends BaseApiController {

	@Autowired
	private DictDataService dictDataService;

	/**
	 * 查询风险类型列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list(String parentCode) {
		if(parentCode.isEmpty()){
			parentCode="0";
		}
		DictData dictData = new DictData();
		dictData.setDictType(Dict.dictionaries.risk.getName());
		dictData.setParentCode(parentCode);
		List<DictData> list = dictDataService.findList(dictData);
		return ResultApp.success(list);
	}
	
}