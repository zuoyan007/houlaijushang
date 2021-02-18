
package com.lamb.pe.api;

import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.lamb.pe.dao.UserPrestoreDao;
import com.lamb.pe.entity.UserCollects;
import com.lamb.pe.entity.UserPrestore;
import com.lamb.pe.service.UserPrestoreService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户预存Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserPrestore")
public class UserPrestoreApiController extends BaseApiController {

	@Autowired
	private UserPrestoreDao userPrestoreDao;
	@Autowired
	private UserPrestoreService userPrestoreService;

	/**
	 * 查看用户预存
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public ResultApp get() {
		UserPrestore userPrestore = new UserPrestore();
		userPrestore.setUserId(UserKit.getUserId());
		UserPrestore byEntity = userPrestoreDao.getByEntity(userPrestore);
		return ResultApp.success(byEntity);
	}


	/**
	 * 支付
	 * @param label   支付方式(微信支付：1， 支付宝：2，预存款：3)     目前只支持预存款
	 * @param encryptPrice   (加密支付金额)
	 * @param parentOrderId  (订单id)
	 * @return
	 */
	@RequestMapping(value = "pay")
	@ResponseBody
	public ResultApp pay(String payType,String label,String encryptPrice, String parentOrderId ,String grouponId) {
		if (!"3".equals(payType)){
			return ResultApp.success("抱歉暂时不支持该支付方式");
		}
		String Price = DesUtils.decode(encryptPrice, Global.getConfig("shiro.loginSubmit.secretKey"));
		java.text.DecimalFormat myformat=new java.text.DecimalFormat("#0.00");
		//将现有价格转换为Double类型保留2为小数
		double goodsPrice = Double.parseDouble(myformat.format(Double.parseDouble(Price)));
		return ResultApp.success(userPrestoreService.prestore(label, goodsPrice, parentOrderId, grouponId));
	}


}