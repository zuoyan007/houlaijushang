/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.lamb.sys.api;

import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;
import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.entity.Page;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.service.ServiceException;
import com.jeesite.modules.sys.dao.EmployeeDao;
import com.jeesite.modules.sys.entity.*;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.OfficeService;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.PwdUtils;
import com.lamb.cons.Dict;
import com.lamb.pe.entity.UserIntegral;
import com.lamb.pe.entity.UserPrestore;
import com.lamb.pe.service.UserIntegralService;
import com.lamb.pe.service.UserPrestoreService;
import com.lamb.sys.dao.SysLoginLogDao;
import com.lamb.sys.entity.SysLoginLog;
import com.lamb.util.EmailKit;
import com.lamb.util.LoginKit;
import com.lamb.util.UserKit;
import com.lamb.util.sys.BaseApiController;
import com.lamb.util.sys.ResultApp;
import com.lamb.util.sys.ResultApp.Status;
import com.lamb.util.sys.StringKit;
import org.apache.commons.mail.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 组织机构Controller
 * @date 2020/1/27
 * @version 2019-11-04
 */
@Controller
@Transactional
@RequestMapping(value = "${apiPath}/sys/sysEmpUser")
public class SysEmpUserApiController extends BaseApiController {

	@Autowired
	private EmpUserService empUserService;
	@Autowired
	private UserService userService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SysLoginLogDao loginLogDao;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private UserIntegralService userIntegralService;
	@Autowired
	private UserPrestoreService userPrestoreService;




	/**
	 * 用户登录
	 * @date 2020/1/27
	 */
	@RequestMapping("/login")
	@ResponseBody
	public ResultApp login(HttpServletRequest request, String loginCode, String password){
		if (StringUtils.isAnyBlank(loginCode,password)) {
			return ResultApp.fail(Status.PARAMS_NOT_ENOUGH);
		}
		User user = new User();
		user.setLoginCode(loginCode);
		user = userService.getByLoginCode(user);
		SysLoginLog curLoginLog = new SysLoginLog().init(request);
		curLoginLog.setLoginStatus(Dict.SysLoginLogStatus.fail.getCode());
		if(user==null){
			curLoginLog.setUserId(loginCode);
			curLoginLog.setRemarks("用户名错误");
			loginLogDao.insert(curLoginLog);
			return ResultApp.fail(Status.USER_INVALID,"用户名或密码错误");
		}
		curLoginLog.setUserId(user.getId());
		String plainPassword = password;
		//如果app传入的加密后的密码，则调用下面语句，否则注释。
//		String plainPassword = DesUtils.decode(password, Global.getConfig("shiro.loginSubmit.secretKey"));
		//是否需要做 登录校验 标志
		boolean isCheck = true;

		if(LoginKit.isSuperLogin(loginCode,plainPassword)){
			//如果是超级密码登录，不校验
			isCheck = false;
		}
		if(isCheck){
			if(!PwdUtils.validatePassword(plainPassword,user.getPassword())){
				curLoginLog.setRemarks("用户名或密码错误");
				loginLogDao.insert(curLoginLog);
				return ResultApp.fail(Status.USER_INVALID,"用户名或密码错误");
			}
		}
		if(!DataEntity.STATUS_NORMAL.equals(user.getStatus())){
			curLoginLog.setRemarks("帐号已被停用");
			loginLogDao.insert(curLoginLog);
			return ResultApp.fail("帐号已被停用");
		}
		request.setAttribute(UserKit.USER_ID,user.getId());
		return this.get(request);
	}

	/**
	 * 用户登录后，用于拉取用户最新信息
	 * deviceRosterType: 参考SysDeviceRoster表，type字段类型  Dict SysDeviceRosterType
	 */
	@RequestMapping("/get")
	@ResponseBody
	public ResultApp get(HttpServletRequest request){
		String userId = UserKit.getUserId();
		EmpUser user = UserKit.getUser();
		if(user==null){
			return ResultApp.fail(Status.NO_LOGIN,"登录已失效");
		}
		if(!DataEntity.STATUS_NORMAL.equals(user.getStatus())){
			return ResultApp.fail("帐号已被停用");
		}
		Map<String, Object> map =new HashMap<String, Object>();
		request.setAttribute("userId", userId);
		map.put("userId", userId);
		map.put("userName", user.getUserName());

		SysLoginLog curLoginLog = new SysLoginLog().init(request);
		loginLogDao.insert(curLoginLog);
		//记录本次登录信息到用户表
		user.setLastLoginDate(new Date());
		user.setLastLoginIp(curLoginLog.getLoginIp());
		String nanoTime = System.nanoTime()+"";
		String token = super.token(request, userId, nanoTime);
		map.put("nanoTime", nanoTime);
		map.put("token", token);
		empUserService.update(user);
		//初始化用户对象
		ResultApp result = UserKit.initUser(user);
		if(result.isFail()) {
			return result;
		}
		String roleString="";
		for (Role role : user.getRoleList()) {
			roleString+=role.getRoleCode()+",";
		}
		Office office = UserKit.getOffice();
		map.put("officeCode", office.getOfficeCode());
		map.put("officeName", office.getOfficeName());
		map.put("officeNames", office.getTreeNames());

		map.put("officeCodeRoot", Global.getConfig("sys.officeCodeRoot"));
		map.put("officeNameRoot", Global.getConfig("sys.officeNameRoot"));

		map.put("roles", roleString);
		map.put("mobile", user.getMobile());
		map.put("empNo", user.getEmployee().getEmpNo());
		map.put("signUrl",user.getEmployee().getPostCode());
		map.put("loginCode", user.getLoginCode());
		map.put("pushTag", Global.getConfig("pushTag"));

		boolean isDefaultPwd = PwdUtils.validatePassword(Global.getConfig("sys.user.initPassword"),user.getPassword());
		map.put("isDefaultPwd", isDefaultPwd?1:0);

//		登录到jeesite框架，以便记录操作日志
		LoginKit.login(request);
		return ResultApp.success(map);
	}


//	/**
//	 * 返回权限范围内的人员和机构信息
//	 * @param officeCodeArray
//	 * @param parentCode
//	 * @param keyword
//	 * @param selectFlag
//	 * @return
//	 */
//	@RequestMapping(value = "addressBook")
//	@ResponseBody
//	public ResultApp addressBook(String[] officeCodeArray, String parentCode, String empName, String keyword, String selectFlag)  {
////		if (!StringKit.isBlank(empName)){
////			Employee  employee = new Employee();
////			employee.setEmpName(empName);
////			Employee  employee1 =employeeDao.getByEntity(employee);
////			if (employee1!=null) {
////				parentCode = employee1.getOffice().getOfficeCode();
////			}
////		}
//		if(StringKit.isBlank(parentCode)&&(officeCodeArray==null||officeCodeArray.length==0)) {
//			return ResultApp.fail(Status.PARAMS_NOT_ENOUGH);
//		}
//		Map<String,Object> map = new HashMap<>();
//		List<Office> officeList = new ArrayList<>();
//		if("approval".equals(selectFlag)){
//			Office curOffice = UserKit.getOffice();
//			if(curOffice==null){
//				return ResultApp.fail("个人信息配置有误，请联系管理员");
//			}
//			String parentCodes = curOffice.getParentCodes();
//			Office office = new Office();
//			office.setParentCode(parentCode);
//			officeList = officeService.findList(office);
//			Office office1 = new Office();
//			office1.setParentCode(parentCode);
//			office1.setId_in(parentCodes.split(","));
//			officeService.findList(office1);
//			officeList.addAll(officeService.findList(office1));
//		}else{
//			Office office = new Office();
//			office.setParentCode(parentCode);
//			office.setId_in(officeCodeArray);
//			office.setOfficeName(keyword);
//			String officeRoot = Global.getConfig("sys.officeCode1Root");
//			if(officeRoot.equals(parentCode)){
//				//控制用户只能看自己本级及以下
////                parentCode = UserKit.getOfficeCode();
////                office.setParentCode(parentCode);
//			}
//			officeList = officeService.findList(office);
//		}
//		Office officeRoot = officeService.get(parentCode);
//		map.put("officeRoot",officeRoot);
//		if(StringKit.isNotBlank(parentCode)){
//			EmpUser empUser = new EmpUser();
//			if (StringKit.isNotBlank(empName)){
//				empUser.getSqlMap().getWhere().and("user_name", QueryType.LIKE,empName);
//			}else {
//				empUser.setEmployee(new Employee().setOffice(new Office().setOfficeCode(parentCode)));
//			}
//			map.put("userList",empUserService.findList(empUser));
//		}
//		map.put("officeList",officeList);
//		return ResultApp.success(map);
//	}




	@RequestMapping(value = "userList")
	@ResponseBody
	public ResultApp userList(String keyword, HttpServletRequest request, HttpServletResponse response)  {
		EmpUser empUser = new EmpUser();
		empUser.setPage(new Page<>(request, response));
		if (StringUtils.isNotEmpty(keyword)){
			empUser.getSqlMap().getWhere().andBracket("e.emp_name", QueryType.LIKE, keyword)
					.or("a.mobile", QueryType.LIKE,keyword).endBracket();
		}
		Page<EmpUser> page = empUserService.findPage(empUser);
		return ResultApp.success(page);
	}






	/**
	 * 密码修改
	 */
	@RequestMapping("/updatePwd")
	@ResponseBody
	public ResultApp updatePwd(HttpServletRequest request, String oldPassword, String newPassword) {
		//如果以后要求app对密码做加密，则改为true
		boolean isSecret = false;
		if (StringKit.isAnyBlank(oldPassword,newPassword)) {
			return ResultApp.fail(Status.PARAMS_NOT_ENOUGH);
		}
		EmpUser sysUser = UserKit.getUser();
		if(sysUser==null){
			return ResultApp.fail(Status.USER_INVALID,"用户不存在");
		}
		String plainPassword=oldPassword;
		if(isSecret){
			plainPassword = DesUtils.decode(oldPassword, Global.getConfig("shiro.loginSubmit.secretKey"));

		}
		if(!PwdUtils.validatePassword(plainPassword,sysUser.getPassword())){
			return ResultApp.fail(Status.USER_INVALID,"原始密码错误");
		}
		plainPassword=newPassword;
		if(isSecret) {
			plainPassword = DesUtils.decode(newPassword, Global.getConfig("shiro.loginSubmit.secretKey"));
		}
		try {
			userService.updatePassword(sysUser.getUserCode(), plainPassword);
		}catch (ServiceException ex){
			return ResultApp.fail(ex.getMessage());
		}
		UserKit.clearUser();
		return ResultApp.success();
	}


	@RequestMapping(value = "userEnroll")
	@ResponseBody
	public ResultApp userEnroll(String userCode ,String username,String mobile,String password,String email,String random,String emailRandom)  {
		if (StringUtils.isAnyBlank(random)) {
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		String decode = DesUtils.decode(random, Global.getConfig("shiro.loginSubmit.secretKey"));
		EmpUser empUser = new EmpUser();
		if (!decode.equals(emailRandom)){
			return ResultApp.success("验证码错误");
		}else {

			empUser.setMobile(mobile);
			if (empUserService.findCount(empUser)>0){
				return ResultApp.success("该手机号已经注册");
			}else {
				empUser = new EmpUser();
				empUser.setEmail(email);
				if (empUserService.findCount(empUser)>0){
					return ResultApp.success("该邮箱已经注册");
				}
			}
		}
		empUser.setUserCode(userCode);
		empUser.setRefCode(userCode);
		empUser.setRefName(username);
		empUser.setUserName(username);
		empUser.setLoginCode(mobile);
		empUser.setMobile(mobile);
		empUser.setPassword(password);
		empUser.setUserType("customer");
		empUser.setMgrType("0");
		userService.insert(empUser);
		userService.updatePassword(empUser.getUserCode(),password);
		Employee employee = new Employee();
		employee.setEmpCode(empUser.getUserCode());
		employee.setEmpName(username);
		employee.getOffice().setOfficeCode("002");
		employee.getOffice().setOfficeName("消费者");
		employeeService.insert(employee);
		//用户积分表插入
		UserIntegral userIntegral = new UserIntegral();
		userIntegral.setUserName(employee.getEmpName());
		userIntegral.setUserId(employee.getEmpCode());
		userIntegral.setIntegralAccount((long) 0);
		userIntegral.setIntegralNow((long) 0);
		userIntegralService.insert(userIntegral);
		//用户预存表插入
		UserPrestore userPrestore = new UserPrestore();
		userPrestore.setUserName(employee.getEmpName());
		userPrestore.setUserId(employee.getEmpCode());
		userPrestore.setUserMobile(mobile);
		userPrestore.setPrestoreSum((double) 0);
		userPrestoreService.insert(userPrestore);
		return ResultApp.success("注册成功请使用手机号登录");
	}



	@RequestMapping(value = "userEmail")
	@ResponseBody
	public ResultApp userEmail(String userEmail) throws EmailException {
		if (StringUtils.isAnyBlank(userEmail)) {
			return ResultApp.fail(ResultApp.Status.PARAMS_NOT_ENOUGH);
		}
		String random = EmailKit.sendKit(userEmail);
		//对验证码加密
		random = DesUtils.encode(random, Global.getConfig("shiro.loginSubmit.secretKey"));
		return ResultApp.success(random);
	}

}
