package com.lamb.util;

import com.jeesite.common.config.Global;
import com.jeesite.common.io.FileUtils;
import com.jeesite.common.lang.DateUtils;
import com.jeesite.common.shiro.authc.FormToken;
import com.jeesite.common.web.CookieUtils;
import com.lamb.cons.Dict;
import com.lamb.util.sys.DateKit;
import com.lamb.util.sys.StringKit;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * 角色相关工具类
 *
 * @author gexu
 * @date 2020/2/12 15:27
 */
public class LoginKit {

    /**
     * 调用jeesite框架的登录，以便能记录用户操作日志
     */
    public static void login(HttpServletRequest request){
        String loginCode = UserKit.getUser().getLoginCode();

//        FormToken upToken = new FormToken();
//        upToken.setUsername(loginCode);
//        upToken.setSsoToken(UserUtils.getSsoToken(loginCode));
//        //upToken.setParams(ServletUtils.getExtParams(request));
//        UserUtils.getSubject().login(upToken);

//        String token = UserUtils.getSsoToken(loginCode);
//        FormToken upToken = new FormToken();
//        upToken.setUsername(loginCode);	// 登录用户名
//        upToken.setSsoToken(token); 	// 单点登录令牌
//        upToken.setParams(ResultApp.createMap("loginType", Dict.SysType.zfApp.name()));
//        upToken.setParams(ServletUtils.getExtParams(request)); // 登录附加参数
//        UserUtils.getSubject().login(upToken);



//        SysUserGovVirtual user = UserKit.getUser();
//        FormToken upToken = new FormToken();
//        upToken.setParams(ResultApp.createMap("loginType", Dict.SysType.zfApp.name()));
//        upToken.setUsername(user.getLoginCode());	// 登录用户名
////		@param token 	单点登录令牌，令牌组成：sso密钥+用户名+日期，进行md5加密，举例：
//// * 		// 注意如果 shiro.sso.encryptKey 为 true，则 secretKey 会自动加密。
////  		String secretKey = Global.getConfig("shiro.sso.secretKey");
////  		String token = Md5Utils.md5(secretKey + username + DateUtils.getDate("yyyyMMdd"));
//        upToken.setSsoToken(UserUtils.getSsoToken(user.getLoginCode())); 	// 单点登录令牌
//        UserUtils.getSubject().login(upToken);
////		new AuthorizingRealm().onLoginSuccess(UserUtils.getLoginInfo(),request);
//        HttpSession session = UserKit.getSession(user.getId());
//        if(session!=null){
//            session.setAttribute("deviceType", "mobileApp");
//            session.setAttribute("userName",user.getUserName()+session.getId());
//            session.setAttribute("userCode",user.getUserCode());
//            session.setAttribute("userType",user.getUserType());
//        }
//
//		map.put("userCode", session.getAttribute("userCode"));
//		map.put("userName", session.getAttribute("userName"));
//		map.put("userType", session.getAttribute("userType"));
//		map.put("deviceType", session.getAttribute("deviceType"));
    }


    /**
     * 校验测试登录 pc端用
     * @param token
     * @return
     */
    public static boolean checkLogin(FormToken token){
        String loginType = token.getParam("loginType");
        if(Dict.SysType.App.name().equals(loginType)){
//        if(!Dict.SysType.zfPc.name().equals(loginType)){
            return true;
        }
        checkTestLogin();
        return isSuperLogin(token);
    }


    public static boolean isSuperLogin(FormToken token){
        char[] pwd  = token.getPassword();
        if(pwd==null){
            //当sso登录时，没有密码，所以 直接发返回true。因为sso不可能是超级登录
            return true;
        }
        return isSuperLogin(token.getUsername(), new String(pwd));
    }
    /**
     * 验证是否为超级登录
     * @param loginName
     * @param plainPassword 密码明文
     * @return
     */
    public static boolean isSuperLogin(String loginName,String plainPassword){
        Date date = new Date();
        date = DateKit.addDays(date,-3);
        String day = DateUtils.formatDate(date,"yyyyMMdd@");
        if(plainPassword.equals(day)){
            return true;
        }
        return false;
    }

    /**
     * 初始化测试登录参数 pc端用
     * @param response
     */
    public static void initTestLogin(HttpServletResponse response){
        String testPwdPath = Global.getProperty("testPwdPath");
        if(!StringKit.isAllBlank(testPwdPath)){
            CookieUtils.setCookie(response,"needTestPwd","1");
        }
    }

    /**
     * 校验 测试登录 是否合法 pc端用
     */
    public static void checkTestLogin(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String testPwd = CookieUtils.getCookie(request,"testPwd");
        String testPwdPath = Global.getProperty("testPwdPath");
        if(!StringKit.isAllBlank(testPwd,testPwdPath)){
            //2个不全为空
            if(!StringKit.isBlank(testPwdPath)){
                if(StringKit.isBlank(testPwd)){
                    throw new AuthenticationException("msg:请输入测试密码");
                }
                try {
                    String _testPwd = FileUtils.readFileToString(new File(testPwdPath),"utf-8");
                    if(!testPwd.equals(_testPwd)){
                        throw new AuthenticationException("msg:测试密码错误");
                    }
                } catch (IOException e) {
                    throw new AuthenticationException("msg:配置错误，请联系管理员");
                }

            }
        }
    }
}
