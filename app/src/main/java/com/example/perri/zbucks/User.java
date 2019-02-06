package com.example.perri.zbucks;

import com.google.gson.Gson;

public class User extends Item{
    private long id;
    private String firstName;
    private String lastName;
    private UserType userType;
    private int balance;

    // Setters
    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    // Getters
    public long getId() {
        return this.id;
    }

    public String getFirstName() {
        if (this.firstName == null)
        {
            return "";
        }
        return this.firstName;
    }

    public String getLastName() {
        if (this.lastName == null)
        {
            return "";
        }
        return this.lastName;
    }

    public UserType getUserType() {
        if (this.userType == null)
        {
            return UserType.none;
        }
        return this.userType;
    }

    public int getBalance() {
        return this.balance;
    }

    @Override
    public String toString() {
        //return new Gson().toJson(this);]
        StringBuilder sb = new StringBuilder();

        sb.append("\n");
        sb.append("FirstName: " + this.getFirstName() + "\n");
        sb.append("Last Name: " + this.getLastName() + "\n");
        sb.append("User Type: " + this.getUserType().toString() + "\n");
        sb.append("Current balance: " + this.getBalance() + "\n");
        sb.append("ID: " + this.getId() + "\n");

        return sb.toString();
    }
}

enum UserType{
    parent,
    child,
    none
}