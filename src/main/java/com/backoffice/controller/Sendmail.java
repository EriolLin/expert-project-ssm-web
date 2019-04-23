package com.backoffice.controller;
import java.security.GeneralSecurityException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sun.mail.util.MailSSLSocketFactory;

/**
 * JavaMail发送邮件:前提是QQ邮箱里帐号设置要开启POP3/SMTP协议
 */

@Controller
@RequestMapping("sendMail")
public class Sendmail {
	
//	private static String receiveMailNumberStr = "";

	@RequestMapping("set")
	@ResponseBody
	public String sendMail(@RequestParam("receiveMailNumber") String receiveMailNumber) throws Exception {

		Properties prop = new Properties();
// 开启debug调试，以便在控制台查看
		prop.setProperty("mail.debug", "true");
// 设置邮件服务器主机名
		prop.setProperty("mail.host", "smtp.qq.com");
// 发送服务器需要身份验证
		prop.setProperty("mail.smtp.auth", "true");
// 发送邮件协议名称
		prop.setProperty("mail.transport.protocol", "smtp");

// 开启SSL加密，否则会失败
		MailSSLSocketFactory sf = new MailSSLSocketFactory();
		sf.setTrustAllHosts(true);
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.ssl.socketFactory", sf);

// 创建session
		Session session = Session.getInstance(prop);
// 通过session得到transport对象
		Transport ts = session.getTransport();
// 连接邮件服务器：邮箱类型，帐号，授权码代替密码（更安全）
		ts.connect("smtp.qq.com", "308185765", "hqjfbopucbdbbggd");// 后面的字符是授权码，用qq密码反正我是失败了（用自己的，别用我的，这个号是我瞎编的，为了。。。。）
//随机产生4位验证码
		String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String uuid=new String();
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            uuid+=ch;
        }
// 创建邮件
		Message message = createSimpleMail(session,receiveMailNumber,uuid);
// 发送邮件
		ts.sendMessage(message, message.getAllRecipients());
		ts.close();
		return uuid;
	}
	
//	@RequestMapping("set")
//	@ResponseBody
//	public void setMailContent(@RequestParam("receiveMailNumber") String receiveMailNumber) {
//		
//		System.out.println("receiveMailNumber=="+receiveMailNumber);
//		
//		receiveMailNumberStr = receiveMailNumber;
//	}

	/**
	 * @Method: createSimpleMail
	 * @Description: 创建一封只包含文本的邮件
	 */
	public static MimeMessage createSimpleMail(Session session,String receiveMailNumber,String uuid) throws Exception {
// 创建邮件对象
		MimeMessage message = new MimeMessage(session);
// 指明邮件的发件人
		message.setFrom(new InternetAddress("308185765@qq.com"));
// 指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(receiveMailNumber));
// 邮件的标题
		message.setSubject("JavaMail验证码测试");
// 邮件的文本内容
		message.setContent(uuid, "text/html;charset=UTF-8");
// 返回创建好的邮件对象
		return message;
	}
}