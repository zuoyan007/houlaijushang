package com.lamb.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.Random;

public class EmailKit {
    public static String sendKit(String userEmail) throws EmailException {
        HtmlEmail email=new HtmlEmail();
        //邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setHostName("smtp.qq.com");
        //设置发送的字符类型
        email.setCharset("utf-8");
        //设置收件人
        email.addTo(userEmail);
        //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setFrom("1293823332@qq.com","dzjboom");
        //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setAuthentication("1293823332@qq.com","byvptqamgaiaicid");
        //设置发送主题
        email.setSubject("后来居上社区团购新用户注册");

        String random = getrandom();
        //设置发送内容
        email.setMsg("尊敬的客户，您好：\n  欢迎注册后来居上社区团购 \n  您的验证码是："+random+"（请勿透露给其他人） \n  请复制后，填写在你的验证码窗口完成验证 \n  如有任何问题，请致电18182512955。感谢您的配合与支持！");
        //进行发送
        email.send();
        return random;
    }

    /**
     * 生成六位随机数
     * @return
     */
    public static String getrandom(){
        String code = "";
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            //每次随机出一个数字（0-9）
            int r = random.nextInt(10);
            //把每次随机出的数字拼在一起
            code = code + r;
        }
        return code;

    }
}
