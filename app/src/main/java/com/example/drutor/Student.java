package com.example.drutor;

import java.util.Date;

public class Student {
    private String Fullname ;
    private String ID;
    private String Phonenumer;
    private String TEmail;
    private String LessonsNum;
    private String payedNum;


    public Student() { }

    public Student(String fullname, String phonenumer, String ID,String TEmail,String LessonsNum, String payedNum) {
        this.Fullname = fullname;
        this.ID = ID;
        this.Phonenumer = phonenumer;
        this.TEmail=TEmail;
        this.LessonsNum=LessonsNum;
        this.payedNum=payedNum;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhonenumer() {
        return Phonenumer;
    }

    public void setPhonenumer(String phonenumer) {
        Phonenumer = phonenumer;
    }

    public String getTEmail() {
        return TEmail;
    }

    public String getLessonsNum() {
        return LessonsNum;
    }

    public void setLessonsNum(String lessonsNum) {
        LessonsNum = lessonsNum;
    }

    public String getPayedNum() {
        return payedNum;
    }

    public void setPayedNum(String payedNum) {
        this.payedNum = payedNum;
    }

    public void setTEmail(String TEmail) {
        this.TEmail = TEmail;
    }
}
