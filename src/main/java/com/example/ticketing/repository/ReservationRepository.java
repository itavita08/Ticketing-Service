package com.example.ticketing.repository;

import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.ReservationEntity;
import com.example.ticketing.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Integer> {

    long countByProduct(ProductEntity product);
    Optional<ReservationEntity> findByUserAndProduct(UserEntity user, ProductEntity product);
}
