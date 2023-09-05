//package org.example.repository;
//
//import jakarta.annotation.Resource;
//import org.example.dao.UserImpDAO;
//import org.example.model.UserImp;
//import org.springframework.data.redis.core.HashOperations;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Repository;
//
//import java.util.Map;
//
//@Repository
//public class UserImpDAOImp implements UserImpDAO {
//
//    private final String hasrefrence = "UserImp";
//
//    @Resource(name="redisTemplate")
//    private HashOperations<String, Integer, UserImp> hashOperations;
//    @Override
//    public void saveUser(UserImp userImp) {
//        hashOperations.putIfAbsent(hasrefrence, userImp.getId(), userImp);
//    }
//
//    @Override
//    public void saveAllUser(Map<Integer, UserImp> userImpMap) {
//        hashOperations.putAll(hasrefrence, userImpMap);
//    }
//
//    @Override
//    public UserImp getUserById(Integer id) {
//        return hashOperations.get(hasrefrence, id);
//    }
//
//    @Override
//    public void updateUser(UserImp userImp) {
//        hashOperations.put(hasrefrence, userImp.getId(), userImp);
//    }
//
//    @Override
//    public Map<Integer, UserImp> findAll() {
//        return hashOperations.entries(hasrefrence);
//    }
//
//    @Override
//    public void deleteUserById(Integer id) {
//        hashOperations.delete(hasrefrence, id);
//    }
//}
