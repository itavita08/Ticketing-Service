package com.example.ticketing.fixture;

import com.example.ticketing.model.UserRole;
import com.example.ticketing.model.entity.UserEntity;

import java.sql.Timestamp;
import java.time.Instant;

public class UserEntityFixture {

    public static UserEntity testUserEntity(Integer userId, String userName, String password, UserRole role){
        return UserEntity
                .builder()
                .id(userId)
                .userName(userName)
                .password(password)
                .role(role)
                .registerAt(Timestamp.from(Instant.now()))
                .build();
    }
}
