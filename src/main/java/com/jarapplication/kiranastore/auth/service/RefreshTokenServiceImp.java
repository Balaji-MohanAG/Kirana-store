package com.jarapplication.kiranastore.auth.service;


import java.util.*;
import com.jarapplication.kiranastore.auth.dao.RefreshTokenDAO;
import com.jarapplication.kiranastore.auth.entity.RefreshToken;
import com.jarapplication.kiranastore.auth.models.RefreshTokenModel;
import com.jarapplication.kiranastore.feature_users.models.AuthResponse;
import com.jarapplication.kiranastore.utils.JwtUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import javax.naming.AuthenticationException;

import static com.jarapplication.kiranastore.constants.SecurityConstants.REFRESH_TOKEN_EXPIRATION_TIME;
import static com.jarapplication.kiranastore.constants.SecurityConstants.TOKEN_PREFIX;

@Component
public class RefreshTokenServiceImp implements RefreshTokenService {
    private final RefreshTokenDAO refreshTokenDao;
    private final JwtUtil jwtUtil;

    @Autowired
    public RefreshTokenServiceImp(RefreshTokenDAO refreshTokenDao, JwtUtil jwtUtil) {
        this.refreshTokenDao = refreshTokenDao;
        this.jwtUtil = jwtUtil;
    }

    /**
     * Implementation of saving refersh token
     * @param userId
     * @return
     */
    @Override
    public RefreshTokenModel saveRefreshToken(String userId) {
        String token = UUID.randomUUID().toString();
        //hash
        String tokenHash = token;
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUserId(userId);
        refreshToken.setToken(tokenHash);
        refreshToken.setCreatedAt(new Date());
        refreshToken.setTimeout(new DateTime(new Date()).plus(REFRESH_TOKEN_EXPIRATION_TIME).toDate());
        refreshToken = refreshTokenDao.save(refreshToken);
        return new RefreshTokenModel(refreshToken.getToken(), refreshToken.getId());
    }

    /**
     * Implementation to generate new Access token
     * @param refreshToken
     * @param accessToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    public AuthResponse generateAccessToken(String refreshToken, String accessToken) throws AuthenticationException {
        accessToken = accessToken.replace(TOKEN_PREFIX, "");
        if(!jwtUtil.isvalidateToken(accessToken)){
            throw new AuthenticationException("Invalid access token");
        }
        String sessionId = jwtUtil.extractSessionId(accessToken);
        Optional<RefreshToken> refreshTokenEntity = refreshTokenDao.findById(sessionId);
        // hash
        String tokenHash = refreshToken;
        if(refreshTokenEntity.isEmpty()
                || !refreshTokenEntity.get().getToken().equals(tokenHash)
                || !refreshTokenEntity.get().getTimeout().after(new Date()))
        {
            throw new AuthenticationException("Authentication failed");
        }
            String username = jwtUtil.extractUsername(accessToken);
            List<String> roles = jwtUtil.extractRoles(accessToken);
            String userId = jwtUtil.extractUserId(accessToken);
            String newAccessToken = jwtUtil.generateToken(username, roles,userId, sessionId);
            return new AuthResponse(userId, newAccessToken, refreshToken);
    }


}
