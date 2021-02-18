
package com.lamb.pe.api;

import com.jeesite.common.entity.Page;
import com.jeesite.modules.file.entity.FileUpload;
import com.jeesite.modules.file.service.FileUploadService;
import com.lamb.gd.entity.GoodsDetail;
import com.lamb.pe.dao.UserCollectsDao;
import com.lamb.pe.entity.UserCollects;
import com.lamb.pe.service.UserCollectsService;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户收藏Controller
 * @author dizj
 * @version 2021-02-01
 */
@Controller
@RequestMapping(value = "${apiPath}/pe/UserCollects")
public class UserCollectsApiController extends BaseApiController {

	@Autowired
	private UserCollectsDao userCollectsDao;
	@Autowired
	private UserCollectsService userCollectsService;
	@Autowired
	private FileUploadService fileUploadService;





	/**
	 * 列表
	 */
	@RequestMapping(value = "list")
	@ResponseBody
	public ResultApp list() {
		UserCollects userCollects = new UserCollects();
		userCollects.setUserId(UserKit.getUserId());
		List<UserCollects> list = userCollectsService.findList(userCollects);
		for (UserCollects list1 : list) {
			FileUpload selImage = new FileUpload();
			selImage.setBizKey(list1.getGoodsId());
			list1.setImageList(fileUploadService.findList(selImage));
		}
		return ResultApp.success(list);
	}


	/**
	 * 修改收藏
	 * @param userCollects
	 * @return
	 */
	@RequestMapping(value = "save")
	@ResponseBody
	public ResultApp save(UserCollects userCollects) {
		userCollects.setUserId(UserKit.getUserId());
		UserCollects UC = userCollectsDao.getByEntity(userCollects);
		if(UC!=null){
			userCollectsDao.phyDeleteByEntity(UC);
		}
		else {
			userCollectsService.insert(userCollects);
		}
		return ResultApp.success();
	}






}