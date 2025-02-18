package com.jarapplication.kiranastore.feature_users.service;

import com.jarapplication.kiranastore.auth.entity.RefreshToken;
import com.jarapplication.kiranastore.auth.models.RefreshTokenModel;
import com.jarapplication.kiranastore.auth.util.RefreshTokenUtil;
import com.jarapplication.kiranastore.feature_users.models.AuthResponse;
import com.jarapplication.kiranastore.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class AuthServiceImp implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenUtil refreshTokenUtil;

    @Autowired
    public AuthServiceImp(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserService userService,
            RefreshTokenUtil refreshTokenUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenUtil = refreshTokenUtil;
    }

    /**
     * Authenticates the user
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthResponse authenticate(String username, String password) {
        if(username==null || password==null){
            throw new IllegalArgumentException("Username or password cannot be null");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        List<String> roles = userService.getUserRolesByUsername(username);
        String userId = userService.getUserIdByUsername(username);
        RefreshTokenModel refreshTokenModel = refreshTokenUtil.saveRefreshToken(userId);

        String jwtToken = jwtUtil.generateToken(username, roles, userId, refreshTokenModel.getSessionId());
        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(userId);
        authResponse.setAccessToken(jwtToken);
        authResponse.setRefreshToken(refreshTokenModel.getRefreshToken());
        return authResponse;
    }
}

