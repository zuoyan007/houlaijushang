package com.lamb.sys.api;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

public class EmailTest {
    public static void main(String[] args) throws EmailException {
        HtmlEmail email=new HtmlEmail();
        //邮箱的SMTP服务器，一般123邮箱的是smtp.123.com,qq邮箱为smtp.qq.com
        email.setHostName("smtp.qq.com");
        //设置发送的字符类型
        email.setCharset("utf-8");
        //设置收件人
        email.addTo("1343595363@qq.com");
        //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setFrom("1293823332@qq.com","dzjboom");
        //设置发送人到的邮箱和用户名和授权码(授权码是自己设置的)
        email.setAuthentication("1293823332@qq.com","byvptqamgaiaicid");
        //设置发送主题
        email.setSubject("后来居上社区团购新用户注册");
        //设置发送内容
        email.setMsg("尊敬的客户，您好：\n  欢迎注册后来居上社区团购 \n  您的验证码是：102371（请勿透露给其他人） \n  请复制后，填写在你的验证码窗口完成验证 \n  如有任何问题，请致电18182512955。感谢您的配合与支持！");
        //进行发送
        email.send();
    }
}
