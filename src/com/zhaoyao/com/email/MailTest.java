package com.zhaoyao.com.email;

public class MailTest {
	public static void main(String[] args) {
		Mail mail = new Mail();  
//        mail.setHost("smtp.163.com"); // 设置邮件服务器,如果不用163的,自己找找看相关的  
//        mail.setSender("shadowsick@163.com");  
//        mail.setReceiver("shadowsick@163.com"); // 接收人  
//        mail.setUsername("shadowsick@163.com"); // 登录账号,一般都是和邮箱名一样吧  
//        mail.setPassword("xxxxx"); // 发件人邮箱的登录密码  
//        mail.setSubject("aaaaaaaaa");  
//        mail.setMessage("bbbbbbbbbbbbbbbbb");
        

        
        new MailUtil().send(mail);  
	}
}
