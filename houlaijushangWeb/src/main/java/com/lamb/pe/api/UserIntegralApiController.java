
package com.lamb.pe.api;

import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.lamb.pe.dao.UserIntegralDao;
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.entity.UserPrestore;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户积分Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserIntegral")
public class UserIntegralApiController extends BaseApiController {

	@Autowired
	private UserIntegralDao userIntegralDao;

	/**
	 * 查看用户积分
	 */
	@RequestMapping(value = "get")
	@ResponseBody
	public ResultApp get() {
		UserIntegral userIntegral = new UserIntegral();
		userIntegral.setUserId(UserKit.getUserId());
		UserIntegral byEntity = userIntegralDao.getByEntity(userIntegral);
		return ResultApp.success(byEntity);
	}


}