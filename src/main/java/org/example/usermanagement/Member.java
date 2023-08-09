package org.example.usermanagement;

public class Member extends User{

    private static int id = 0;
    private int memberID;
    public Member(String firstname, String lastname, String username, String password) {
        super(firstname, lastname, username, password);
        id = id + 1;
        this.memberID = id;
    }

    @Override
    public boolean authenticate(String inputUsername, String inputPassword) {
        return getUsername().equals(inputUsername) && getPassword().equals(inputPassword);
    }

    @Override
    public String displayUserInfo() {
        return "Member{" +
                "firstname='" + getFirstname() + '\'' +
                ", lastname='" + getLastname() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", member id='" + getMemberID() + '\'' +
                '}';
    }

    public int getMemberID() {
        return memberID;
    }
}
