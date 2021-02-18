
package com.lamb.cp.api;

import com.lamb.cp.entity.CouponUser;
import com.lamb.cp.service.CouponUserService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 用户优惠卷Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${apiPath}/cp/CouponUser")
public class CouponUserApiController extends BaseApiController {

	@Autowired
	private CouponUserService couponUserService;




	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list() {
		CouponUser couponUser = new CouponUser();
		couponUser.setUserId(UserKit.getUserId());
		List<CouponUser> list = couponUserService.findList(couponUser);
		return ResultApp.success(list);
	}


	/**
	 * 优惠卷数量
	 * @return
	 */
	@RequestMapping(value = "count")
	@ResponseBody
	public ResultApp count() {
		CouponUser couponUser = new CouponUser();
		couponUser.setUserId(UserKit.getUserId());
		long count = couponUserService.findCount(couponUser);
		return ResultApp.success(count);
	}






}