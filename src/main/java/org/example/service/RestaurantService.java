package org.example.service;

import org.example.config.AppConfig;
import org.example.dto.RestaurantDTO;
import org.example.model.*;
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

    private final UserDetailsServiceImpl userDetailsService;

    private Authentication authentication;

    private final Map<String, Integer> codeVerification;

    private List<Restaurant> restaurants = new ArrayList<>();

    private List<Food> foods;
    private List<Restaurant> selectRestaurant;

    public RestaurantService(UserDetailsServiceImpl userDetailsService,
                             ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userDetailsService = userDetailsService;
        codeVerification = scheduleTask.getCodeVerification();
        restaurants = appConfig.getRestaurants();
        foods = appConfig.getFoods();
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Map<String ,UserImp> UserService() {
        return userDetailsService.getUsers();
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

        for (int i = 0; i < foods.size(); i++) {
            if (costs.get(foods.get(i)) != null){
                foods.get(i).setCost(costs.get(foods.get(i)));
            }
        }

        UserImp userImp = userDetailsService.getUsers().get(authentication.getName());

        Restaurant restaurant = new Restaurant(restaurantDTO.getName(),
                userImp, restaurantDTO.getLocation(), foods);
        foods.stream().forEach(food -> System.out.println(food.getName() + ", " + food.getTypeFood()));

        restaurants.add(restaurant);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    public ResponseEntity<Object> removeRestaurant(Authentication authentication, int id, int code){
        UserImp userImp = userDetailsService.getUsers().get(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, id, userImp);
        if (check != null) {
            return check;
        }

        restaurants.remove(selectRestaurant.get(0));
        return new ResponseEntity<>("رستوران شما با موفقیت حذف شد.", HttpStatus.OK);
    }

    public ResponseEntity<Object> changeCostFood(Authentication authentication, int restaurantID,
                                                 int foodID ,int code, Integer inputCost){
        UserImp userImp = userDetailsService.getUsers().get(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }

        check = checkFoodAndChange(selectRestaurant.get(0), foodID, inputCost);
        if (check != null){
            return check;
        }

        return new ResponseEntity<>(selectRestaurant.get(0), HttpStatus.OK);

    }

    public ResponseEntity<Object> removeFood(Authentication authentication, int restaurantID,
                                             int foodID ,int code){
        UserImp userImp = userDetailsService.getUsers().get(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }
        check = checkFoodAndRemove(selectRestaurant.get(0), foodID);
        if (check != null){
            return check;
        }

        return new ResponseEntity<>(selectRestaurant.get(0), HttpStatus.OK);
    }

    public ResponseEntity<Object> addFood(Authentication authentication, int restaurantID,
                                             Food food ,int code){
        UserImp userImp = userDetailsService.getUsers().get(authentication.getName());

        ResponseEntity<Object> check = checkOwner(authentication, code, restaurantID, userImp);
        if (check != null) {
            return check;
        }

        selectRestaurant.get(0).addFood(food);

        return new ResponseEntity<>(selectRestaurant.get(0), HttpStatus.OK);
    }


    public ResponseEntity<Object> checkOwner(Authentication authentication, int code, int id,
                                             UserImp userImp){
        if (codeVerification.get(authentication.getName()) != code) {
            return new ResponseEntity<>("کد تایید شما درست نیست.", HttpStatus.FORBIDDEN);
        }
        selectRestaurant = restaurants.stream().filter(
                restaurant -> restaurant.getId() == id && restaurant.getOwner() == userImp
        ).collect(Collectors.toList());


        if(selectRestaurant.isEmpty()){
            return new ResponseEntity<>("شما مالک رستوران نیستید.", HttpStatus.NOT_FOUND);
        }

        return null;
    }

    public ResponseEntity<Object> checkFoodAndChange(Restaurant restaurant, int foodID, Integer inputCost){
        boolean changed = false;
//        Map<Food, Integer> cost = new HashMap<>(restaurant.getCost());
        for (Food food:restaurant.getFoods()) {
            if (food.getId() == foodID) {
                food.setCost(inputCost);
//                cost.put(food, inputCost);
                changed = true;
            }
        }
        if (!changed){
            return new ResponseEntity<>("غذای مورد نظر یافت نشد.", HttpStatus.NOT_FOUND);
        }
//        restaurant.setCost(cost);
        return null;
    }

    public ResponseEntity<Object> checkFoodAndRemove(Restaurant restaurant, int foodID){
        boolean changed = false;
        Food removedFood = null;
        for (Restaurant res:restaurants) {
            for (Food food:res.getFoods()){
                if (food.getId() == foodID) {
                    removedFood = food;
                    changed = true;
                }
            }
        }
        if(changed){
            restaurant.removeFood(removedFood);
        }

        if (!changed){
            return new ResponseEntity<>("غذای مورد نظر یافت نشد.", HttpStatus.NOT_FOUND);
        }
        return null;
    }


}
