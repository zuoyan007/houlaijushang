package com.lamb.sys.api;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.sys.entity.DictType;
import com.jeesite.modules.sys.service.DictDataService;
import com.lamb.cons.Dict;
import com.lamb.sys.dao.SysDictDataDao;
import com.lamb.sys.entity.SysDictData;
import com.lamb.sys.entity.SysDictDataVO;
import com.lamb.sys.service.SysDictDataService;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import com.lamb.util.sys.StringKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author dizj
 * @date 2020/1/27
 * @describe 
 */
@RestController
@RequestMapping(value = "${apiPath}/sys/sysDict")
public class SysDictApiController extends BaseApiController {

	@Autowired
	private DictDataService dictDataService;
	@Autowired
	private SysDictDataService sysDictDataService;
	@Autowired
	private SysDictDataDao dictDataDao;

	/**
	 * 1、需要更新的type（初始化时为全部，之后为增量）
	 */
	@RequestMapping(value = "dictType")
	@ResponseBody
	public ResultApp dictType(Date updateDate, HttpServletRequest request, HttpServletResponse response) {
		DictType entity = new DictType();
		entity.setPage(new Page<>(request, response));
		entity.setUpdateDate(updateDate);
		entity.setPageSize(499);
		Page<DictType> page = sysDictDataService.findPageSyncType(entity);
		return ResultApp.success(page);
	}

	/**
	 * 查询二级分类列表列表数据
	 * sys_app_use_dict
	 */
	@RequestMapping(value = "dictSecondList")
	@ResponseBody
	public ResultApp dictSecondList( String dictType, HttpServletRequest request, HttpServletResponse response) {
		SysDictData dictData = new SysDictData();
		dictData.setPage(new Page<>(request, response));
		dictData.setDictType(dictType);
		dictData.setParentCode("0");
		dictData.setStatus("0");
		dictData.setPageSize(499);
		dictData.setIsAppUse(Dict.YesOrNo.yes.getCode());
		Page<SysDictDataVO> page = sysDictDataService.findPageSyncList(dictData);
		for (SysDictDataVO sysDictDataVO : page.getList()) {
			dictData.setParentCode(sysDictDataVO.getDictCode());
			sysDictDataVO.setChildList(sysDictDataService.findPageSyncList(dictData).getList());
		}
		return ResultApp.success(page);
	}





	/**
	 * 查询列表数据
	 * sys_app_use_dict
	 */
	@RequestMapping(value = "dictList")
	@ResponseBody
	public ResultApp dictList(Date updateDate, String dictType, String parentCode, HttpServletRequest request, HttpServletResponse response) {
		SysDictData dictData = new SysDictData();
		dictData.setPage(new Page<>(request, response));
		dictData.setUpdateDate_gte(updateDate);
		dictData.setDictType(dictType);
		dictData.setParentCode(parentCode);
		dictData.setStatus("0");
		dictData.setPageSize(499);
		dictData.setIsAppUse(Dict.YesOrNo.yes.getCode());
		Page<SysDictDataVO> page = sysDictDataService.findPageSyncList(dictData);
		return ResultApp.success(page);
	}

	/**
	 查询树状数据 是否有变化
	 通过这个接口先获取到那些树数据有变化。然后在调用treeList获取最新树数据。
	 */
	@RequestMapping(value = "syncTreeType")
	@ResponseBody
	public ResultApp syncTreeType(String[] dictTypeArray, Date updateDate) {
		if(dictTypeArray.length==0){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		Map<String,Object> selMap = new HashMap<>();
		selMap.put("dictTypeArray",dictTypeArray);
		selMap.put("updateDate",updateDate);
		List<DictType> page = dictDataDao.syncTreeType(selMap);
		return ResultApp.success(page);
	}

	/**
	  查询树状数据
	 */
	@RequestMapping(value = "treeList")
	@ResponseBody
	public ResultApp treeList(String dictType, String parentCode) {
		if(StringKit.isNull(dictType)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		SysDictData dictData = new SysDictData();
		dictData.setDictType(dictType);
		dictData.setParentCode(parentCode);
		dictData.setStatus("0");
		List<SysDictDataVO>  page  = sysDictDataService.treeList(dictType,dictData);
		return ResultApp.success(page);
	}

}
