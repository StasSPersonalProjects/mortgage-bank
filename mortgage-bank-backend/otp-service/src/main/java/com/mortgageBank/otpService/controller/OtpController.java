package com.mortgageBank.otpService.controller;

import com.mortgageBank.otpService.model.dto.CustomerDto;
import com.mortgageBank.otpService.services.OtpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/otp")
@Slf4j
public class OtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/generate")
    public ResponseEntity<Void> sendOtp(@RequestBody @Validated CustomerDto customerDto) {
        log.debug("Received request for sending otp code to email {}", customerDto.getEmail());
        otpService.sendOtp(customerDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam("id") long id, @RequestParam("otp") String otp) {
        log.debug("Received request for verifying otp code {}", otp);

        // TODO - change the method to send a request to the auth service in order to generate a token
        // TODO - return the token to the user

        String storedOtp = otpService.getOtp(id);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpService.deleteOtp(id);
            return ResponseEntity.ok().body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
