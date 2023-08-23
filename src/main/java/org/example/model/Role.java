package org.example.model;

public enum Role {
    ADMIN,
    OWNER,
    USER;

    public String getRole(){
        switch (this){
            case ADMIN -> {
                return "ADMIN";
            }
            case OWNER -> {
                return "OWNER";
            }
            case USER -> {
                return "USER";
            }
            default -> {
                return null;
            }
        }
    }
}
