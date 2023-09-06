package org.example.schedule;

import org.example.cachemanager.NameCache;
import org.example.cachemanager.RestaurantCache;
import org.example.config.AppConfig;
import org.example.dto.FoodDTOredis;
import org.example.dto.FoodMapperRedis;
import org.example.dto.restaurant.RestaurantDTOredis;
import org.example.model.Food;
import org.example.model.Restaurant;
import org.example.repository.FoodRepository;
import org.example.repository.RestaurantRepository;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ScheduleTask {

    private final Map<String, Integer> codeVerification;
    private final RestaurantCache restaurantCache;
    private FoodMapperRedis foodMapperRedis;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    public ScheduleTask(AppConfig appConfig, RestaurantCache restaurantCache, RestaurantRepository restaurantRepository, FoodRepository foodRepository) {
        this.codeVerification = appConfig.getCodeVerification();
        this.restaurantCache = restaurantCache;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
    }

    @Scheduled(fixedRate = 50000)
    public void performTask() {
        for (String userImp:codeVerification.keySet()) {
            codeVerification.put(userImp, getRandomNumber(999,99999));
        }
    }

    @Scheduled(fixedRate = 50000)
    public void loadFoodToDatabase(){
        foodMapperRedis = FoodMapperRedis.instanse;
        RMap<RestaurantDTOredis, RList<FoodDTOredis>> restaurantMap = restaurantCache.getRedisConfig().redissionClient()
                .getMap(NameCache.RESTAURANT_CACHE);
        for (RestaurantDTOredis res:restaurantMap.keySet()) {
            RList<FoodDTOredis> foodRedis = restaurantCache.getRedisConfig().redissionClient()
                    .getList("restaurantID:"+res.getRestaurantID());
            for (int i = 0; i < foodRedis.size(); i++) {
                Food food = foodMapperRedis.dtoTOEntity(foodRedis.get(i));
                food.setRestaurant(restaurantRepository.findByRestaurantID(res.getRestaurantID(),
                        Restaurant.class));
                foodRepository.save(food);
            }
        }
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public Map<String, Integer> getCodeVerification() {
        return codeVerification;
    }
}
