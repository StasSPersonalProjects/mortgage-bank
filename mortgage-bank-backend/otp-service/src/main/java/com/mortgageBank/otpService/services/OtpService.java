package com.mortgageBank.otpService.services;

import com.mortgageBank.otpService.model.dto.CustomerDto;
import com.mortgageBank.otpService.model.entities.Customer;
import com.mortgageBank.otpService.repositories.CustomersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class OtpService {

    private static final long OTP_EXPIRATION_TIME = 180;
    
    @Autowired
    private CustomersRepository customersRepository;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private EmailService emailService;

    @Transactional
    public void sendOtp(CustomerDto customerDto) {
        long id = customerDto.getId();
        String email = customerDto.getEmail();
        if (customersRepository.existsById(id)) {
            storeAndSendOtp(id, email);
        } else {
            createAndSaveCustomer(customerDto);
            storeAndSendOtp(id, email);
        }
    }

    public String getOtp(long id) {
        String key = "otp_" + id;
        return String.valueOf(redisTemplate.opsForValue().get(key));
    }

    public void deleteOtp(long userId) {
        String key = "otp_" + userId;
        redisTemplate.delete(key);
        log.debug("Deleted stored otp for user {}", userId);
    }

    private void storeAndSendOtp(long id, String email) {
        String key = "otp_" + id;
        String otp = generateRandomOtp();
        log.debug("Storing in Redis: key - {}, value - {}", key, otp);
        redisTemplate.opsForValue().set(key, otp, OTP_EXPIRATION_TIME, TimeUnit.SECONDS);
        emailService.sendOtpMail(otp, email);
    }

    @Transactional
    private void createAndSaveCustomer(CustomerDto customerDto) {
        Customer newCustomer = Customer.of(customerDto);
        customersRepository.save(newCustomer);
        log.debug("New Customer created - {}", newCustomer);
    }

    private String generateRandomOtp() {
        return String.valueOf((int) (Math.random() * 90000) + 10000);
    }

}
