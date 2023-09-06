package org.example.cachemanager;

import org.example.config.RedisConfig;
import org.example.dto.FoodDTOredis;
import org.example.dto.FoodMapperRedis;
import org.example.dto.restaurant.RestaurantDTOredis;
import org.example.dto.restaurant.RestaurantView;
import org.example.model.Food;
import org.example.model.Restaurant;
import org.example.repository.FoodRepository;
import org.example.repository.RestaurantRepository;
import org.redisson.api.RList;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.Codec;
import org.redisson.client.codec.JsonJacksonMapCodec;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.codec.SerializationCodec;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RestaurantCache {
    RMap<RestaurantDTOredis, RList<FoodDTOredis>> map;

    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    private final RedisConfig redisConfig;

    private FoodMapperRedis foodMapperRedis;

    public RestaurantCache(RestaurantRepository restaurantRepository, FoodRepository foodRepository, RedisConfig redisConfig) {
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.redisConfig = redisConfig;
    }


    public RMap<RestaurantDTOredis, RList<FoodDTOredis>> loadDataToCache(){
        foodMapperRedis = FoodMapperRedis.instanse;
        boolean first = false;



        List<RestaurantView> allRestaurant = restaurantRepository.findAllRestaurant();
//        List<FoodView> allFood = foodRepository.allFoods();
        List<Food> foods = foodRepository.findAll();
        List<FoodDTOredis> foodDTOredis = new ArrayList<>();
        for (int i = 0; i < foods.size(); i++) {
            foodDTOredis.add(foodMapperRedis.entityToDTO(foods.get(i)));
        }
        RList<FoodDTOredis> temp = null;
        List<FoodDTOredis> foodDTOredisList = new ArrayList<>();
        for (int i = 0; i < allRestaurant.size(); i++) {
            if (!first){
                temp = redisConfig.redissionClient().getList("restaurantID:" + (i+1));

                map = redisConfig.redissionClient().getMap(NameCache.RESTAURANT_CACHE);
                first = true;
            }
            for (int j = 0; j < allRestaurant.get(i).getFoods().size(); j++) {
                for (int k = 0; k < foodDTOredis.size(); k++) {
                    if (allRestaurant.get(i).getFoods().get(j).getFoodID() == foodDTOredis.get(k).getFoodID()){
                        temp.add(foodDTOredis.get(k));
                    }
                }
            }
            map.put(restaurantRepository.findRestaurantForRedis(allRestaurant.get(i).getRestaurantID()),
                    temp);
            first = false;
        }

        return map;
    }

    public void addRestaurant(RestaurantDTOredis restaurantDTOredis, List<Food> food){
        foodMapperRedis = FoodMapperRedis.instanse;
        RList<FoodDTOredis> temp = redisConfig.redissionClient().getList("restaurantID:" + restaurantDTOredis.getRestaurantID());

        map = redisConfig.redissionClient().getMap(NameCache.RESTAURANT_CACHE);
        for (int i = 0; i < food.size(); i++) {
            temp.add(foodMapperRedis.entityToDTO(food.get(i)));
        }
        map.put(restaurantDTOredis, temp);
    }

    public void removeRestaurant(RestaurantDTOredis restaurantDTOredis){
        foodMapperRedis = FoodMapperRedis.instanse;
        RedissonClient redissonClient = redisConfig.redissionClient();
        map = redissonClient.getMap(NameCache.RESTAURANT_CACHE);
        map.fastRemove(restaurantDTOredis);
        RList<FoodDTOredis> foodDTOredis = redissonClient.getList("restaurantID:"+restaurantDTOredis.getRestaurantID());
        foodDTOredis.clear();
    }

    public RestaurantDTOredis getRestaurant(Long id){
        System.out.println("-------" + id);
        RedissonClient redissonClient = redisConfig.redissionClient();
        RList<FoodDTOredis> temp = redisConfig.redissionClient().getList("restaurantID:"+(id-1),
                new JsonJacksonCodec(FoodDTOredis.class.getClassLoader()));

        map = redissonClient.getMap(NameCache.RESTAURANT_CACHE);
        System.out.println("-------" + id);
        for (RestaurantDTOredis res:map.keySet()) {
            if (res.getRestaurantID() == id){
                System.out.println("-------" + id);
                return res;
            }
        }
        return null;
    }

    public FoodDTOredis changeCostFood(Long foodID,int inoutCost, RestaurantDTOredis restaurantDTOredis){
        if(map.containsKey(restaurantDTOredis)){
            foodMapperRedis = FoodMapperRedis.instanse;
            RedissonClient redissonClient = redisConfig.redissionClient();
            RList<FoodDTOredis> foodDTOredis = redissonClient.getList(
                    "restaurantID:"+restaurantDTOredis.getRestaurantID()
            );
            FoodDTOredis inputFood = null;
            int changedFood = 0;
            for (int i = 0; i < foodDTOredis.size(); i++) {
                if(foodDTOredis.get(i).getFoodID() == foodID){
                    foodDTOredis.get(i).setCost(inoutCost);
                    inputFood = foodDTOredis.get(i);
                    changedFood = i;

                }
            }
            inputFood.setCost(inoutCost);
            foodDTOredis.remove(changedFood);
            foodDTOredis.add(inputFood);
            return inputFood;
        }
        return null;
    }

    public RMap<RestaurantDTOredis, RList<FoodDTOredis>> getMap() {
        return map;
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }
}
