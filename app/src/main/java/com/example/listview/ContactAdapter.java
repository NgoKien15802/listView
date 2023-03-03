package com.example.listview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends BaseAdapter {
//    ngữ cảnh của ứng dụng
    private Activity context;
    private int layout;
//    danh sách các contact
    private ArrayList<Contact> contacts;

//    object để phần tích layout
    private LayoutInflater inflater;

    public ContactAdapter(Activity activity, ArrayList<Contact> contacts) {
        this.context = activity;
        this.contacts = contacts;
        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }



    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int i) {
        return contacts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contacts.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = convertView;
        if(v==null)
            v = inflater.inflate(R.layout.layoutlistview, null);
            ImageView imgProfile = v.findViewById(R.id.imageView);
            TextView tvname = v.findViewById(R.id.tvName);
            tvname.setText(contacts.get(i).getName());
            TextView tvphone = v.findViewById(R.id.tvPhone);
            tvphone.setText(contacts.get(i).getPhone());
            return v;
    }
}
