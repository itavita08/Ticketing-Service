package com.example.ticketing.service;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.Product;
import com.example.ticketing.model.Reservation;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.ReservationEntity;
import com.example.ticketing.model.entity.UserEntity;
import com.example.ticketing.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ProductRepository productRepository;
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final LeftSeatCacheRepository leftSeatCacheRepository;
    private final ProductCacheRepository productCacheRepository;

    // 현재 예매 가능한 상품 리스트 조회
    @Transactional(readOnly = true)
    public List<Product> productList(){
        return productRepository.findAllProduct().stream()
                .map(Product::fromEntity).toList();
    }

//    @Cacheable(cacheNames = "product", key = "#productId", value = "product")
////    @Transactional(readOnly = true)
//    public ProductEntity loadProduct(int productId){
//        return productRepository.findByProductId(productId).orElseThrow(() ->
//                new TicketingApplicationException(ErrorCode.PRODUCT_NOT_FOUND));
//    }

    // 예매하기
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Reservation reserve(String userName, Integer productId){
        UserEntity userEntity = userService.loadUser(userName);
        ProductEntity productEntity = soldOut(productId);

        duplicate(userEntity, productEntity);
        if (leftSeatCacheRepository.hasLeftTicketInRedis(productId)){
            leftSeatCacheRepository.decrementLeftTicketInRedis(productId);
        } else {
            productRepository.updateLeftTicket(productId);
        }

        ReservationEntity reservationEntity =  reservationRepository.saveAndFlush(ReservationEntity.builder()
                                                        .user(userEntity)
                                                        .product(productEntity)
                                                        .build());
        return Reservation.fromEntity(reservationEntity);
    }

    // 선택한 상품이 예매 가능한지 매진인지 확인
    @Transactional(readOnly = true)
    public ProductEntity soldOut(Integer productId){
        ProductEntity productEntity = productCacheRepository.loadProduct(productId);
        if(leftSeatCacheRepository.checkLeftTicketInRedis(productId)){
            return productEntity;
        } else {
            throw new TicketingApplicationException(ErrorCode.SOLD_OUT);
        }
    }

     //예매 중복 확인
    @Transactional(readOnly = true)
    public void duplicate(UserEntity user, ProductEntity product){
        reservationRepository.findByUserAndProduct(user,product).ifPresent(it -> {
            throw new TicketingApplicationException(ErrorCode.DUPLICATED_RESERVATION);
        });
    }

}
