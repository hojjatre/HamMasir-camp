package org.example.config;

import org.example.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
@Configuration
public class AppConfig implements CommandLineRunner {

    private final List<Restaurant> restaurants;
    Map<String , UserImp> users = new HashMap<>(); // username, user
    Map<String , Role> roles = new HashMap<>(); // role name, role
    private final List<Order> orders;
    private final List<Food> foods;

    private final Map<String, Integer> codeVerification;

    public Map<String, Integer> getCodeVerification() {
        return codeVerification;
    }

    public AppConfig(List<Restaurant> restaurants, List<Order> orders, List<Food> foods, Map<String, Integer> codeVerification) {
        this.restaurants = restaurants;
        this.orders = orders;
        this.foods = foods;
        this.codeVerification = codeVerification;
    }

    public Map<String, UserImp> getUsers() {
        return users;
    }

    public Map<String, Role> getRoles() {
        return roles;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role_user = new Role(ERole.ROLE_USER);
        roles.put(ERole.ROLE_USER.name(), role_user);
        Role role_admin = new Role(ERole.ROLE_ADMIN);
        roles.put(ERole.ROLE_ADMIN.name(), role_admin);
        Role role_owner = new Role(ERole.ROLE_OWNER);
        roles.put(ERole.ROLE_OWNER.name(), role_owner);
        UserImp hojjat = new UserImp("HojjatRE", "hojjat@gmailcom",
                new BCryptPasswordEncoder().encode("hojjat123"));
        hojjat.setRoles(Collections.singleton(role_owner));
        codeVerification.put(hojjat.getUsername(), (int) ((Math.random() * (99999 - 999)) + 999));
        users.put(hojjat.getUsername(), hojjat);


        Food kabob = new Food("کباب", 4000, TypeFood.IRANIAN, "100 گرم گوشت");
        foods.add(kabob);
        Food pizza = new Food("پیتزا سبزیجات",2000, TypeFood.PIZZA, "ریحان + قارچ + جعفری");
        foods.add(pizza);
        Food mahi = new Food("ماهی", 5000, TypeFood.SEA, "100 گرم ماهی");
        foods.add(mahi);
        Food mahi2 = new Food("ماهی", 6000, TypeFood.SEA, "200 گرم ماهی");
        foods.add(mahi2);
        Food morgh_sokhari = new Food("مرغ سوخاری",2000,TypeFood.FRIED, "1 سینه + 1 ران + 1 بال");
        foods.add(morgh_sokhari);
        Food ghormeh_sabzi = new Food("قورمه سبزی", 3000, TypeFood.IRANIAN, "100 گرم گوشت + برنج 1 نفر");
        foods.add(ghormeh_sabzi);

        List<Food> foods1 = new ArrayList<>(List.of(new Food[]{kabob, pizza, mahi}));
        List<Food> foods2 = new ArrayList<>(List.of(new Food[]{mahi2, morgh_sokhari, ghormeh_sabzi}));
        Restaurant restaurant1 = new Restaurant("دربار", hojjat, "خیابان پیروزی - پیروزی 5",
                foods1);
        restaurants.add(restaurant1);

        Restaurant restaurant2 = new Restaurant("پدیده", hojjat, "خیابان پیروزی - پیروزی 40",
                foods2);
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


    public List<Order> getOrders() {
        return orders;
    }

    public List<Food> getFoods() {
        return foods;
    }
}
