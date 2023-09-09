package org.example.service;

//import jakarta.transaction.Transactional;
import org.springframework.transaction.annotation.Transactional;
import org.example.dto.restaurant.RestaurantDTO;
import org.example.dto.restaurant.RestaurantView;
import org.example.model.*;
import org.example.repository.FoodRepository;
import org.example.repository.RestaurantRepository;
import org.example.repository.UserRepository;
import org.example.schedule.ScheduleTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RestaurantService {


    private final Map<String, Integer> codeVerification;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private Restaurant selectRestaurant;

    public RestaurantService(ScheduleTask scheduleTask, RestaurantRepository restaurantRepository,
                             FoodRepository foodRepository, UserRepository userRepository) {
        codeVerification = scheduleTask.getCodeVerification();
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }



    public ResponseEntity<Object> addRestaurant(Authentication authentication, int code, RestaurantDTO restaurantDTO){
        if (codeVerification.get(authentication.getName()) != code) {
            return new ResponseEntity<>("Your verification code is not correct.", HttpStatus.FORBIDDEN);
        }
        if (restaurantDTO.getNameFood().length != restaurantDTO.getNameFood().length ||
                restaurantDTO.getNameFood().length != restaurantDTO.getCost().length){
            return new ResponseEntity<>("شما باید تمام موارد غذا را پر کنید، دوباره تلاش کنید.", HttpStatus.FORBIDDEN);
        }
        // new Food
        List<Food> foodList = IntStream.range(0, restaurantDTO.getNameFood().length)
                .mapToObj(i -> new Food(restaurantDTO.getNameFood()[i],
                        TypeFood.valueOf(restaurantDTO.getTypeFood()[i]),
                        restaurantDTO.getCost()[i],
                        restaurantDTO.getDescription()[i]))
                .collect(Collectors.toList());
        foodRepository.saveAll(foodList);

        UserImp userImp = userRepository.findByUsername(authentication.getName());

        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                userImp, restaurantDTO.getLocation(), foodList);

        restaurantRepository.save(restaurant);
        RestaurantView restaurantView = restaurantRepository.findRestaurant(restaurant.getRestaurantID());
        return new ResponseEntity<>(restaurantView, HttpStatus.OK);
    }

    public ResponseEntity<Object> removeRestaurant(Authentication authentication, Long id, int code){
        UserImp userImp = userRepository.findByUsername(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, id, userImp);
        if (check != null) {
            return check;
        }

        restaurantRepository.delete(selectRestaurant);
        return new ResponseEntity<>("رستوران شما با موفقیت حذف شد.", HttpStatus.OK);
    }


    public ResponseEntity<Object> changeCostFood(Authentication authentication, Long restaurantID,
                                                 Long foodID ,int code, Integer inputCost){
        UserImp userImp = userRepository.findByUsername(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }

        return checkFoodAndChange(selectRestaurant, foodID, inputCost);

    }

    public ResponseEntity<Object> removeFood(Authentication authentication, Long restaurantID,
                                             Long foodID ,int code){
        UserImp userImp = userRepository.findByUsername(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }
        return checkFoodAndRemove(selectRestaurant, foodID);
    }

    public ResponseEntity<Object> addFood(Authentication authentication, Long restaurantID,
                                             Food food ,int code){
        UserImp userImp = userRepository.findByUsername(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }

        if (selectRestaurant != null){
            food.setRestaurant(selectRestaurant);
            selectRestaurant.getFoods().add(food);
            restaurantRepository.save(selectRestaurant);
        }

        return new ResponseEntity<>(restaurantRepository.findByRestaurantID(restaurantID, RestaurantView.class), HttpStatus.OK);
    }


    public ResponseEntity<Object> checkOwner(Authentication authentication, int code, Long id,
                                             UserImp userImp){
        if (codeVerification.get(authentication.getName()) != code) {
            return new ResponseEntity<>("کد تایید شما درست نیست.", HttpStatus.FORBIDDEN);
        }
        selectRestaurant = restaurantRepository.findByRestaurantIDAndOwner(id, userImp);


        if(selectRestaurant == null){
            return new ResponseEntity<>("شما مالک رستوران نیستید.", HttpStatus.NOT_FOUND);
        }

        return null;
    }
    @Transactional
    public ResponseEntity<Object> checkFoodAndChange(Restaurant restaurant, Long foodID, Integer inputCost){
        Food food = foodRepository.findByFoodID(foodID);
        restaurant.getFoods().stream()
                .filter(f -> f.getFoodID() == foodID)
                .findFirst()
                .orElse(null);
        food.setCost(inputCost);
        restaurantRepository.save(restaurant);
        foodRepository.save(food);
        return new ResponseEntity<>(restaurantRepository.findByRestaurantID(restaurant.getRestaurantID(),
                RestaurantView.class), HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<Object> checkFoodAndRemove(Restaurant restaurant, Long foodID){
        boolean changed = false;
        Food removedFood = foodRepository.findByFoodID(foodID);
        if (removedFood != null){
            changed = true;
        }
        if(changed){
            foodRepository.deleteById((long) foodID);
        }

        if (!changed){
            return new ResponseEntity<>("غذای مورد نظر یافت نشد.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(restaurantRepository.findByRestaurantID(restaurant.getRestaurantID(), RestaurantView.class), HttpStatus.OK);
    }


}
