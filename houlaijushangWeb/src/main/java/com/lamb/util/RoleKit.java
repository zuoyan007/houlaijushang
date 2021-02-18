package com.lamb.util;

import com.jeesite.common.utils.SpringUtils;
import com.jeesite.modules.sys.entity.Role;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.RoleService;
import com.jeesite.modules.sys.utils.RoleUtils;
import com.lamb.util.sys.StringKit;

import java.util.List;

/**
 * 角色相关工具类
 * @author gexu
 * @date 2019年10月23日 下午12:19:54
 */
public class RoleKit extends RoleUtils {
	
	private static RoleService roleService = SpringUtils.getBean(RoleService.class);
	/**
	 * 判断用户是否具备指定的角色 【之一】
	 * @param user
	 * @param roleCode 多个角色，用英文逗号分隔开
	 * @return
	 * @author gexu
	 * @date 2019年10月23日 下午12:19:50
	 */
	public static boolean hasUserRoleOne(String roleCode) {
		return RoleKit.hasUserRoleOne(UserKit.getUser(),roleCode);
	}
	
	/**
	 * 判断用户是否具备指定的角色 【之一】
	 * @param user
	 * @param roleCode 多个角色，用英文逗号分隔开
	 * @return
	 * @author gexu
	 * @date 2019年10月23日 下午12:19:50
	 */
	public static boolean hasUserRoleOne(User user, String roleCode) {
		if(user.getRoleList()==null|| StringKit.isBlank(roleCode)) {
			return false;
		}
		for (Role role : user.getRoleList()) {
			if(roleCode.contains(role.getRoleCode())) {
				return true;
			}
		}
		return false;
	}
	
	public static List<Role> findList(String userId){
		Role selRole = new Role();
		selRole.setUserCode(userId);
		return roleService.findListByUserCode(selRole);
	}
}
