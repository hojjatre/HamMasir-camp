package org.example.config;

import org.example.model.*;
import org.example.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;
@Configuration
public class AppConfig implements CommandLineRunner {

    private final Map<String, Integer> codeVerification;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;



    public AppConfig(Map<String, Integer> codeVerification, UserRepository userRepository, RoleRepository roleRepository, FoodRepository foodRepository, RestaurantRepository restaurantRepository, OrderRepository orderRepository) {
        this.codeVerification = codeVerification;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Role role_user = new Role(ERole.ROLE_USER);
        roleRepository.save(role_user);

        Role role_admin = new Role(ERole.ROLE_ADMIN);
        roleRepository.save(role_admin);

        Role role_owner = new Role(ERole.ROLE_OWNER);
        roleRepository.save(role_owner);

        UserImp hojjat = new UserImp("HojjatRE", "hojjat@gmailcom",
                new BCryptPasswordEncoder().encode("hojjat123"));
        hojjat.setRoles(Collections.singleton(role_owner));
        codeVerification.put(hojjat.getUsername(), (int) ((Math.random() * (99999 - 999)) + 999));


        userRepository.save(hojjat);
        Food kabob = new Food("کباب", 4000, TypeFood.IRANIAN, "100 گرم گوشت");
//        foodRepository.save(kabob);

        Food pizza = new Food("پیتزا سبزیجات",2000, TypeFood.PIZZA, "ریحان + قارچ + جعفری");
//        foodRepository.save(pizza);

        Food mahi = new Food("ماهی", 5000, TypeFood.SEA, "100 گرم ماهی");
//        foodRepository.save(mahi);

        Food mahi2 = new Food("ماهی", 6000, TypeFood.SEA, "200 گرم ماهی");
//        foodRepository.save(mahi2);

        Food morgh_sokhari = new Food("مرغ سوخاری",2000,TypeFood.FRIED, "1 سینه + 1 ران + 1 بال");
//        foodRepository.save(morgh_sokhari);

        Food ghormeh_sabzi = new Food("قورمه سبزی", 3000, TypeFood.IRANIAN, "100 گرم گوشت + برنج 1 نفر");
//        foodRepository.save(ghormeh_sabzi);

        List<Food> foods1 = new ArrayList<>(List.of(new Food[]{kabob, pizza, mahi}));
        List<Food> foods3 = new ArrayList<>(List.of(new Food[]{kabob, kabob, mahi}));
        List<Food> foods2 = new ArrayList<>(List.of(new Food[]{mahi2, morgh_sokhari, ghormeh_sabzi}));
        Restaurant restaurant1 = new Restaurant("دربار", hojjat, "خیابان پیروزی - پیروزی 5",
                foods1);
        restaurantRepository.save(restaurant1);


        Restaurant restaurant2 = new Restaurant("پدیده", hojjat, "خیابان پیروزی - پیروزی 40",
                foods2);
        restaurantRepository.save(restaurant2);

        Order order = new Order(restaurant1, "قاشق");
        order.setFood(foods3);
        order.setTotalCost(9000);
        hojjat.getOrders().add(order);
        orderRepository.save(order);
        userRepository.save(hojjat);

    }

    public Map<String, Integer> getCodeVerification() {
        return codeVerification;
    }

}
