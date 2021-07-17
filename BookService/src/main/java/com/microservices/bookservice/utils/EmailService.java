package com.microservices.bookservice.utils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
        JavaMailSender javaMailSender;
	
	public void sendMail(String body,String subject,String emailID) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setTo(emailID);
        helper.setSubject(subject);
        helper.setText(body,true);
        javaMailSender.send(message);
    }
}
