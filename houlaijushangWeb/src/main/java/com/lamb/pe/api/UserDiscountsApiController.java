
package com.lamb.pe.api;

import com.jeesite.common.entity.Page;
import com.lamb.pe.dao.UserCollectsDao;
import com.lamb.pe.entity.UserCollects;
import com.lamb.pe.entity.UserDiscounts;
import com.lamb.pe.service.UserCollectsService;
import com.lamb.pe.service.UserDiscountsService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户促销优惠卷Controller
 * @author dizj
 * @version 2021-02-09
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserDiscounts")
public class UserDiscountsApiController extends BaseApiController {


	@Autowired
	private UserDiscountsService userDiscountsService;




	/**
	 * 查看用户是否有优惠卷
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public boolean get(String userId,String goodsId) {
		UserDiscounts userDiscounts = new UserDiscounts();
		userDiscounts.setUserId(userId);
		userDiscounts.setGoodsId(goodsId);
		UserDiscounts userDiscounts1 = userDiscountsService.get(userDiscounts);
		return userDiscounts1!=null;
	}


	/**
	 * 领取商品促销
	 * @param userDiscounts
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public ResultApp save(UserDiscounts userDiscounts) {
		userDiscounts.setUserId(UserKit.getUserId());
		userDiscountsService.save(userDiscounts);
		return ResultApp.success();
	}






}