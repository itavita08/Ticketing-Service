package com.example.ticketing.service;

import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.fixture.UserEntityFixture;
import com.example.ticketing.model.User;
import com.example.ticketing.model.UserRole;
import com.example.ticketing.model.entity.UserEntity;
import com.example.ticketing.repository.UserRepository;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("비즈니스 로직 - 로그인,회원가입")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks private UserService userService;
    @Mock private UserRepository userRepository;
    @Mock private BCryptPasswordEncoder encoder;

    @DisplayName("유저정보를 받아 새로운 유저면 회원가입 성공")
    @Test
    void givenUser_whenJoin_thenReturns(){

        String userName = "dudwns";
        String password = "1234";
        UserRole role = UserRole.ADMIN;
        UserEntity testUserEntity = UserEntityFixture.testUserEntity(1, userName, password, role);
        User testUser = User.fromEntity(testUserEntity);

        given(userRepository.findByUserName("dudwns")).willReturn(Optional.empty());
        given(userRepository.save(any())).willReturn(testUserEntity);

        User user = userService.join(userName, encoder.encode(password), role);

        assertThat(user).isInstanceOf(User.class);
        assertThat(user).usingRecursiveComparison().isEqualTo(testUser);
    }

    @DisplayName("유저정보를 받아 중복 유저면 회원가입 실패")
    @Test
    void givenUser_whenJoinDuplicate_thenReturnsFail(){

        String userName = "dudwns";
        String password = "1234";
        UserRole role = UserRole.ADMIN;
        UserEntity testUserEntity = UserEntityFixture.testUserEntity(1, userName, password, role);

        given(userRepository.findByUserName("dudwns")).willReturn(Optional.of(testUserEntity));

        Throwable thrown = catchThrowable(() -> {
            userService.join(userName, password, role);
        });

        assertThat(thrown).isInstanceOf(TicketingApplicationException.class)
                .hasMessageContaining("User name is duplicated");
    }
}
