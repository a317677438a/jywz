package xft.workbench.backstage.base.mail.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.util.StringUtils;

import xft.workbench.backstage.base.mail.IEmailSender;

/**
 * 邮件发送实现类
 * @author 
 * @email 
 * @date 
 * @version 1.0
 */
public class EmailSenderImpl implements IEmailSender {
	
	// 由spring注入邮件的发送对象	
	private JavaMailSender javaMailSender;
	private String from;
	/**
	 * 邮件发送
	 * @param from 发送人
	 * @param to 接收人
	 * @param content 邮件内容
	 * @param subject 邮件主题
	 * @param html true:html 格式  false:文本
	 */
	@SuppressWarnings("finally")
	@Override
	public boolean send(String from, String to, String content, String subject,
			boolean html) {
		   boolean flag = true;
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			if (StringUtils.hasText(from)){
				message.setFrom(from); // 发送人
			}else{
				message.setFrom(this.from); // 发送人
			}
		    message.setTo(to); // 接收人
		    message.setSubject(subject); // 主题
		    message.setText(content, html); // 内容
		    message.setSentDate(new Date()); // 发送日期			    		    
		    javaMailSender.send(mimeMessage); // 发送邮件
		} catch (MessagingException e) {
			flag = false;
			e.printStackTrace();
			//throw new RuntimeException("发送邮件时出现异常！", e);			
		}		
		finally{
			return flag;
		}
		
	}

	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
