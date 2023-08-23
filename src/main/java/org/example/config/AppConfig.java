package org.example.config;

import org.example.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
@Configuration
public class AppConfig implements CommandLineRunner {

    private final List<Restaurant> restaurants;
    private final List<UserImp> userImps;
    private final List<Order> orders;
    private final List<Food> foods;



    public AppConfig(List<Restaurant> restaurants, List<UserImp> userImps, List<Order> orders, List<Food> foods) {
        this.restaurants = restaurants;
        this.userImps = userImps;
        this.orders = orders;
        this.foods = foods;
    }

    @Override
    public void run(String... args) throws Exception {


        UserImp hojjat = new UserImp("Hojjat Rezaei", "HojjatRE", "hojjat@gmail.com",
                new BCryptPasswordEncoder().encode("hojjat123"),
                new HashSet<>(Collections.singleton(Role.ADMIN.getRole())));

        userImps.add(hojjat);

        UserImp hamed = new UserImp("Hamed Rezaei", "Hamed_re", "hamed@gmail.com",
                new BCryptPasswordEncoder().encode("hamed123"),
                new HashSet<>(Collections.singleton(Role.USER.getRole())));

        userImps.add(hamed);

        UserImp ali = new UserImp("Ali Hasani", "Ali_h", "ali@gmail.com",
                new BCryptPasswordEncoder().encode("ali123"),
                new HashSet<>(Collections.singleton(Role.OWNER.getRole())));

        userImps.add(ali);


        Food kabob = new Food("کباب", TypeFood.IRANIAN);
        foods.add(kabob);
        Food pizza = new Food("پیتزا", TypeFood.PIZZA);
        foods.add(pizza);
        Food mahi = new Food("ماهی", TypeFood.SEA);
        foods.add(mahi);
        Food morgh_sokhari = new Food("مرغ سوخاری", TypeFood.FRIED);
        foods.add(morgh_sokhari);
        Food ghormeh_sabzi = new Food("قورمه سبزی", TypeFood.IRANIAN);
        foods.add(ghormeh_sabzi);

        Map<Food, Integer> cost1 = Map.ofEntries(
                Map.entry(kabob, 1000),
                Map.entry(pizza, 2000),
                Map.entry(mahi, 3000)
        );

        Map<Food, Integer> cost2 = Map.ofEntries(
                Map.entry(kabob, 2000),
                Map.entry(morgh_sokhari, 5000),
                Map.entry(ghormeh_sabzi, 3000)
        );

        Restaurant restaurant1 = new Restaurant("دربار", hamed, "خیابان پیروزی - پیروزی 5",
                cost1);
        restaurants.add(restaurant1);

        Restaurant restaurant2 = new Restaurant("پدیده", hamed, "خیابان پیروزی - پیروزی 40",
                cost2);
        restaurants.add(restaurant2);


//        Order order1 = new Order(restaurant1, Map.ofEntries(
//                Map.entry(kabob, 1000),
//                Map.entry(kabob, 1000)
//        ), "");
//        hamed.addOrder(order1);
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public List<UserImp> getUserImps() {
        return userImps;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<Food> getFoods() {
        return foods;
    }
}
