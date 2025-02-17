package com.jarapplication.kiranastore.feature_users.models;

import com.jarapplication.kiranastore.AOP.annotation.Capitalize;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
public class UserRequest {

    @Capitalize
    private String username;
    private String password;
    private List<String> roles;

}
