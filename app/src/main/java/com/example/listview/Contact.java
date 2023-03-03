package com.example.listview;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Comparator;

public class Contact {
    private int id;
    private String images;
    private String name;
    private String phone;

    public Contact() {
    }

    public Contact(int id, String images, String name, String phone) {
        this.id = id;
        this.images = images;
        this.name = name;
        this.phone = phone;
    }
    
    public static void filterName(String str){
        String[] arr;
        arr = str.split(" ");
        Toast toast = Toast.makeText(null, arr[arr.length - 1], Toast.LENGTH_SHORT);
        toast.show();
    }
    //sort by name
    public static Comparator<Contact> sortByName = new Comparator<Contact>() {
        @Override
        public int compare(Contact obj1, Contact obj2) {
            return obj1.name.split(" ")[2].compareTo(obj2.name.split(" ")[2]);
        }
    };

    //sort by name
    public static Comparator<Contact> sortByPhone = new Comparator<Contact>() {
        @Override
        public int compare(Contact obj1, Contact obj2) {
            return obj1.phone.compareTo(obj2.phone);
        }
    };

    public int getId() {
        return id;
    }

    public String getImages() {
        return images;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
