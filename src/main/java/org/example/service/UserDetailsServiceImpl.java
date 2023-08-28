package org.example.service;

import org.example.config.AppConfig;
import org.example.model.UserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final AppConfig appConfig;
    private Map<String, UserImp> users;
    public UserDetailsServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.users = appConfig.getUsers();
    }

    public Map<String, UserImp> getUsers() {
        return users;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserImp user = null;
        for (String username_:appConfig.getUsers().keySet()) {
            if(username_.equals(username)){
                user = appConfig.getUsers().get(username);
            }
        }
        return UserDetailsImpl.build(user);
    }

}
