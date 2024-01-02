package com.example.ticketing.repository;

import com.example.ticketing.model.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

    @Query("SELECT p FROM ProductEntity p join fetch p.stadium")
    List<ProductEntity> findAllProduct();

    @Query("SELECT p FROM ProductEntity p join fetch p.stadium WHERE p.id =:id")
    Optional<ProductEntity> findByProductId(@Param("id") Integer productId);

    @Query("SELECT p.leftTicket FROM ProductEntity p WHERE p.id =:id")
    int findLeftTicketByProductId(@Param("id") Integer productId);

    @Transactional
    @Modifying
    @Query("UPDATE ProductEntity p set p.leftTicket = p.leftTicket-1 where p.id =:id")
    void updateLeftTicket(@Param("id") int productId);
}
