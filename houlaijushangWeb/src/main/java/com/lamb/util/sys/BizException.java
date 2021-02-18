package com.lamb.util.sys;

/**
 * 自定义异常
 * @author gexu
 */
public class BizException extends RuntimeException {

    public BizException() {
        super();
    }
    public BizException(String message) {
        super(StringKit.isBlank(message)?"未知错误":message.startsWith("msg:")?message:"msg:"+message);
    }
    public BizException(Throwable t) {
        super(t);
    }
    public BizException(String message,Throwable t) {
        super(message,t);
    }
}
