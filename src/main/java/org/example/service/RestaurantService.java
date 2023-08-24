package org.example.service;

import org.example.config.AppConfig;
import org.example.model.*;
import org.example.schedule.ScheduleTask;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class RestaurantService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;

    private List<Restaurant> restaurants = new ArrayList<>();

    private List<Food> foods;

    public RestaurantService(UserService userService, AuthenticationManager authenticationManager,
                             ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
        codeVerification = scheduleTask.getCodeVerification();
        restaurants = appConfig.getRestaurants();
        foods = appConfig.getFoods();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Map<String ,UserImp> UserService() {
        return userService.getUserImps();
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
                        restaurantDTO.getDescription()[i]))
                .collect(Collectors.toList());
        foods.addAll(foodList);

        Map<Food, Integer> costs = IntStream.range(0, foodList.size())
                .boxed()
                .collect(Collectors.toMap(foodList::get, i -> restaurantDTO.getCost()[i]));

        UserImp userImp = userService.getUserImps().get(authentication.getName());

        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                userImp, restaurantDTO.getLocation(), costs);
        foods.stream().forEach(food -> System.out.println(food.getName() + ", " + food.getTypeFood()));

        restaurants.add(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    public ResponseEntity<Object> removeRestaurant(Authentication authentication, int id, int code){
        if (codeVerification.get(authentication.getName()) != code) {
            return new ResponseEntity<>("کد تایید شما درست نیست.", HttpStatus.FORBIDDEN);
        }

        UserImp userImp = userService.getUserImps().get(authentication.getName());

        List<Restaurant> selectRestaurant = restaurants.stream().filter(
                restaurant -> restaurant.getId() == id && restaurant.getOwner() == userImp
        ).collect(Collectors.toList());

        if(selectRestaurant.isEmpty()){
            return new ResponseEntity<>("شما مالک رستوران نیستید.", HttpStatus.NOT_FOUND);
        }
        restaurants.remove(selectRestaurant.get(0));
        return new ResponseEntity<>("رستوران شما با موفقیت حذف شد.", HttpStatus.OK);
    }


}
