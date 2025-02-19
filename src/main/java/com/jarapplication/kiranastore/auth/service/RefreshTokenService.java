package com.jarapplication.kiranastore.auth.service;

import com.jarapplication.kiranastore.auth.entity.RefreshToken;
import com.jarapplication.kiranastore.auth.models.RefreshTokenModel;
import com.jarapplication.kiranastore.feature_users.models.AuthResponse;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
public interface RefreshTokenService {
    /**
     * Saves the refresh token
     * @param userId
     * @return
     */
    RefreshTokenModel saveRefreshToken(String userId);

    /**
     * Generates new AccessToken
     * @param refreshToken
     * @param accessToken
     * @return
     * @throws AuthenticationException
     */
    AuthResponse generateAccessToken(String refreshToken, String accessToken) throws AuthenticationException;
}
