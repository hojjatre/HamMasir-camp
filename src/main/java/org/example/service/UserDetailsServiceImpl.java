package org.example.service;

import org.example.config.AppConfig;
import org.example.model.UserImp;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    final UserRepository userRepository;
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserImp user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("User Not Found with username: " + username);
        }
        return UserDetailsImpl.build(user);
    }

}
