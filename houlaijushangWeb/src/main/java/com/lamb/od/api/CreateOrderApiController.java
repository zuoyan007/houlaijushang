
package com.lamb.od.api;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.lang.StringUtils;
import com.lamb.od.service.CreateOrderService;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 支付生成校验Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/od/CreateOrder")
public class CreateOrderApiController extends BaseApiController {

	@Autowired
	private CreateOrderService createOrderService;






	/**
	 * 购物车订单生成
	 */
	@RequestMapping(value = "Create")
	@ResponseBody
	public ResultApp Create(String orderList) {
		if (StringUtils.isEmpty(orderList)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		Map<Object, Object> objectObjectMap = createOrderService.CreateOrder(orderList);
		return ResultApp.success(objectObjectMap);
	}

	@RequestMapping(value = "Create1")
	@ResponseBody
	public ResultApp Create1(String orderList) {
		List<Map<String,String>> list = JSON.parseObject(orderList, java.util.List.class) ;
		Map obj = (Map)list.get(0);
		System.out.println(obj.get("goodsId"));
		return null;
	}






	/**
	 * 付款校验
	 */
	@RequestMapping(value = "checkPayment")
	@ResponseBody
	public ResultApp checkPayment(String couponId,HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		Object attribute = session.getAttribute("goodsPricePrice");
		return ResultApp.success();
	}





}