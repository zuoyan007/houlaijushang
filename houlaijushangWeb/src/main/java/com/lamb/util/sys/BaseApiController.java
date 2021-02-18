package com.lamb.util.sys;

import com.jeesite.common.codec.Md5Utils;
import com.jeesite.common.config.Global;
import com.jeesite.common.web.BaseController;
import com.lamb.util.UserKit;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

public class BaseApiController extends BaseController {

	@ExceptionHandler(Exception.class)
	@ResponseBody
    public ResultApp handleException(Exception ex, HttpServletRequest request) {

		String msg = "程序累了想休息一下尼";
		if ("1".equals(Global.getConfig("devMode"))) {
			msg = "仅开发模式下返回："+ex.getMessage();
		}
		if(ex instanceof BizException){
			msg = ex.getMessage();
			msg = msg.replace("msg:","");
			logger.error("api[biz]异常:{}",msg);
		}else{
			logger.error("api异常",ex);
		}
    	return ResultApp.fail(ResultApp.Status.SYSTEM_EXCEPTION,msg);
    }
	
	@ExceptionHandler(BindException.class)
	@ResponseBody
    public ResultApp bindExceptionHandler(BindException be){
		BindingResult bindingResult = be.getBindingResult();
		StringBuilder msg = new StringBuilder();
		if ("1".equals(Global.getConfig("devMode"))) {
			msg.append("仅开发模式下返回：");
			for (FieldError error: bindingResult.getFieldErrors()) {
				msg.append(error.getField()).append(error.getDefaultMessage());
			}
		}
        return ResultApp.fail(ResultApp.Status.PARAMS_INVALID,msg.toString());
    }
	
	public static String token(HttpServletRequest request, String objectId, String nanoTime){
		return Md5Utils.md5(objectId+ Global.getConfig("shiro.sso.secretKey")+nanoTime);
		
	}
	
//	public <T> PageApp<T> setPage(HttpServletRequest request){
//		int pageNo = StringKit.Obj2Int(request.getParameter("pageNo"),1);
//		int pageSize = StringKit.Obj2Int(request.getParameter("pageSize"),20);
//		return new PageApp<T>(pageNo, pageSize);
//	}

	/**
	 * 临时把以前api的代码拷贝过来，以后要逐步的改进代码，优化他
	 * @param request
	 * @return
	 * @author gexu
	 * @date 2019-10-17
	 */
    public String getUserId(HttpServletRequest request) {
    	return UserKit.getUserId();
    }

}
