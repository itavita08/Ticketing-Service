package com.example.ticketing.controller;

import com.example.ticketing.controller.request.UserJoinRequest;
import com.example.ticketing.controller.request.UserLoginRequest;
import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.User;
import com.example.ticketing.model.UserRole;
import com.example.ticketing.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserService userService;
    @MockBean
    private User user;

    @DisplayName("회원가입")
    @Test
    void givenUser_whenJoin_thenSuccess() throws Exception{
        String userName = "test";
        String password = "1234";
        UserRole role = UserRole.USER;

        when(userService.join(userName, password, role)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users/join")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password, role)))
                ).andDo(print())
                .andExpect(status().isOk());

    }

    @DisplayName("이미 회원가입되어서 회원가입 실패")
    @Test
    void givenDuplicatedUser_whenJoin_thenFail() throws Exception{
        String userName = "test";
        String password = "1234";
        UserRole role = UserRole.USER;

        when(userService.join(userName, password, role)).thenThrow(new TicketingApplicationException(ErrorCode.DUPLICATED_USER));

        mockMvc.perform(post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserJoinRequest(userName, password, role)))
                ).andDo(print())
                .andExpect(status().isConflict());
    }

    @DisplayName("로그인")
    @Test
    void givenUser_whenLogin_thenSuccess() throws Exception{
        String userName = "test";
        String password = "1234";

        when(userService.login(userName, password)).thenReturn(user);

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("로그인시 회원가입이 안된 아이디를 입력할 경우 로그인 실패")
    @Test
    void givenWrongUser_whenLogin_thenFail() throws Exception{
        String userName = "test";
        String password = "1234";

        when(userService.login(userName, password)).thenThrow(new TicketingApplicationException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("로그인시 비밀번호를 틀렸을 경우 로그인 실패")
    @Test
    void givenPassword_whenLogin_thenFail() throws Exception{
        String userName = "test";
        String password = "1234";

        when(userService.login(userName, password)).thenThrow(new TicketingApplicationException(ErrorCode.INVALID_PASSWORD));

        mockMvc.perform(post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new UserLoginRequest(userName, password)))
                ).andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
