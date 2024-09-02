package com.MyProject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService  {
	
	@Value("${spring.mail.username}")
	private String MESSAGE_FROM;
	
	private final Logger logg= LoggerFactory.getLogger(this.getClass());
	
	private JavaMailSender javaMailSender;
	@Autowired
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	public void sendMessage(String firstName, String lastName, String email, String activation) {
			SimpleMailMessage message=null;
		try {
			message= new SimpleMailMessage();
			message.setFrom(MESSAGE_FROM);
			message.setTo(email);
			message.setSubject("Aktiváció");
			message.setText("Kedves "+ lastName+ "!" 
					+ "/n/n "
					+ "Örülök hogy regisztráltál az oldalamra. "
					+ "Remélem elnyeri tetszésedet."
					+ "Hogy az alkalmazásokat használhasd, kérlek regisztrálj az alábbi kóddal: " 
					+ activation
					+ "/n "
					+ "Bármilyen javaslatod van a látottakkal kapcsolatban,"
					+ "kérlek jelezd nekem ezen az email címen."
					+ "/n/n"
					+ "Köszönettel"
					+ "/n"
					+ "Ádám"
					);
			javaMailSender.send(message);

		} catch (Exception e) {
			System.err.println("Hiba az email elküldése során erre a címre " + email);

		}
		
	}
}