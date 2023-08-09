package org.example.usermanagement;

public class Member extends User{

    private int memberID;
    public Member(String firstname, String lastname, String username, String password, int memberID) {
        super(firstname, lastname, username, password);
        this.memberID = memberID;
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
