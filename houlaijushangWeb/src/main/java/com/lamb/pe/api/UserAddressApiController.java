
package com.lamb.pe.api;

import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.lamb.pe.entity.UserAddress;
import com.lamb.pe.service.UserAddressService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户地址Controller
 * @author dizj
 * @version 2021-02-03
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserAddress")
public class UserAddressApiController extends BaseApiController {

	@Autowired
	private UserAddressService userAddressService;

	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list() {
		UserAddress userAddress = new UserAddress();
		userAddress.setUserId(UserKit.getUserId());
		List<UserAddress> list = userAddressService.findList(userAddress);
		return ResultApp.success(list);
	}

	/**
	 * 获取详细地址信息
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public ResultApp get(String addressId) {
		if(StringUtils.isAnyBlank(addressId)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		return ResultApp.success(userAddressService.get(new UserAddress().setAddressId(addressId)));
	}


	/**
	 * 修改地址
	 * @param userAddress
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public ResultApp save(UserAddress userAddress,int isInsert) {
		if(userAddress.getUserId()==null){
			userAddress.setUserId(UserKit.getUserId());
		}
		 userAddressService.save(userAddress,isInsert);
		return ResultApp.success();
	}


	/**
	 * 删除地址
	 * @param addressId
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public ResultApp delete(String addressId) {
		if(StringUtils.isAnyBlank(addressId)){
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		userAddressService.delete(new UserAddress().setAddressId(addressId));
		return ResultApp.success();
	}
}