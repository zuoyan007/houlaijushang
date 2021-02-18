package com.lamb.util;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.dao.UserRoleDao;
import com.jeesite.modules.sys.entity.*;
import com.jeesite.modules.sys.service.EmpUserService;
import com.jeesite.modules.sys.service.EmployeeService;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.utils.UserUtils;
import com.lamb.sys.dao.SysDictDataDao;
import com.lamb.sys.dao.SysLoginLogDao;
import com.lamb.util.sys.ResultApp;
import com.lamb.util.sys.StringKit;
import com.lamb.util.sys.BizException;
import com.lamb.cons.Constants;
import com.lamb.cons.Constants.CacheKey;
import com.lamb.cons.Dict;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class UserKit {

	public static final String USER_ID = "appUserId";

	private static final Logger LOG = LoggerFactory.getLogger(UserKit.class);
	private static EmpUserService empUserService = SpringUtils.getBean(EmpUserService.class);
	private static RoleService roleService = SpringUtils.getBean(RoleService.class);
	private static UserRoleDao userRoleDao = SpringUtils.getBean(UserRoleDao.class);
	private static EmployeeService employeeService = SpringUtils.getBean(EmployeeService.class);
	private static SysLoginLogDao sysLoginLogDao = SpringUtils.getBean(SysLoginLogDao.class);


//	public static HttpSession getSession(String userId){
//		return CacheUtils.get(CacheKey.appUserSession.name()+userId);
//	}

	public static ResultApp initUser(EmpUser user) {
		Role selRole = new Role();
		if(user==null) {
			throw new BizException("个人信息配置有误，请联系管理员。");
		}
		if(user.getEmployee()==null) {
			throw new BizException("个人信息配置有误，请联系管理员。");
		}
		String userId = user.getId();
		selRole.setUserCode(userId);
//		List<Role> roleList = roleService.findListByUserCode(selRole);
		List<Role> roleList = roleService.findList(selRole);
		//当用户没有角色时，自动加入消费人员角色
		if(roleList==null||roleList.size()==0) {
			UserRole role = new UserRole();
			role.preInsert();
			role.setUserCode(userId);
			role.setRoleCode("customer");
			userRoleDao.insert(role);
			roleList = roleService.findListByUserCode(selRole);
		}
		user.setRoleList(roleList);
		CacheUtils.put(Constants.CacheKey.appUser.name(),userId, user);

		return ResultApp.success();
	}

	/**
	 * 临时把以前api的代码拷贝过来，以后要逐步的改进代码，优化他
	 * @return
	 * @author gexu
	 * @date 2019-10-17
	 */
	public static String getUserId() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if(requestAttributes==null){
			//异步线程处理时，没有会话，request为null
			if(UserUtils.getUser()!=null){
				return UserUtils.getUser().getId();
			}
			return null;
		}
		HttpServletRequest request = requestAttributes.getRequest();

		String userId = request.getAttribute(USER_ID)+"";
		if(!StringKit.isNull(userId)) {
			return userId;
		}
		userId = request.getParameter(USER_ID);
		if("-2".equals(userId)) {
			return Constants.DEV_USER_ID;
		}
		if(!StringUtils.isBlank(userId)) {
			return userId;
		}

		userId = request.getHeader(USER_ID);
		if(!StringUtils.isBlank(userId)) {
			return userId;
		}

		userId = UserUtils.getUser().getId();
		return StringUtils.trimToNull(userId);
	}

	public static void clearUser() {
		clearUser(getUserId());
	}

	public static void clearUser(String userId) {
		CacheUtils.remove(CacheKey.appUser.name(),userId);
	}

	public static EmpUser getUser(String userId) {
		if(StringKit.isBlank(userId)){
			return null;
		}
		EmpUser user= CacheUtils.get(CacheKey.appUser.name(),userId);
		if(user!=null) {
			LOG.debug("APP用户 缓存命中");
			return user;
		}
		user = empUserService.get(userId);
		if(user==null){
			user=new EmpUser();
			user.setUserCode(userId);
			user.setUserType("customer");
			user = sysLoginLogDao.getCustomerUser("customer",userId);
			if (user==null){
				return null;
			}
		}
		user.setRoleList(RoleKit.findList(user.getId()));
		// 获取当前用户所拥有的岗位
		user.getEmployee().setEmployeePostList(employeeService.findEmployeePostList(user.getEmployee()));
		CacheUtils.put(CacheKey.appUser.name(),user.getId(), user);
		return user;
	}
	public static EmpUser getUser() {
		return getUser(getUserId());
	}


	public static Office getOffice() {
		if(UserKit.getUser()==null){
			return new Office();
		}
		return UserKit.getUser().getEmployee().getOffice();
	}

	public static String getOfficeCode() {
		if(UserKit.getUser()==null){
			return null;
		}
		return UserKit.getUser().getEmployee().getOffice().getOfficeCode();
	}


	public static String getCreateBy(String createBy){
		if(StringKit.isBlank(createBy)|| User.SUPER_ADMIN_CODE.equals(createBy)){
			createBy = UserKit.getUserId();
			if(StringKit.isBlank(createBy)){
				return User.SUPER_ADMIN_CODE;
			}
		}
		return createBy;
	}

}
