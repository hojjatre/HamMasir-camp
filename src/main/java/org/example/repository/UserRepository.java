package org.example.repository;

import org.example.config.AppConfig;
import org.example.model.UserImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserImp, Long> {
    UserImp findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
//    final
//    AppConfig appConfig;
//    private Map<String, UserImp> users;
//
//    public UserRepository(AppConfig appConfig) {
//        this.appConfig = appConfig;
//        this.users = appConfig.getUsers();
//    }
//
//    public boolean existsByUsername(String username){
//        for (String user_name: users.keySet()) {
//            if(user_name.equals(username)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public boolean existsByEmail(String email){
//        for (String user_name: users.keySet()) {
//            if (users.get(user_name).getEmail().equals(email)){
//                return true;
//            }
//        }
//        return false;
//    }
}
