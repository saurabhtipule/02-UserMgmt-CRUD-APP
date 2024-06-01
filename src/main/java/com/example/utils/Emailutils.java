package com.example.utils;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class Emailutils {

	@Autowired
	private JavaMailSender sender;

	public boolean sendEmail(String to, String subject, String body) {

		boolean isSent = false;
		try {

			MimeMessage mimeMessage = sender.createMimeMessage();
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);

			messageHelper.setTo(to);
			messageHelper.setSubject(subject);
			messageHelper.setText(body, true);
			sender.send(mimeMessage);
			isSent = true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSent;

	}

}
