package com.example.ticketing.model.entity;

import com.example.ticketing.model.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@Table(name = "user", indexes = {
        @Index(name = "idx_user_id", columnList = "id")
})
@Where(clause = "deleted_at is NULL")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private UserRole role;

    @Column(name = "register_at")
    private Timestamp registerAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registerAt() {
        this.registerAt = Timestamp.from(Instant.now());
    }

    public static UserEntity of(String userName, String password, UserRole role){
        return UserEntity.builder().userName(userName).password(password).role(role).build();
    }

}
