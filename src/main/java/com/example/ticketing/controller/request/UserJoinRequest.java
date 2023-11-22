package com.example.ticketing.controller.request;

import com.example.ticketing.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserJoinRequest {

    private String userName;
    private String password;
    private UserRole role;
}
