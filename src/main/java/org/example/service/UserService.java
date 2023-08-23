package org.example.service;

import org.example.config.AppConfig;
import org.example.model.UserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private Map<String ,UserImp> userImps = new HashMap<>();

    private Authentication authentication;

    public Authentication getAuthentication() {
        return authentication;
    }

    @Autowired
    public UserService(AppConfig appConfig) {
        this.userImps = appConfig.getUserImps();
    }

    public Map<String ,UserImp> getUserImps() {
        return userImps;
    }



    @Override
    public UserDetails loadUserByUsername(String username) {
        UserImp userImp = null;
        for (UserImp user:userImps.values()) {
            if(user.getUsername().equals(username)){
                userImp = user;
            }
        }
        Set<GrantedAuthority> authorities = userImp
                .getRoles()
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.toString())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(userImp.getEmail(),
                userImp.getPassword(),
                authorities);
    }
}
