package com.example.ticketing.service;

import com.example.ticketing.model.Product;
import com.example.ticketing.model.ViewingAge;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.StadiumEntity;
import com.example.ticketing.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@DisplayName("비즈니스 로직 - 티켓팅")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @InjectMocks ReservationService reservationService;
    @Mock ProductRepository productRepository;

    @Test
    void productList() {
        List<ProductEntity> testList = new ArrayList<>();
        ProductEntity testProductEntity = createProductTest();
        testList.add(testProductEntity);


        given(productRepository.findAllProduct()).willReturn(testList);

        List<Product> testList2 = reservationService.productList();

        assertThat(testList2.get(0)).isInstanceOf(Product.class);
        assertThat(testList2).usingRecursiveComparison().isEqualTo(testList.stream().map(Product::fromEntity).toList());
    }

    @Test
    void reserve() {
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
                .capacity(1000)
                .build();
    }
}