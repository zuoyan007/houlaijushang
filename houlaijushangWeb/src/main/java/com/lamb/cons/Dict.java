package com.lamb.cons;

/**
 * 数据字典
 */

public class Dict {






	/**
	 *	private String startStatus;		// 启动状态   0、未启动1、已启动
	 * @author dizj
	 * @date 2021/1/27
	 *
	 */
	public static enum YesOrNo{
		no(0),
		yes(1);
		private int code;
		YesOrNo(int code) {
			this.code = code;
		}
		public int getCode() {
			return code;
		}
	}




	/**
	 * 登录 状态
	 ** 状态 10、待辅助验证20、失败。30、成功
	 * @author dizj
	 *
	 */
	public static enum SysLoginLogStatus{
		wait(10),
		fail(20),
		success(30);

		private Integer code;

		SysLoginLogStatus(Integer code) {
			this.code = code;
		}
		public Integer getCode() {
			return code;
		}
	}

	/**
	 *	empUser:普通员工
	 * @author dizj
	 */
	public static enum RoleCode{
		corpAdmin,empUser,
	}

	/**
	 * 系统类型
	 */
	public static enum SysType{
		App, ldApp,zfPc;
	}


	/**
	 * imgType;
	 * 风险点管理图片 0
	 * @author dizj
	 *
	 */
	public static enum imgType{
		riskPoint(0,"riskPoint_image");
		private int code;
		private String name;
		imgType(int code,String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}



	/**
	 * 数据字典：风险类型管理：risk_point_type
	 * @author dizj
	 *
	 */
	public static enum dictionaries{
		risk(0,"risk_point_type");
		private int code;
		private String name;
		dictionaries(int code,String name) {
			this.code = code;
			this.name = name;
		}
		public int getCode() {
			return code;
		}

		public String getName() {
			return name;
		}
	}



}