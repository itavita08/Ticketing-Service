package com.example.ticketing.model;

import com.example.ticketing.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {
    private Integer id;
    private String userName;
    private String password;
    private UserRole role;
    private Timestamp registerAt;
    private Timestamp deletedAt;

    public static User fromEntity(UserEntity userEntity){
        return new User(
                userEntity.getId(),
                userEntity.getUserName(),
                userEntity.getPassword(),
                userEntity.getRole(),
                userEntity.getRegisterAt(),
                userEntity.getDeletedAt()
        );
    }
}
