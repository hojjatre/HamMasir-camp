//package org.example.controller;
//
//import org.example.dto.SaveUserRequest;
//import org.example.model.UserImp;
//import org.example.repository.UserImpDAOImp;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/user")
//public class UserController {
//    final
//    UserImpDAOImp userImpDAOImp;
//
//    public UserController(UserImpDAOImp userImpDAOImp) {
//        this.userImpDAOImp = userImpDAOImp;
//    }
//
//    @PostMapping("/save")
//    public ResponseEntity<Object> saveUser(@RequestBody SaveUserRequest saveUserRequest){
//        UserImp userImp = new UserImp(saveUserRequest.getId(), saveUserRequest.getUsername(), saveUserRequest.getEmail());
//        userImpDAOImp.saveUser(userImp);
//        return ResponseEntity.ok(userImpDAOImp.findAll());
//    }
//
//
//}
