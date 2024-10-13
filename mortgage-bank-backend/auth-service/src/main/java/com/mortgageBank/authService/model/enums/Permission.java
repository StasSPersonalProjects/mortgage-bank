package com.mortgageBank.authService.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    CUSTOMER_READ("user:read"),
    CUSTOMER_CREATE("user:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    UNDERWRITER_CREATE("underwriter:create"),
    UNDERWRITER_READ("underwriter:read"),
    UNDERWRITER_UPDATE("underwriter:update"),
    UNDERWRITER_DELETE("underwriter:delete");

    @Getter
    private final String permission;

}
