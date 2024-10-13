package com.mortgageBank.otpService.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpMail(String otp, String receiverEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receiverEmail);
        message.setSubject("S&M Mortgage OTP");
        message.setText("Your OTP code is: " + otp);
        log.debug("Mail sent to {}", receiverEmail);
        javaMailSender.send(message);
    }
}
