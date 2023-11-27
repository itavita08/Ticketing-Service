package com.example.ticketing.service;

import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.fixture.UserEntityFixture;
import com.example.ticketing.model.Product;
import com.example.ticketing.model.Reservation;
import com.example.ticketing.model.UserRole;
import com.example.ticketing.model.ViewingAge;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.ReservationEntity;
import com.example.ticketing.model.entity.StadiumEntity;
import com.example.ticketing.model.entity.UserEntity;
import com.example.ticketing.repository.ProductRepository;
import com.example.ticketing.repository.ReservationRepository;
import com.example.ticketing.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DisplayName("비즈니스 로직 - 티켓팅")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks ReservationService reservationService;
    @Mock ProductRepository productRepository;
    @Mock
    ReservationRepository reservationRepository;
    @Mock
    UserRepository userRepository;

    @DisplayName("상품 리스트 불러오기")
    @Test
    void given_whenProduct_thenList() {
        List<ProductEntity> testList = new ArrayList<>();
        ProductEntity testProductEntity = createProductTest();
        testList.add(testProductEntity);


        given(productRepository.findAllProduct()).willReturn(testList);

        List<Product> testList2 = reservationService.productList();

        assertThat(testList2.get(0)).isInstanceOf(Product.class);
        assertThat(testList2).usingRecursiveComparison().isEqualTo(testList.stream().map(Product::fromEntity).toList());
    }

    @DisplayName("중복예매도 아니고 매진도 아니면 예매 성공")
    @Test
    void givenUserAndProduct_whenReservation_thenSuccess() {

        ReservationEntity reservationEntity = createReservationEntity();
        ProductEntity productEntity = createProductTest();
        UserEntity userEntity = UserEntityFixture.testUserEntity(1, "test", "1234", UserRole.USER);

        long n = 1;

        given(productRepository.findByProductId(productEntity.getId())).willReturn(Optional.of(productEntity));
        given(reservationRepository.countByProduct(productEntity)).willReturn(n);
        given(userRepository.findByUserName("test")).willReturn(Optional.of(userEntity));
        given(reservationRepository.findByUserAndProduct(userEntity, productEntity)).willReturn(Optional.empty());
        given(reservationRepository.saveAndFlush(any())).willReturn(reservationEntity);

        Reservation reservation = reservationService.reserve("test", 1);

        assertThat(reservation).isInstanceOf(Reservation.class);
        assertThat(reservation.getUserName()).isEqualTo("test");
        assertThat(reservation.getProductName()).isEqualTo("test");
    }

    @DisplayName("중복 예매이면 예매 실패")
    @Test
    void givenUserAndProduct_whenDuplicatedReservation_thenFail(){
        UserEntity userEntity = UserEntityFixture.testUserEntity(1, "test", "1234", UserRole.USER);
        ProductEntity productEntity = createProductTest();

        given(reservationRepository.findByUserAndProduct(userEntity, productEntity)).willReturn(Optional.of(createReservationEntity()));

        Throwable thrown = catchThrowable(() ->{
            reservationService.duplicate(userEntity, productEntity);
        });

        assertThat(thrown).isInstanceOf(TicketingApplicationException.class)
                .hasMessageContaining("Reservation is duplicated");
    }

    @DisplayName("예매 가능")
    @Test
    void givenProduct_whenReservation_thenSuccess(){
        ProductEntity productEntity = createProductTest();
        long n = 1;

        given(productRepository.findByProductId(productEntity.getId())).willReturn(Optional.of(productEntity));
        given(reservationRepository.countByProduct(productEntity)).willReturn(n);

        ProductEntity result = reservationService.soldOut(productEntity.getId());

        assertThat(result).isInstanceOf(ProductEntity.class);
    }

    @DisplayName("예매 불가능")
    @Test
    void givenProduct_whenReservationSoldOut_thenFail(){
        ProductEntity productEntity = createProductTest();
        long n = 10;

        given(productRepository.findByProductId(productEntity.getId())).willReturn(Optional.of(productEntity));
        given(reservationRepository.countByProduct(productEntity)).willReturn(n);

        Throwable thrown = catchThrowable(() -> {
            reservationService.soldOut(productEntity.getId());
        });

        assertThat(thrown).isInstanceOf(TicketingApplicationException.class)
                .hasMessageContaining("Product sold out");
    }

    public ProductEntity createProductTest(){
        Integer id = 1;
        String productName = "test";
        LocalDate startDate = LocalDate.parse("2023-11-11");
        LocalDate endDate = LocalDate.parse("2023-11-11");
        ViewingAge viewingAge = ViewingAge.ALL_AGE;
        Integer price = 1000;

        return ProductEntity.builder()
                .id(id)
                .productName(productName)
                .startDate(startDate)
                .endDate(endDate)
                .viewingAge(viewingAge)
                .stadium(createStadiumTest())
                .price(price)
                .build();
    }

    public StadiumEntity createStadiumTest(){
        return StadiumEntity.builder()
                .id(1)
                .stadiumName("test stadium")
                .address("경기도")
                .capacity(10)
                .build();
    }

    public ReservationEntity createReservationEntity(){
        Integer id = 1;
        UserEntity userEntity = UserEntityFixture.testUserEntity(1, "test", "1234", UserRole.USER);
        ProductEntity productEntity = createProductTest();

        return ReservationEntity.builder()
                .id(id)
                .user(userEntity)
                .product(productEntity)
                .build();
    }
}