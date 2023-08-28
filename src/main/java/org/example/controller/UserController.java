package org.example.controller;


import jakarta.validation.Valid;
import org.example.config.AppConfig;
import org.example.dto.*;
import org.example.model.ERole;
import org.example.model.Role;
import org.example.model.UserImp;
import org.example.repository.UserRepository;
import org.example.schedule.ScheduleTask;
import org.example.security.JwtUtilities;
import org.example.service.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/auth")
public class UserController {
    private final AuthenticationManager authenticationManager;

    Authentication authentication;

    private final Map<String, Integer> codeVerification;

    final AppConfig appConfig;

    final PasswordEncoder encoder;

    final JwtUtilities jwtUtilities;

    private Map<String, UserImp> users;
    final
    UserRepository userRepository;



    public UserController(AuthenticationManager authenticationManager,
                          ScheduleTask scheduleTask, AppConfig appConfig,
                          PasswordEncoder encoder, JwtUtilities jwtUtilities, Map<String, UserImp> users, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.appConfig = appConfig;
        this.encoder = encoder;
        this.jwtUtilities = jwtUtilities;
        this.users = appConfig.getUsers();
        codeVerification = scheduleTask.getCodeVerification();
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtilities.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new UserInfoResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/get-code-verification")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<Objects> getCodeVerification(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if(codeVerification.get(authentication.getName()) != null){
            return new ResponseEntity(
                    "and your verification code is: " + codeVerification.get(authentication.getName())
                    , HttpStatus.OK);
        }else {
            return new ResponseEntity("You are not a OWNER.", HttpStatus.OK);
        }
    }

    @GetMapping("/all-users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserImp>> allUser(){
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<Object> registration(@Valid @RequestBody RegistrationRequest registrationRequest){
        if (userRepository.existsByUsername(registrationRequest.getUsername())){
            return new ResponseEntity<>("Username is taken", HttpStatus.BAD_REQUEST);
        }
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            return new ResponseEntity<>("Email is taken", HttpStatus.BAD_REQUEST);
        }

        UserImp user = new UserImp(registrationRequest.getUsername(),
                registrationRequest.getEmail(),
                encoder.encode(registrationRequest.getPassword()));

        Set<String> strRoles = registrationRequest.getRole();
        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role ->{
            switch (role){
                case "admin":
                    roles.add(appConfig.getRoles().get(ERole.ROLE_ADMIN.name()));
                    break;
                case "owner":
                    roles.add(appConfig.getRoles().get(ERole.ROLE_OWNER.name()));
                    codeVerification.put(registrationRequest.getUsername(), (int) ((Math.random() * (99999 - 999)) + 999));
                    break;
                default:
                    roles.add(appConfig.getRoles().get(ERole.ROLE_USER.name()));
            }
        });

        user.setRoles(roles);
        users.put(registrationRequest.getUsername(), user);
        return ResponseEntity.ok(registrationRequest.getUsername() + " registered successfully!");
    }
}
