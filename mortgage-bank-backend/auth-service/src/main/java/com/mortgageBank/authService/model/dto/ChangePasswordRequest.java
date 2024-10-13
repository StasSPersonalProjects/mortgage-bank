package com.mortgageBank.authService.model.dto;

import com.mortgageBank.authService.model.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangePasswordRequest {

    @NotBlank(message = "Please fill the password correctly.")
    private String currentPassword;
    @NotBlank(message = "Please choose a valid password.")
    @ValidPassword
    private String newPassword;
    @NotBlank(message = "Please choose a valid password.")
    @ValidPassword
    private String confirmationPassword;

}
