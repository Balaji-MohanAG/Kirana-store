package com.jarapplication.kiranastore.feature_users.entity;

import com.jarapplication.kiranastore.AOP.annotation.Capitalize;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;
    @Capitalize
    private String username;
    private String password;
    private List<String> roles;

}
