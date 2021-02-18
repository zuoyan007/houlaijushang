package com.lamb.util.sys;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeesite.common.config.Global;
import com.jeesite.common.network.IpUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author gexu
 */
public class ResultApp implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 返回消息，失败时为失败信息。
	 */
	private String message;
	/**
	 * 返回数据
	 */
	private Object data;
	/**
	 * 返回状态
	 */
	private int status= Status.SUCCESS;


	public ResultApp() {
		super();
	}

	public ResultApp(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public ResultApp(Object data) {
		super();
		this.data = data;
	}
	public ResultApp(Object data,String message) {
		this(data);
		this.message = message;
	}


	public static ResultApp success(){
		return new ResultApp();
	}


	/**
	 * 兼容了老接口返回值字段
	 * 这个方法也可以用来做快速判断用，后期不需要兼容老接口，删除success返回值字段时，只需要加上  @JsonIgnore  注解即可。
	 * @return
	 * @author gexu
	 */
	@JsonIgnore
	public boolean isSuccess(){
		return this.status== Status.SUCCESS?true:false;
	}

	@JsonIgnore
	public boolean isFail(){
		return this.status!= Status.SUCCESS?true:false;
	}

	public static ResultApp success(Object data){
		return new ResultApp(data);
	}
	public static ResultApp success(Object data, String message){
		return new ResultApp(data,message);
	}

	public static Map<String,Object> createMap(String key,Object value){
		return createMap(new String[]{key}, new Object[]{value});
	}

	public static Map<String,Object> createMap(String keys[],Object values[]){
		Map<String,Object> map = new HashMap<String, Object>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return map;
	}

	public static ResultApp fail(){
		return new ResultApp(Status.SYSTEM_EXCEPTION,null);
	}
	public static ResultApp fail(int status){
		return new ResultApp(status,null);
	}
	public static ResultApp fail(int status, String message){
		return new ResultApp(status,message);
	}
	public static ResultApp fail(String message){
		return new ResultApp(Status.FAIL,message);
	}

	public String getMessage() {
		if ("1".equals(Global.getConfig("devMode"))) {
			if(StringKit.isBlank(message)) {
				HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
				String ip = IpUtils.getRemoteAddr(request);
				if(isFail()) {
					return "仅开发模式下返回：错误码：["+status+"] IP:["+ip+"] URL:["+request.getRequestURI()+"]";
				}
				return "仅开发模式下返回：操作成功. IP:["+ip+"] URL:["+request.getRequestURI()+"]";
			}else{
				return "开发模式"+message;
			}
		}
		return  StringKit.defaultIfBlank(message,"操作成功");
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}



//	public static class PageApp<T>{
//		private int pageNo;
//		private int pageSize;
//		private List<T> list;
//		
//		public PageApp() {
//			super();
//		}
//		
//		public int getStartNo(){
//			return (this.pageNo-1)*this.pageSize;
//		}
//		
//		public int getEndNo(){
//			return (this.pageNo)*this.pageSize;
//		}
//		
//		public PageApp(int pageNo, int pageSize) {
//			super();
//			this.pageNo = pageNo<1?1:pageNo;
//			this.pageSize = pageSize<1?15:pageSize;
//		}
//
//		public int getPageNo() {
//			return pageNo;
//		}
//		public void setPageNo(int pageNo) {
//			this.pageNo = pageNo;
//		}
//		public int getPageSize() {
//			return pageSize;
//		}
//		public void setPageSize(int pageSize) {
//			this.pageSize = pageSize;
//		}
//		public List<T> getList() {
//			return list;
//		}
//		public void setList(List<T> list) {
//			this.list = list;
//		}
//		
//	}





	public int getStatus() {
		return status;
	}

	@Override
	public String toString() {
		Object obj = this.getData();
		System.out.println(JSON.toJSONString(obj));
		return "{\"message\":\""+this.getMessage()+"\",\"data\":\""+ JSON.toJSONString(obj)+"\",\"status\":"+this.getStatus()+"}";
	}

	public void setStatus(int status) {
		this.status = status;
	}
	public static final class AlertType {

		/**
		 * 不弹提示
		 */
		public static final int NONE = -1;

		/**
		 * 纯消息
		 */
		public static final int MSG = 10;


		/**
		 * 只有一个ok确认按钮
		 */
		public static final int OK = 20;

		/**
		 * 只有2个 ok和取消 确认按钮
		 */
		public static final int OK_CANCEL = 30;
	}


	public static final class Status {

		public static int SUCCESS = 1;

		public static int FAIL = 0;

		/**
		 * 无效的手机号
		 */
		public static int PHONE_INVALID = 10001;
		/**
		 * 无效的用户
		 */
		public static int USER_INVALID = 10003;
		/**
		 * 无效的验证码
		 */
		public static int VALIDATE_CODE_INVALID = 10002;
		/**
		 * 无操作权限
		 */
		public static int NO_PERMISSIONS = 10005;


		/**
		 * 身份证信息格式错误
		 */
		public static final int IDCARD_FORMAT_ERROR = 20001;

		/**
		 * 文书签名不完善，主要用于提交审核审批验证执法人员是否都签名了。
		 */
		public static int PARAMS_NOT_SIGN = 30001;


		/**
		 * 设备被禁用
		 */
		public static final int DEVICE_BLACK = 90081;




		/**
		 * 缺失必备的参数
		 */
		public static int PARAMS_NOT_ENOUGH = 90001;

		/**
		 * 无效参数
		 */
		public static int PARAMS_INVALID = 90002;

		/**
		 * 未登陆。
		 */
		public static int NO_LOGIN = 90098;

		/**
		 * 程序异常。
		 */
		public static int SYSTEM_EXCEPTION = 90099;
	}

	public static class ResultMap implements Serializable{
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		Map<String,Object> map = new HashMap<>();
		public ResultMap put(String key,Object value){
			map.put(key, value);
			return this;
		}
		public Map<String,Object> get(){
			return this.map;
		}
	}
}
