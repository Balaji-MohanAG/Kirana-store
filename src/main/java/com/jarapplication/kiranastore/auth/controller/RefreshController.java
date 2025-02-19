package com.jarapplication.kiranastore.auth.controller;

import com.jarapplication.kiranastore.auth.service.RefreshTokenService;
import com.jarapplication.kiranastore.feature_users.models.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping
public class RefreshController {
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public RefreshController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    /**
     * to refresh Access token if refresh token is valid
     * @param accessToken
     * @param refreshToken
     * @return
     * @throws AuthenticationException
     */
    @GetMapping("/generate-token")
    public AuthResponse refreshAccessToken(@RequestHeader("Authorization") String accessToken, @RequestHeader("Refresh-Token") String refreshToken) throws AuthenticationException {
        return refreshTokenService.generateAccessToken(refreshToken, accessToken);
    }

}
