package com.example.drutor;

public class Lesson {
    private String time;
    private String date;
    private String TEmail;
    private String s1;
    private String s2;
    private String s3;

    public Lesson(String time, String date, String TEmail, String s1, String s2, String s3) {
        this.time = time;
        this.date = date;
        this.TEmail = TEmail;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    public Lesson() {

    }

    public Lesson(String date , String time) {
        this.date=date;
        this.time=time;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTEmail() {
        return TEmail;
    }

    public void setTEmail(String TEmail) {
        this.TEmail = TEmail;
    }

    public String getS1() {
        return s1;
    }

    public void setS1(String s1) {
        this.s1 = s1;
    }

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getS3() {
        return s3;
    }

    public void setS3(String s3) {
        this.s3 = s3;
    }

}
