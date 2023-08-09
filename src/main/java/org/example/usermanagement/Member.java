package org.example.usermanagement;

public class Member extends User{

    private int memberID;
    public Member(String firstname, String lastname, String username, String password, int memberID) {
        super(firstname, lastname, username, password);
        this.memberID = memberID;
    }

    public int getMemberID() {
        return memberID;
    }
}
