package com.example.listview;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDB extends SQLiteOpenHelper {

//    đặt tên cho bảng
    public static final String TableName = "ContactTable";

//    đặt tên cho các cột
    public static final String Id = "Id";
    public static final String Name = "Fullname";
    public static final String Phone = "PhoneNumber";
    public static final String Image = "Image";

    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

//    public MyDB(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
//        super(context, name, factory, version, errorHandler);
//    }
//
//    public MyDB(@Nullable Context context, @Nullable String name, int version, @NonNull SQLiteDatabase.OpenParams openParams) {
//        super(context, name, version, openParams);
//    }

//    Tạo csdl
    @Override
    public void onCreate(SQLiteDatabase db) {
//        tạo bảng tableName
        String sqlCreate = "Create table if not exists " + TableName + "(" + Id +  " Integer Primary key, " + Image + " Text,  " + Name + " Text, "
        + Phone + " Text)";

//        chạy câu truy vấn sql để tạo bảng
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
//        Xóa bảng table đã có
        db.execSQL("Drop table if exists " + TableName);
//        Tạo lại
        onCreate(db);
    }

//    Lấy tất cả các dòng của bảng TableContact trả về dạng arrayList

    public ArrayList<Contact> getAllContact(){
        ArrayList<Contact> list = new ArrayList<>();

//        câu truy vấn
        String sql = "Select * from " + TableName;
//        Lấy đối tượng csdl sqlite
        SQLiteDatabase db = this.getReadableDatabase();

//        chạy câu truy vấn trả về dạng cursor
        Cursor cursor = db.rawQuery(sql, null);

//        Tạo arraylist<Contact> để trả về
        if(cursor!=null){
            while (cursor.moveToNext()){
                Contact contact = new Contact(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
                list.add(contact);
            }
        }
        return list;
    }

//    Hàm thêm 1 contact vào bảng tableContact
    public void addContact(Contact contact){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, contact.getId());
        value.put(Image, contact.getImages());
        value.put(Name, contact.getName());
        value.put(Phone, contact.getPhone());
        db.insert(TableName,null, value);
        db.close();
    }

//    update contact
    public void updateContact (int id , Contact contact){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Id, contact.getId());
        value.put(Image, contact.getImages());
        value.put(Name, contact.getName());
        value.put(Phone, contact.getPhone());
//        update theo điều kiện nào đấy.
        db.update(TableName, value,Id + "=?",new String[]{String.valueOf(id)});
        db.close();
    }

//    delete contact
    public void deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "Delete from " + TableName + " Where ID=" + id;
        db.execSQL(sql);
        db.close();
    }
}
