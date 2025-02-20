package com.jarapplication.kiranastore.feature_users.service;

import com.jarapplication.kiranastore.auth.models.RefreshTokenModel;
import com.jarapplication.kiranastore.auth.service.RefreshTokenServiceImp;
import com.jarapplication.kiranastore.feature_users.models.AuthResponse;
import com.jarapplication.kiranastore.utils.JwtUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final RefreshTokenServiceImp refreshTokenService;

    @Autowired
    public AuthServiceImp(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            UserService userService,
            RefreshTokenServiceImp refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * Authenticates the user
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public AuthResponse authenticate(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("Username or password cannot be null");
        }
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        List<String> roles = userService.getUserRolesByUsername(username);
        String userId = userService.getUserIdByUsername(username);
        RefreshTokenModel refreshTokenModel = refreshTokenService.saveRefreshToken(userId);

        String jwtToken =
                jwtUtil.generateToken(username, roles, userId, refreshTokenModel.getSessionId());
        return new AuthResponse(userId, jwtToken, refreshTokenModel.getRefreshToken());
    }
}
