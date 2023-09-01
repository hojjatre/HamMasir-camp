package org.example.testing;

import org.example.config.AppConfig;
import org.example.config.WebSecurityConfig;
import org.example.controller.UserController;
import org.example.dto.userimp.LoginRequest;
import org.example.dto.MakeOrderDTO;
import org.example.schedule.ScheduleTask;
import org.example.service.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import org.springframework.http.*;

import org.springframework.security.authentication.AuthenticationManager;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Execution(ExecutionMode.CONCURRENT)
public class ChangeCostAndMakeOrderTest {

    @Autowired
    AppConfig appConfig;
    @Autowired
    ScheduleTask scheduleTask;
    @Autowired
    UserDetailsServiceImpl userDetailsService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    WebSecurityConfig webSecurityConfig;
    @Autowired
    UserController userController;


    private final TestRestTemplate restTemplate = new TestRestTemplate();

    @LocalServerPort
    int randomServerPort;

    public TestView authenticate(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("HojjatRE");
        loginRequest.setPassword("hojjat123");
        HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);
        ResponseEntity<TestView> responseEntity = restTemplate.exchange(
                "http://127.0.0.1:+" + randomServerPort +"/api/user/auth/login",
                HttpMethod.POST,
                requestEntity,
                TestView.class
        );
        TestView responseBody = responseEntity.getBody();

        return responseBody;
    }
    @Test
    public void makeOrder(){
        TestView token = authenticate();
        Map<String, Integer> restaurantIdVariable = new HashMap<>();
        restaurantIdVariable.put("id", 1);
        HttpHeaders makeOrderHeader = new HttpHeaders();
        makeOrderHeader.setBearerAuth(token.getToken());
        Long[] food_id = new Long[3];
        food_id[0] = 1L;
        food_id[1] = 1L;
        food_id[2] = 1L;
        MakeOrderDTO makeOrderDTO = new MakeOrderDTO(food_id, "قاشق");
        HttpEntity<MakeOrderDTO> orderRequest = new HttpEntity<>(makeOrderDTO, makeOrderHeader);
        ResponseEntity<OrderTestView> orderResponse = restTemplate.exchange(
                "http://localhost:"+ randomServerPort + "/api/order/make-order/{id}",
                HttpMethod.POST,
                orderRequest,
                OrderTestView.class,
                restaurantIdVariable
        );
        OrderTestView orderTestView = orderResponse.getBody();
        System.out.println("ORDER: " + orderTestView.getFoods() + " - " + orderTestView.getTotalCost());
    }

    @Test
    public void changeCostFood(){
        TestView responseView = authenticate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(responseView.getToken());
        int code = appConfig.getCodeVerification().get(responseView.getUsername());
        Map<String, Integer> pathVariable = new HashMap<>();
        pathVariable.put("foodID", 1);
        pathVariable.put("restaurantID", 1);
        int foodID = 1;
        int restaurantID = 1;
        Map<String, Integer> queryParams = new HashMap<>();
        queryParams.put("inputCost", 454545);
        queryParams.put("code", code);
        int inputCode = 454545;
        ResponseEntity<RestaurantTestView> responseEntity = restTemplate.exchange(
                "http://localhost:"+ randomServerPort + "/api/restaurant/change-cost-food/{foodID}/{restaurantID}?inputCost={inputCost}&code={code}",
                HttpMethod.POST,
                new HttpEntity<>(null, headers),
                RestaurantTestView.class,
                foodID,
                restaurantID,
                inputCode,
                code
        );

        RestaurantTestView restaurantTestView = responseEntity.getBody();
        System.out.println(restaurantTestView.getFood());
    }

    @Test
    public void testThread() throws InterruptedException {
        Thread userOrderThread = new Thread(this::makeOrder);
        Thread ownerChangeCostThread = new Thread(this::changeCostFood);



        ownerChangeCostThread.start();
        userOrderThread.start();

        userOrderThread.join();
        ownerChangeCostThread.join();


    }
}
