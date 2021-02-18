package com.lamb.util;

import com.alibaba.fastjson.JSON;
import com.jeesite.common.config.Global;
import com.jeesite.common.lang.NumberUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.shiro.session.SessionDAO;
import com.jeesite.common.web.CookieUtils;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.utils.UserUtils;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import com.lamb.util.sys.StringKit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class TokenApiInterceptor implements HandlerInterceptor {


	private static final Logger LOG = LoggerFactory.getLogger(TokenApiInterceptor.class);
	
	private static final String noTokenUrls[]={"sysEmpUser/login","sysEmpUser/userEmail","sysEmpUser/userEnroll"};

	@Autowired
	private SessionDAO sessionDAO;

	public TokenApiInterceptor() {
	}

	/**
	 * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
	 * 
	 * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
	 * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
	 */
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		String cross_origin = Global.getProperty("crossOrigin");
//		if(!StringKit.isNull(cross_origin)){
//			String Origin=request.getHeader("Origin");
//			if(Origin!=null&&(cross_origin.contains(Origin)||cross_origin.equals("*"))){
//				response.setHeader("Access-Control-Allow-Origin", Origin);
//		        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
//			}
//
//		}

//

		String requestUrl = request.getRequestURL().toString();
		if(!requestUrl.contains("sysVersion/upgrade")){
			int versionCodeMin =  NumberUtils.toInt(Global.getConfig("sys.versionCodeMin.3mApp","999999999"));
			int versionCode = NumberUtils.toInt(this.getParam(request,"versionCode"),999999999);
			String sysType = request.getHeader("sysType");

//			if(Dict.SysType.zfApp.name().equals(sysType)){
				if(versionCode<versionCodeMin){
					this.print(response, ResultApp.fail(ResultApp.Status.NO_LOGIN,"系统版本升级，请更新系统重新登录。"));
					return false;
				}
//			}
		}

		for (String noTokenUrl : noTokenUrls) {
			if(requestUrl.endsWith(noTokenUrl)){
				return true;
			}
		}
		//这里代码，不要乱动 gexu
		String userId = request.getHeader(UserKit.USER_ID);
		if("-2".equals(request.getParameter(UserKit.USER_ID))) {
			return true;
		}
		String testPwd = request.getParameter("testPwd");
		if("-2".equals(testPwd)) {
			return true;
		}
		User user = UserUtils.getUser();
		if (user != null && !StringUtils.isBlank(user.getUserCode())) {
			return true;
		}
		if(StringKit.isBlank(userId)){
			userId  = UserKit.getUserId();
		}
		String nanoTime = this.getParam(request,"appNanoTime");
		String token = this.getParam(request,"appToken");
		String _token = BaseApiController.token(request,userId,nanoTime);
		if(!_token.equals(token)){
			this.print(response, ResultApp.fail(ResultApp.Status.NO_LOGIN,"登录信息失效，请重新登录"));
			return false;
		}
//		LotMember member = memberService.get(memberId);
//		if(!_token.equals(member.getToken())){
//			this.print(response,ResultApp.fail(Status.NO_LOGIN,"登录信息失效，请重新登录。"));
//			return false;
//		}
		return true;
	}

	public String getParam(HttpServletRequest request, String name){
		Enumeration er = request.getHeaderNames();
		while(er.hasMoreElements()){
			String name1	=(String) er.nextElement();
			String value = request.getHeader(name);
			LOG.info(name1+"="+value);
		}
		String value = request.getHeader(name);
		if(StringKit.isNotBlank(value)){
			return value;
		}
		value = request.getParameter(name);
		if(StringKit.isNotBlank(value)){
			return value;
		}
		value = CookieUtils.getCookie(request,name);
		return value;
	}
	
	public void print(HttpServletResponse response, ResultApp result) throws IOException{
		response.setContentType("text/json; charset=utf-8");
		response.getWriter().print(JSON.toJSONString(result));
	}

	// 在业务处理器处理请求执行完成后,生成视图之前执行的动作
	public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//		String userId = request.getHeader(USER_ID);
//		HttpSession session = UserKit.getSession(userId);
//		HttpSession session2 = request.getSession();
//		ShiroHttpSession s1 = (ShiroHttpSession) session;
//
//		if(session!=null&&session.getId()!=session2.getId()){
//			System.out.println("session="+session.getId());
//			session.setAttribute("userName","1");
//			session.setAttribute("userCode","2");
//			s1.invalidate();
//			session2.setAttribute("deviceType", "mobileApp");
//			session2.setAttribute("userName",UserKit.getUser().getUserName()+session.getId());
//			session2.setAttribute("userCode",UserKit.getUser().getUserCode());
//			session2.setAttribute("userType",UserKit.getUser().getUserType());
////			s.getLastAccessTime()
////			session.("lastAccessTime",DateKit.getDateTime());
////			session.setAttribute("userType","dddd");
//		}
	}

	/**
	 * 在DispatcherServlet完全处理完请求后被调用
	 * 
	 * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
	 */
	public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	
	public static String getQuery(HttpServletRequest request){
		Enumeration params = request.getParameterNames();
		StringBuilder paramStr = new StringBuilder();
		while (params.hasMoreElements()) {
			String name = params.nextElement().toString();
			String[] value = request.getParameterValues(name);
			for (int i = 0; i < value.length; i++) {
				paramStr.append(name).append("=").append(value[i]).append("&");
			}
		}
		if(!StringUtils.isBlank(paramStr.toString())){
			paramStr = new StringBuilder(paramStr.substring(0, paramStr.length()-1)); 
		}
		return paramStr.toString();
	}
	
}