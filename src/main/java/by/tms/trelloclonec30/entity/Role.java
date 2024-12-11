package by.tms.trelloclonec30.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    ROLE_USER, USER_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
