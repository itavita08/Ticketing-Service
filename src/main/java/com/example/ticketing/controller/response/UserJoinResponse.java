package com.example.ticketing.controller.response;

import com.example.ticketing.model.User;
import com.example.ticketing.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinResponse {

    private String username;
    private UserRole role;

    public static UserJoinResponse fromUser(User user){
        return new UserJoinResponse(
                user.getUsername(),
                user.getRole()
        );
    }
}
