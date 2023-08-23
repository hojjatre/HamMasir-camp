package org.example.controller;


import org.example.model.LoginDTO;
import org.example.model.UserImp;
import org.example.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    Authentication authentication;



    public UserController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        authentication = userService.getAuthentication();
    }

    @PostMapping("/login")
    public ResponseEntity<Objects> login(@RequestBody LoginDTO login){
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                login.getUsername(), login.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity(login.getUsername() + " are login." + "", HttpStatus.OK);
    }

    @GetMapping("/all-users")
    public ResponseEntity<List<UserImp>> allUser(){
        return new ResponseEntity(userService.getUserImps(), HttpStatus.OK);
    }



}
