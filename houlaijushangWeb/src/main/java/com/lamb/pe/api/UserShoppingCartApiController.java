
package com.lamb.pe.api;

import com.jeesite.common.entity.Page;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.gd.service.GoodsDetailService;
import com.lamb.pe.dao.UserShoppingCartDao;
import com.lamb.pe.entity.UserShoppingCart;
import com.lamb.pe.service.UserShoppingCartService;
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
 * 用户购物车Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserShoppingCart")
public class UserShoppingCartApiController extends BaseApiController {

	@Autowired
	private UserShoppingCartService userShoppingCartService;
	@Autowired
	private UserShoppingCartDao userShoppingCartDao;
	@Autowired
	private GoodsDetailService goodsDetailService;




	/**
	 * 获取购物车列表列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list( HttpServletRequest request, HttpServletResponse response) {
		UserShoppingCart userShoppingCart = new UserShoppingCart();
		userShoppingCart.setPage(new Page<>(request, response));
		userShoppingCart.setUserId(UserKit.getUserId());
		Page<UserShoppingCart> page = userShoppingCartService.findPage(userShoppingCart);
		for (UserShoppingCart shoppingCart : page.getList()) {
			GoodsDetail goodsDetail = goodsDetailService.get(shoppingCart.getGoodsId());
			shoppingCart.setGoodsDetail(goodsDetail);
			shoppingCart.setImgUrl(goodsDetail.getImageList().get(0).getFileUrl());
			shoppingCart.setChecked(true);
		}
		return ResultApp.success(page);
	}

	/**
	 * 添加购物车
	 * @param userShoppingCart
	 * @return
	 */
	@RequestMapping(value = "add")
	@ResponseBody
	public ResultApp add(UserShoppingCart userShoppingCart) {
		userShoppingCart.setUserId(UserKit.getUserId());
		if (userShoppingCartDao.getByEntity(userShoppingCart)==null){
			userShoppingCartService.save(userShoppingCart);
		}
		return ResultApp.success("添加成功");
	}


	/**
	 * 删除购物车
	 * @param userShoppingCart
	 * @return
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public ResultApp delete(UserShoppingCart userShoppingCart) {
		userShoppingCartDao.phyDeleteByEntity(userShoppingCart);
		return ResultApp.success();
	}


}