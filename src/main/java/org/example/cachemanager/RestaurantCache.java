package org.example.cachemanager;

import org.example.dto.restaurant.RestaurantDTOredis;
import org.example.dto.restaurant.RestaurantView;
import org.example.model.Restaurant;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.JsonJacksonMapCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.stereotype.Component;

@Component
public class RestaurantCache {
    RMap<Long, RestaurantDTOredis> map;

    public void addRestaurantToCache(RedissonClient redissonClient, RestaurantDTOredis restaurant, Long id){
        map = redissonClient.getMap(NameCache.RESTAURANT_CACHE, new JsonJacksonMapCodec(Long.class, RestaurantDTOredis.class));
        map.put(id, restaurant);
    }

    public void removeRestaurantCache(RedissonClient redissonClient, RestaurantDTOredis restaurant){
        map = redissonClient.getMap(NameCache.RESTAURANT_CACHE, new JsonJacksonMapCodec(Long.class, RestaurantDTOredis.class));
        map.remove(restaurant);
    }

    public void updateRestaurantCache(RedissonClient redissonClient, RestaurantDTOredis restaurant, Long id){
        map = redissonClient.getMap(NameCache.RESTAURANT_CACHE, new JsonJacksonMapCodec(Long.class, RestaurantDTOredis.class));
        map.put(id, restaurant);
    }

    public RestaurantDTOredis getRestaurantCache(Long id){
        return map.get(id);
    }
    public RMap<Long, RestaurantDTOredis> getAllRestaurant(RedissonClient redissonClient){
        return redissonClient.getMap(NameCache.RESTAURANT_CACHE,
                new JsonJacksonMapCodec(Long.class, RestaurantDTOredis.class));
    }

}
