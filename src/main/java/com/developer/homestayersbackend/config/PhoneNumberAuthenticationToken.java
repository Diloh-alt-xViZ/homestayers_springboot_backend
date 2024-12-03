



package com.developer.homestayersbackend.config;


import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PhoneNumberAuthenticationToken extends AbstractAuthenticationToken {

    private final String phoneNumber;
    private Object credentials;


    public PhoneNumberAuthenticationToken(String phoneNumber, Object credentials) {
        super(null);
        this.phoneNumber = phoneNumber;
        this.credentials = credentials;
        setAuthenticated(false);
    }
    public PhoneNumberAuthenticationToken(String phoneNumber, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phoneNumber = phoneNumber;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.phoneNumber;
    }

    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        if(authenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted");
        }

        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }
}
