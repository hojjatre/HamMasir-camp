//package org.example.testing;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.example.config.AppConfig;
//import org.example.config.SecurityConfig;
//import org.example.controller.UserController;
//import org.example.model.Role;
//import org.example.schedule.ScheduleTask;
//import org.example.service.UserService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//@Import({SecurityConfig.class, UserService.class, ScheduleTask.class, AppConfig.class})
//@WebMvcTest(controllers = UserController.class)
//public class UserControllerTest {
////    @MockBean
////    private UserService userService;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//
////    private Authentication authentication = userService.getAuthentication();;
//
//    @Test
//    public void allMethod(){
//        RegistrationDTO hojjat = new RegistrationDTO("Hojjat Rezaei", "HojjatRE", "hojjat@gmail.com",
//                new BCryptPasswordEncoder().encode("hojjat123"),
//                Role.OWNER.getRole());
//
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                "HojjatRE", "hojjat123"));
//
//
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/user/registration")
//                    .content(objectMapper.writeValueAsString(hojjat))
//                    .contentType("application/json")).andExpect(MockMvcResultMatchers.status().isOk());
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/user/get-code-verification")
//                    .with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
//            ).andExpect(MockMvcResultMatchers.status().isOk());
//
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void registerUserTest() {
//
//        RegistrationDTO hojjat = new RegistrationDTO("Hojjat Rezaei", "HojjatRE", "hojjat@gmail.com",
//                new BCryptPasswordEncoder().encode("hojjat123"),
//                Role.ADMIN.getRole());
//
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/user/registration")
//                    .content(objectMapper.writeValueAsString(hojjat))
//                    .contentType("application/json")).andExpect(MockMvcResultMatchers.status().isOk());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void loginUserTest(){
//        LoginDTO loginDTO1 = new LoginDTO("HojjatRE", "hojjat123");
//        LoginDTO loginDTO2 = new LoginDTO("HojjatRE", "wrongpass");
//
//        try {
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                    .contentType("application/json")
//                    .content(objectMapper.writeValueAsString(loginDTO1))).andExpect(
//                            MockMvcResultMatchers.status().isOk());
//
//            mockMvc.perform(MockMvcRequestBuilders.post("/api/user/login")
//                    .contentType("application/json")
//                    .content(objectMapper.writeValueAsString(loginDTO2))).andExpect(
//                    MockMvcResultMatchers.status().isUnauthorized());
//        }catch (Exception e){
//            throw new RuntimeException(e);
//        }
//    }
//
//    @Test
//    public void getVerificationCode(){
//        try {
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    "Ali_h", "ali123"));
//
//            Authentication authentication2 = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                    "Ali_h", "wrongpass"));
//            try {
//                mockMvc.perform(MockMvcRequestBuilders.post("/api/user/get-code-verification")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
//                ).andExpect(MockMvcResultMatchers.status().isOk());
//                mockMvc.perform(MockMvcRequestBuilders.post("/api/user/get-code-verification")
//                        .with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
//                ).andExpect(MockMvcResultMatchers.status().isForbidden());
//
//
//            }catch (Exception e){
//                throw new RuntimeException(e);
//            }
//        }catch (BadCredentialsException badCredentialsException){
//            System.out.println("Your pass is not correct.");
//        }
//
//
//    }
//}
