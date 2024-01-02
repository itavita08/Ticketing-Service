package com.example.ticketing.repository;

import com.example.ticketing.exception.ErrorCode;
import com.example.ticketing.exception.TicketingApplicationException;
import com.example.ticketing.model.Product;
import com.example.ticketing.model.entity.ProductEntity;
import com.example.ticketing.model.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class LeftSeatCacheRepository {

    private final RedisTemplate<String, Integer> leftRedisTemplate;
    private final ProductRepository productRepository;

    public void saveLeftTicketToRedis(Product product){
        String key = getKey(product.getId());
        leftRedisTemplate.opsForValue().set(key, product.getLeftTicket(), Duration.ofDays(1));
    }

    @Scheduled(cron = "0 * * * * *")
    public void saveLeftTicketFromRedis(){
        Set<String> keys = leftRedisTemplate.keys("LT:*");
        if (keys == null || keys.isEmpty()){
            return;
        }
        for (String key : keys){
            Integer leftTicketInRedis = leftRedisTemplate.opsForValue().get(key);
            int productId = Integer.parseInt(key.substring(3));

            ProductEntity productEntity = productRepository.findByProductId(productId)
                    .orElseThrow(() -> new TicketingApplicationException(ErrorCode.PRODUCT_NOT_FOUND));

            productEntity.setLeftTicket(leftTicketInRedis);
            productRepository.save(productEntity);
        }
    }

    public Boolean hasLeftTicketInRedis(int productId){
        String key = getKey(productId);
        return leftRedisTemplate.hasKey(key);
    }

    public boolean checkLeftTicketInRedis(int productId){
        String key = getKey(productId);
        Optional<Integer> leftTicket = Optional.ofNullable(leftRedisTemplate.opsForValue().get(key));
        if (leftTicket.orElse(productRepository.findLeftTicketByProductId(productId)) > 0){
            return true;
        }
        return false;
    }

    public void decrementLeftTicketInRedis(int productId){
        String key = getKey(productId);
        leftRedisTemplate.opsForValue().decrement(key);
    }

    private String getKey(int keyName) { return "LT:" + keyName; }
}
