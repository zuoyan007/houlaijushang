
package com.lamb.gd.api;

import com.alibaba.druid.sql.ast.statement.SQLDeleteStatement;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.gd.service.GoodsDetailService;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 商品详情Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/gd/GoodsDetail")
public class GoodsDetailApiController extends BaseApiController {

	@Autowired
	private GoodsDetailService goodsDetailService;




	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list(String goodsId,String goodsProperty, HttpServletRequest request, HttpServletResponse response) {
		GoodsDetail goodsDetail = new GoodsDetail();
		goodsDetail.setPage(new Page<>(request, response));
		goodsDetail.setGoodsId(goodsId);
		if (StringUtils.isAnyBlank(goodsProperty)){
			goodsDetail.getSqlMap().getWhere().and("goods_property", QueryType.NE, "140");
		}else {
			goodsDetail.setGoodsProperty(goodsProperty);
		}
		Page<GoodsDetail> page = goodsDetailService.findPage(goodsDetail);

		return ResultApp.success(page);
	}

	/**
	 * 详情
	 */
	@RequestMapping(value = "view")
	@ResponseBody
	public ResultApp view(String goodsId) {
		if (StringUtils.isAnyBlank(goodsId)) {
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		return ResultApp.success(goodsDetailService.get(goodsId));
	}

	/**
	 * 猜你喜欢
	 */
	@RequestMapping(value = "findYouLike")
	@ResponseBody
	public ResultApp findYouLike() {
		return ResultApp.success(goodsDetailService.findYouLike());
	}


}