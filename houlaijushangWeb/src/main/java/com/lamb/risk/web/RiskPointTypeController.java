
package com.lamb.risk.web;

import com.jeesite.common.config.Global;
import com.jeesite.common.idgen.IdGen;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.DictData;
import com.jeesite.modules.sys.service.DictDataService;
import com.lamb.cons.Dict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * risk_point_type  字典数据表Controller
 * @author 狄张杰
 * @version 2020-11-30
 */

@Controller
@RequestMapping(value = "${adminPath}/sys/riskPointType")
public class RiskPointTypeController extends BaseController {

	@Autowired
	private DictDataService dictDataService;




	/**
	 * 获取数据
	 */
	@ModelAttribute
	public DictData get(String dictCode, boolean isNewRecord) {
		return dictDataService.get(dictCode, isNewRecord);
	}



	/**
	 * 查询列表
	 */
	@RequestMapping(value = {"list", ""})
	public String list(DictData dictData, Model model) {
		dictData.setDictType("risk_point_type");
		model.addAttribute("dictData", dictData);
		return "lamb/risk/riskPointTypeList";
	}


	/**
	 * 查询列表数据
	 */

	@RequestMapping(value = "listData")
	@ResponseBody
	public List<DictData> listData(DictData dictData) {
		dictData.setDictType(Dict.dictionaries.risk.getName());
		List<DictData> list = dictDataService.findList(dictData);
		return list;
	}


	/**
	 * 查看编辑表单
	 */
	@RequestMapping(value = "form")
	public String form(DictData dictData, Model model) {
		dictData.setDictType(Dict.dictionaries.risk.getName());
		// 创建并初始化下一个节点信息
		dictData = createNextNode(dictData);
		model.addAttribute("dictData", dictData);
		return "lamb/risk/riskPointTypeForm";
	}


	/**
	 * 创建并初始化下一个节点信息，如：排序号、默认值
	 */
	@RequestMapping(value = "createNextNode")
	@ResponseBody
	public DictData createNextNode(DictData dictData) {
		dictData.setDictType("risk_point_type");
		if (StringUtils.isNotBlank(dictData.getParentCode())){
			dictData.setParent(dictDataService.get(dictData.getParentCode()));
		}
		if (dictData.getIsNewRecord()) {
			DictData where = new DictData();
			where.setDictType("risk_point_type");
			where.setParentCode(dictData.getParentCode());
			DictData last = dictDataService.getLastByParentCode(where);
			// 获取到下级最后一个节点
			if (last != null){
				dictData.setTreeSort(last.getTreeSort() + 30);
				dictData.setDictValue(IdGen.nextCode(last.getDictValue()));
			}else if (dictData.getParent() != null){
				dictData.setDictValue(dictData.getParent().getDictValue() + "001");
			}
		}
		// 以下设置表单默认数据
		if (dictData.getTreeSort() == null){
			dictData.setTreeSort(DictData.DEFAULT_TREE_SORT);
		}
		dictData.setIsSys("1");
		return dictData;
	}

	/**
	 * 保存数据
	 */
	@PostMapping(value = "save")
	@ResponseBody
	public String save( DictData dictData) {
		dictData.setDictType(Dict.dictionaries.risk.getName());
		dictData.setIsSys("1");
		dictDataService.save(dictData);
		return renderResult(Global.TRUE, text("保存风险类型成功！"));
	}

	/**
	 * 删除数据
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(DictData dictData) {
		dictData.setDictType("risk_point_type");
		dictDataService.delete(dictData);
		return renderResult(Global.TRUE, text("删除风险类型成功！"));
	}

}
