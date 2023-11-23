package com.example.ticketing.controller.response;

import com.example.ticketing.model.User;
import com.example.ticketing.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserLoginResponse fromUser(User user){
        return new UserLoginResponse(
                user.getId(),
                user.getUsername(),
                user.getRole()
        );
    }
}
