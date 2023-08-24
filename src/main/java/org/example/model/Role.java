package org.example.model;

public enum Role {
    ADMIN,
    OWNER,
    USER;

    public String getRole(){
        switch (this){
            case ADMIN -> {
                return "ROLE_ADMIN";
            }
            case OWNER -> {
                return "ROLE_OWNER";
            }
            case USER -> {
                return "ROLE_USER";
            }
            default -> {
                return null;
            }
        }
    }
}
