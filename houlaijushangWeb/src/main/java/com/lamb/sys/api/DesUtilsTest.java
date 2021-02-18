package com.lamb.sys.api;

import com.jeesite.common.codec.DesUtils;
import com.jeesite.common.config.Global;

/**
 * 加密解密
 */
public class DesUtilsTest {
    public static void main(String[] args) {
        String encode = DesUtils.encode("123456", Global.getConfig("shiro.loginSubmit.secretKey"));
        System.out.println(encode);
        String decode = DesUtils.decode(encode, Global.getConfig("shiro.loginSubmit.secretKey"));
        System.out.println(decode);
    }


}
