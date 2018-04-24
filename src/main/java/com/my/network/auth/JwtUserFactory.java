package com.my.network.auth;

import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import java.util.List;
import java.util.stream.Collectors;

public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(Users user) {
        return new JwtUser(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                mapToGrantedAuthorities(user.getUsersTypes()),
                user.getIsApproved(),
                user.getLastUpdatedOn()
        );
    }

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<UsersTypes> authorities) {
        return authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getMstType().getTypeName()))
                .collect(Collectors.toList());
    }
}

