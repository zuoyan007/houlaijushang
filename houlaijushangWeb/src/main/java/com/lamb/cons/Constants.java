package com.lamb.cons;

/**
 * 系统常量
 * @author gexu
 * @date 2019年10月23日 上午10:10:50
 */
public interface Constants {

	/**
	 * 常用的字符串  常量用于 代表 true含义的 1
	 */
	String DATA_1 = "1";
	/**
	 * 数据默认值0
	 */
	String DATA_0 = "0";

	String DEV_USER_ID = "711";

	/**
	 * 缓存用的key 前缀
	 */
	enum CacheKey{
		/**
		 * app用户 缓存
		 */
		appUser,
	}
}
