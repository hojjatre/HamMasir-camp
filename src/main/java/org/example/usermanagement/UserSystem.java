package org.example.usermanagement;

import java.util.HashMap;
import java.util.Map;

public class UserSystem {
    private Map<String, User> users;

    public UserSystem() {
        users = new HashMap<>();
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public String getUserType(String username) {
        User user = users.get(username);
        if (user instanceof Member) {
            return "Member";
        } else if (user instanceof Librarian) {
            return "Librarian";
        } else {
            return "Unknown";
        }
    }

    public boolean isUserInSystem(String username, String password) {
        User user = users.get(username);
        if (user == null) {
            return false;
        }
        return user.authenticate(username, password);
    }

    public User getUser(String username){
        return users.get(username);
    }
}
