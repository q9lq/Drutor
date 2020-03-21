package com.example.drutor;

import java.util.Date;

public class Student {
    private String Fullname ;
    private String ID;
    private String Phonenumer;
    private String Notes;
    private String TEmail;

    public Student() { }

    public Student(String fullname, String phonenumer, String ID,String TEmail) {
        this.Fullname = fullname;
        this.ID = ID;
        this.Phonenumer = phonenumer;
        this.TEmail=TEmail;

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

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getTEmail() {
        return TEmail;
    }

    public void setTEmail(String TEmail) {
        this.TEmail = TEmail;
    }
}
