package org.example.model;

public class Role {
    private int id;
    private ERole name;

    public Role() {

    }

    public Role(ERole name) {
        this.name = name;
    }

    public ERole getName() {
        return name;
    }
}
