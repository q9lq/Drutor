package com.example.drutor;

import java.util.Stack;

public class Tutor {
    private String fullname;
    private String email;
    private String password;
    private String phonenumber;
    private String ID;
    private String price;


    public Tutor(){};
    public Tutor(String firstn, String lastn ,String phonenumber,String ID,String email, String password,String price){
        this.fullname=firstn+" "+lastn;
        this.email=email;
        this.password=password;
        this.phonenumber=phonenumber;
        this.ID=ID;
        this.price=price;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
