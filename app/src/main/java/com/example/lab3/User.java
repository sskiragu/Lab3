package com.example.lab3;

public class User {
    public String fullName, age, email, html, css, javascript;

    public User(){

    }

    public User(String fullName, String age, String email){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
    }

    public User(String fullName, String age, String email, String html, String css, String javascript){
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.html = html;
        this.css = css;
        this.javascript = javascript;
    }
}
