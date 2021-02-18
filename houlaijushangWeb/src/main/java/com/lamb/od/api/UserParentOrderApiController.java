
package com.lamb.od.api;

import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.gd.service.GoodsDetailService;
import com.lamb.od.dao.UserChildOrderDao;
import com.lamb.od.entity.UserChildOrder;
import com.lamb.od.entity.UserParentOrder;
import com.lamb.od.service.UserParentOrderService;
import com.lamb.pe.entity.UserCollects;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 支付订单Controller
 * @author dizhangjie
 * @version 2020-12-02
 */
@Controller
@RequestMapping(value = "${apiPath}/od/UserParentOrder")
public class UserParentOrderApiController extends BaseApiController {


	@Autowired
	private UserParentOrderService userParentOrderService;
    @Autowired
    private UserChildOrderDao userChildOrderDao;
    @Autowired
    private GoodsDetailService goodsDetailService;


    /**
     * 保存生成订单
     */
    @RequestMapping(value = "get")
    @ResponseBody
    public ResultApp get(String parentOrderId) {
        if (StringUtils.isEmpty(parentOrderId)){
            return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
        }
        UserParentOrder userParentOrder = userParentOrderService.get(parentOrderId);
		String encode = DesUtils.encode(userParentOrder.getTotalPaid(), Global.getConfig("shiro.loginSubmit.secretKey"));
		userParentOrder.setEncryptPrice(encode);
        return ResultApp.success(userParentOrder);
    }



	/**
	 * 保存生成订单
	 */
	@RequestMapping(value = "CreateToSave")
	@ResponseBody
	public ResultApp CreateToSave(String orderList, String couponId , UserParentOrder userParentOrder) {
		if (StringUtils.isEmpty(orderList)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		String encryptPrice = userParentOrderService.CreateToSave(orderList, couponId, userParentOrder);
		return ResultApp.success(encryptPrice);
	}



	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list( String goodsState ,HttpServletRequest request, HttpServletResponse response) {
		UserParentOrder userParentOrder = new UserParentOrder();
		userParentOrder.setPage(new Page<>(request, response));
		userParentOrder.setUserId(UserKit.getUserId());
		userParentOrder.setGoodsState(goodsState);
		Page<UserParentOrder> page = userParentOrderService.findPage(userParentOrder);
        for (UserParentOrder parentOrder : page.getList()) {
            UserChildOrder userChildOrder = new UserChildOrder();
            List<UserChildOrder> list = userChildOrderDao.findList(userChildOrder.setParentOrderId(parentOrder));
            for (UserChildOrder childOrder : list) {
				int index = childOrder.getGoodNumber().indexOf(".");
				String intNumber = childOrder.getGoodNumber().substring(0,index);
				childOrder.setShowNumber(Integer.parseInt(intNumber));
                GoodsDetail goodsDetail = goodsDetailService.get(childOrder.getGoodsId());
                childOrder.setGoodsDetail(goodsDetail);
            }
            parentOrder.setUserChildOrderList(list);
        }
		return ResultApp.success(page);
	}



	/**
	 * 确认收货
	 */
	@RequestMapping(value = "take")
	@ResponseBody
	public ResultApp take(String parentOrderId) {
		if (StringUtils.isEmpty(parentOrderId)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		userParentOrderService.take(parentOrderId);

		return ResultApp.success("收货成功");
	}


	/**
	 * 取消订单
	 */
	@RequestMapping(value = "cancel")
	@ResponseBody
	public ResultApp cancel(String parentOrderId) {
		if (StringUtils.isEmpty(parentOrderId)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		userParentOrderService.cancel(parentOrderId);

		return ResultApp.success("收货成功");
	}


	/**
	 * 申请退款
	 */
	@RequestMapping(value = "refund")
	@ResponseBody
	public ResultApp refund(String parentOrderId) {
		if (StringUtils.isEmpty(parentOrderId)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		userParentOrderService.refund(parentOrderId);
		return ResultApp.success("退款成功");
	}

}