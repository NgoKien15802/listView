package com.example.listview;

public class user {
    private int id;
    private String Name;
    private String PhoneNum;
    private String imgUri;

    public user(int id, String name, String phoneNum, String imgUri) {
        this.id = id;
        Name = name;
        PhoneNum = phoneNum;
        this.imgUri = imgUri;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }
}
