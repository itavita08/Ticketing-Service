package com.example.ticketing.repository;

import com.example.ticketing.model.entity.StadiumEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface StadiumRepository extends JpaRepository<StadiumEntity, Integer> {

    @Transactional
    @Modifying
    @Query("UPDATE StadiumEntity s set s.remain = s.remain-1 where s.id =:id")
    void updateRemain(@Param("id") Integer productId);
}
