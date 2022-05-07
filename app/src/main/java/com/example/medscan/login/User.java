package com.example.medscan.login;

public class User {
    String doc_name,imageURl,userId,Context;


    public User(String userId) {
        this.userId = userId;
    }

    public User(String doc_name, String imageURl) {
        this.doc_name = doc_name;
        this.imageURl = imageURl;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }

    public String getImageURl() {
        return imageURl;
    }

    public void setImageURl(String imageURl) {
        this.imageURl = imageURl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContext() {
        return Context;
    }

    public void setContext(String context) {
        Context = context;
    }
}
