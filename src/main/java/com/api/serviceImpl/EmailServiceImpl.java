package com.api.serviceImpl;


import jakarta.mail.internet.MimeMessage;


import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.api.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {
	
	
    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public String sendMail( String to) {
        try {
        	int otp=ThreadLocalRandom.current().nextInt(100000,999999);
        	
        	MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(fromEmail);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("OTP for Timesheet Management Project");
            mimeMessageHelper.setText("Please use the OTP to Register.\n"
            		+ "\n"
            		+ otp
            		+ "\n"
            		+ "\nIf you didn’t request this, you can ignore this email.\n"
            		+ "\n"
            		+ "Thanks,\n"
            		+ "Naveen Solanki");
            javaMailSender.send(mimeMessage);
            String strOTP = Integer.toString(otp);
            return strOTP;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
