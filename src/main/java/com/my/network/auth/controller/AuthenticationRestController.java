package com.my.network.auth.controller;


import com.my.network.auth.JwtAuthenticationRequest;
import com.my.network.auth.JwtTokenUtil;
import com.my.network.auth.JwtUser;
import com.my.network.auth.model.Users;
import com.my.network.auth.model.UsersRepository;
import com.my.network.auth.service.JwtAuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UsersRepository usersRepository;

    private static final String jwtTokenCookieName = "JWT-TOKEN";

    //@PostMapping(value = "${jwt.route.authentication.path}")
    @PostMapping(value = "/")
    public ResponseEntity createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest) throws AuthenticationException {

        System.out.println("Came to login screen");
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //Users existingUser = usersRepository.findById(authenticationRequest.getUsername()).get();

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails, authenticationRequest.getUsername());

        // Return the token
        //CookieUtil.create(httpServletResponse, jwtTokenCookieName, token, false, -1, "localhost");
        // CookieUtil.create(httpServletResponse, name, value, secure, maxAge, domain);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    @GetMapping(value = "${jwt.route.authentication.refresh}")
    public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUserIdFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = jwtTokenUtil.refreshToken(token);
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping(value = "/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) {

    /* Getting session and then invalidating it */
        HttpSession session = request.getSession(false);
        if (request.isRequestedSessionIdValid() && session != null) {
            session.invalidate();
        }
        CookieUtil.clear(response, jwtTokenCookieName);
    }

    @PostMapping(value = "/register")
    public ResponseEntity register(@RequestBody Users user) {
        // String userType = "Admin";
        // String userType = "Retailer";
        // String userType = "Supplier";
        if(user.getUserName() == null || user.getPassword() == null){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }

        user.setUserId(UUID.randomUUID().toString());
        user.setIsApproved(true);
        String pwd = bcryptEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        return new ResponseEntity<>(usersRepository.save(user), HttpStatus.OK);
    }


}
