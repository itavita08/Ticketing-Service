package com.example.ticketing.controller;

import com.example.ticketing.controller.request.UserJoinRequest;
import com.example.ticketing.controller.request.UserLoginRequest;
import com.example.ticketing.controller.response.Response;
import com.example.ticketing.controller.response.UserJoinResponse;
import com.example.ticketing.controller.response.UserLoginResponse;
import com.example.ticketing.model.User;
import com.example.ticketing.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request){
        User user = userService.join(request.getUserName(), request.getPassword(), request.getRole());

        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request){
        User user = userService.login(request.getUserName(), request.getPassword());
        
        return Response.success(UserLoginResponse.fromUser(user));
    }
}
