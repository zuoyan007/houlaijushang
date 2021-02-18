
package com.lamb.gp.api;

import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.lamb.gp.entity.GoodsGroupon;
import com.lamb.gp.service.GoodsGrouponService;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品详情Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/gp/GoodsGroupon")
public class GoodsGrouponApiController extends BaseApiController {

	@Autowired
	private GoodsGrouponService goodsGrouponService;
	@Autowired
	private FileUploadService fileUploadService;




	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list() {
		GoodsGroupon goodsGroupon = new GoodsGroupon();
		List<GoodsGroupon> GoodsGrouponList = goodsGrouponService.findList(goodsGroupon);
		for (GoodsGroupon list : GoodsGrouponList) {
			FileUpload selImage = new FileUpload();
			selImage.setBizKey(list.getGoodsId());
			list.setImageList(fileUploadService.findList(selImage));
			double grouponNowNumber = (double)list.getGrouponNowNumber();
			double grouponTarget =  (double)list.getGrouponTarget();
			list.setPercent(grouponNowNumber/grouponTarget);
		}
		return ResultApp.success(GoodsGrouponList);
	}

	/**
	 * 详情
	 */
	@RequestMapping(value = "view")
	@ResponseBody
	public ResultApp view(String grouponId) {
		if (StringUtils.isAnyBlank(grouponId)) {
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		GoodsGroupon goodsGroupon = goodsGrouponService.get(grouponId);
		FileUpload selImage = new FileUpload();
		selImage.setBizKey(goodsGroupon.getGoodsId());
		goodsGroupon.setImageList(fileUploadService.findList(selImage));
		double grouponNowNumber = (double)goodsGroupon.getGrouponNowNumber();
		double grouponTarget =  (double)goodsGroupon.getGrouponTarget();
		goodsGroupon.setPercent(grouponNowNumber/grouponTarget);

		String substring = goodsGroupon.getGoodsDetail().getGoodPrice().substring(1);
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
		//将现有价格转换为Double类型保留2为小数
		double goodPrice = Double.parseDouble(myformat.format(Double.parseDouble(substring)));
		//将原价转换为Double类型保留2为小数
		if (!"".equals(goodsGroupon.getGoodsDetail().getOriginalCost())&&goodsGroupon.getGoodsDetail().getOriginalCost()!=null){
			String substring1 = goodsGroupon.getGoodsDetail().getOriginalCost().substring(1);
			double originalCost = Double.parseDouble(myformat.format(Double.parseDouble(substring1)));
			goodsGroupon.getGoodsDetail().setDiscount(Double.parseDouble(myformat.format(goodPrice/originalCost)));
		}
		return ResultApp.success(goodsGroupon);
	}




}