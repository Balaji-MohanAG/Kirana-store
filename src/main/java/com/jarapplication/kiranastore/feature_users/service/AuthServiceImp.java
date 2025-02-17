package com.jarapplication.kiranastore.feature_users.service;

import com.jarapplication.kiranastore.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AuthServiceImp implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserServiceImp userService;

    @Autowired
    public AuthServiceImp(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserServiceImp userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    /**
     * Authenticates the user
     * @param username
     * @param password
     * @return
     */
    @Override
    public String authenticate(String username, String password) {
        if(username==null || password==null){
            throw new IllegalArgumentException("Username or password cannot be null");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        List<String> roles = userService.getUserRolesByUsername(username);
        String userId = userService.getUserIdByUsername(username);
        return jwtUtil.generateToken(username, roles, userId);
    }
}

