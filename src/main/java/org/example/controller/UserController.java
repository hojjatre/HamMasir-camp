package org.example.controller;


import org.example.config.AppConfig;
import org.example.model.LoginDTO;
import org.example.model.RegistrationDTO;
import org.example.model.Role;
import org.example.model.UserImp;
import org.example.schedule.ScheduleTask;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;



    public UserController(UserService userService, AuthenticationManager authenticationManager,
                          ScheduleTask scheduleTask, AppConfig appConfig) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
        codeVerification = scheduleTask.getCodeVerification();
    }

    @PostMapping("/login")
    public ResponseEntity<Objects> login(@RequestBody LoginDTO login){
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        if(codeVerification.get(login.getUsername()) != null) {
            return new ResponseEntity(login.getUsername() + " are login. " +
                    "and your verification code is: " + codeVerification.get(login.getUsername()) , HttpStatus.OK);
        }else{
            return new ResponseEntity(login.getUsername() + " are login." , HttpStatus.OK);
        }
    }

    @PostMapping("/get-code-verification")
    public ResponseEntity<Objects> getCodeVerification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(codeVerification.get(authentication.getName()) != null){
            return new ResponseEntity(
                    "and your verification code is: " + codeVerification.get(authentication.getName())
                    , HttpStatus.OK);
        }else {
            return new ResponseEntity("You are not a OWNER.", HttpStatus.OK);
        }
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserImp>> allUser(){
        return new ResponseEntity(userService.getUserImps(), HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@RequestBody RegistrationDTO registrationDTO){
        UserImp user = new UserImp(
                registrationDTO.getName(),
                registrationDTO.getUsername(),
                registrationDTO.getEmail(),
                new BCryptPasswordEncoder().encode(registrationDTO.getPassword()),
                new HashSet<>(Collections.singleton(registrationDTO.getRoles())));

        userService.getUserImps().put(user.getEmail(), user);
        codeVerification.put(user.getEmail(), (int) ((Math.random() * (99999 - 999)) + 999));
        return new ResponseEntity<>("شما وارد شدید.", HttpStatus.OK);
    }



}
