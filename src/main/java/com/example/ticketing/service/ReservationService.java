package com.example.ticketing.service;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.Product;
import com.example.ticketing.model.Reservation;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.ReservationEntity;
import com.example.ticketing.model.entity.UserEntity;
import com.example.ticketing.repository.ProductRepository;
import com.example.ticketing.repository.ReservationRepository;
import com.example.ticketing.repository.StadiumRepository;
import com.example.ticketing.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StadiumRepository stadiumRepository;

    // 현재 예매 가능한 상품 리스트 조회
    @Transactional(readOnly = true)
    public List<Product> productList(){

        return productRepository.findAllProduct().stream()
                .map(Product::fromEntity).toList();
    }

    @Transactional(readOnly = true)
    public UserEntity loadUser(String userName){

        return userRepository.findByUserName(userName).orElseThrow(() ->
                new TicketingApplicationException(ErrorCode.USER_NOT_FOUND));
    }

    // 예매하기
    @Transactional
    public Reservation reserve(String userName, Integer productId){

        ProductEntity productEntity = soldOut(productId);
        UserEntity userEntity = loadUser(userName);

        duplicate(userEntity, productEntity);

        ReservationEntity reservationEntity =  reservationRepository.saveAndFlush(ReservationEntity.builder()
                                                        .user(userEntity)
                                                        .product(productEntity)
                                                        .build());
        stadiumRepository.updateRemain(productId);

        return Reservation.fromEntity(reservationEntity);
    }

    // 선택한 상품이 예매 가능한지 매진인지 확인
    @Transactional(readOnly = true)
    public ProductEntity soldOut(Integer productId){

        ProductEntity productEntity = productRepository.findByProductId(productId)
                .orElseThrow(() -> new TicketingApplicationException(ErrorCode.PRODUCT_NOT_FOUND));

        if(productEntity.getStadium().getRemain() == 0){
            throw new TicketingApplicationException(ErrorCode.SOLD_OUT);
        }
        return productEntity;
    }

    // 예매 중복 확인
    @Transactional(readOnly = true)
    public void duplicate(UserEntity user, ProductEntity product){

        reservationRepository.findByUserAndProduct(user,product).ifPresent(it -> {
            throw new TicketingApplicationException(ErrorCode.DUPLICATED_RESERVATION);
        });
    }
}
