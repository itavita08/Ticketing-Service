package com.example.ticketing.service;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.User;
import com.example.ticketing.model.UserRole;
import com.example.ticketing.model.entity.UserEntity;
import com.example.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    // 유저정보를 이용해 중복 확인 후 회원가입 진행
    @Transactional
    public User join(String userName, String password, UserRole role) {
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new TicketingApplicationException(ErrorCode.DUPLICATED_USER, userName);
        });

        UserEntity userEntity = userRepository.save(UserEntity.of(userName, passwordEncoder.encode(password), role));

        return User.fromEntity(userEntity);
    }

    // 유저정보를 이용해 회원가입한 유저인지 확인 로그인
    @Transactional(readOnly = true)
    public User login(String userName, String password) {
        User user = userRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                () -> new TicketingApplicationException(ErrorCode.USER_NOT_FOUND, userName));

        if(!passwordEncoder.matches(password, user.getPassword())){
            throw new TicketingApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        return user;
    }
}
