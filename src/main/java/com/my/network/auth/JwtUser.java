package com.my.network.auth;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;


public class JwtUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	private final String userId;
    private final String userEmail;
    private final String password;
    private final Collection<GrantedAuthority> authorities;
    private final boolean enabled;
    private final Timestamp lastPasswordResetDate;

    public JwtUser(
            String userId,
            String userEmail,
            String password,
            Collection<GrantedAuthority> authorities,
            boolean enabled,
            Timestamp lastPasswordResetDate
    ) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    public String getUserId() {
        return userId;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

   
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }
    
   
}

