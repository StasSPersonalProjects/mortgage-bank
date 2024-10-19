package com.mortgageBank.authService.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mortgageBank.authService.model.enums.Permission.*;

@RequiredArgsConstructor
public enum Role {

    CUSTOMER(
            Set.of(
                    CUSTOMER_READ,
                    CUSTOMER_CREATE
            )
    ),
    ADMIN(
            Set.of(
                    ADMIN_CREATE,
                    ADMIN_READ,
                    ADMIN_UPDATE,
                    ADMIN_DELETE,
                    UNDERWRITER_CREATE,
                    UNDERWRITER_READ,
                    UNDERWRITER_UPDATE,
                    UNDERWRITER_DELETE
            )
    ),
    UNDERWRITER(
            Set.of(
                    UNDERWRITER_CREATE,
                    UNDERWRITER_READ,
                    UNDERWRITER_UPDATE,
                    UNDERWRITER_DELETE
            )
    ),
    CONSULTANT(
            Set.of(
                    CONSULTANT_CREATE,
                    CONSULTANT_READ,
                    CONSULTANT_UPDATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
