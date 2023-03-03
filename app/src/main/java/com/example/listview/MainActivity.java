package com.example.listview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.se.omapi.Session;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Contact> contactList;
    private ContactAdapter listAdapter;
    private EditText etSearch;
    private ListView lstContact;
    private FloatingActionButton btnAdd;
    private EditText etId;
    private EditText etName;
    private EditText etPhone;
    private Button btnOk;
    private Button btnCancel;
    int selectItemId;


    //để lưu dữ liệu danh sách các User
    //khai báo một ArrayList<User>
    ArrayList<user> listUser;
    //Adapter của listview hiển thị danh sách User
    int selectedid=-1;

    private MyDB db;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




//        thiết lập dữ liệu mẫu
        contactList = new ArrayList<>();
//        contactList.add(new Contact(1,"img1","Ngo Trung Kien", "0958573849"));
//        contactList.add(new Contact(2,"img2","Nguyen Ngoc Hiep", "093821449"));
//        contactList.add(new Contact(3,"img3","Nguyen Quoc Dung", "0998284989"));

//        tạo mới csdl
        db = new MyDB(this, "ContactDB",null,1);

//        Thêm dữ liệu lần đầu vào db
        db.addContact(new Contact(1,"img1","Ngo Trung Kiensfsdf", "0958573849"));
        db.addContact(new Contact(2,"img2","Nguyen Ngoc Hiep", "093821449"));
        db.addContact(new Contact(3,"img3","Nguyen Quoc Dung", "0998284989"));
        contactList = db.getAllContact();

        listAdapter = new ContactAdapter(this, contactList);
        etSearch = findViewById(R.id.etSearch);
        lstContact = findViewById(R.id.lstContact);
        btnAdd = findViewById(R.id.btnAdd);
        lstContact.setAdapter(listAdapter);
        registerForContextMenu(lstContact);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, addUser.class);
//                startActivity(intent);
                startActivityForResult(intent, 100);
            }
        });

            lstContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int i, long id) {
                selectedid = i;
                return false;
            }
        });

;    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        Create menu option
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.actionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnuSortName:
//                sxep arrayList<Contact> theo teen
                    Collections.sort(contactList,Contact.sortByName);
                    listAdapter = new ContactAdapter(this, contactList);
                    lstContact.setAdapter(listAdapter);
                    break;
            case R.id.mnuSortPhone:
                //                sxep arrayList<Contact> theo phone
                Collections.sort(contactList,Contact.sortByPhone);
                listAdapter = new ContactAdapter(this, contactList);
                lstContact.setAdapter(listAdapter);
                break;

            case R.id.btnAdd:
                //Tạo đối tượng Intent để gọi tới AddNew
                Intent intent = new Intent(MainActivity.this,
                        addUser.class);
                //Có 2 cacash gọi tới AddNew
                //Gọi không cần chờ dữ liệu phản hồi dùng hàm startActivity
                //Gọi nhưng chờ dữ liệu phản hồi dùng hàm startActivityForResult
                //Trường hợp này muốn chờ dữ liệu Contact mới nên chọn cách 2
                //tham số thứ nhất là intent
                //tham số thứ hai là requestCode để đánh dấu lần gọi
                startActivityForResult(intent, 100);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenuitem, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater2 = new MenuInflater(this);
        inflater2.inflate(R.menu.call, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater3 = new MenuInflater(this);
        inflater3.inflate(R.menu.sendmessage, menu);
        super.onCreateContextMenu(menu, v, menuInfo);


        MenuInflater inflater4 = new MenuInflater(this);
        inflater4.inflate(R.menu.alarm, menu);
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Contact c = contactList.get(selectedid);
        //Tạo đối tượng Intent để gọi tới AddNew

        switch (item.getItemId()){
            case R.id.mnuEdit:
                Intent intent = new Intent(MainActivity.this,
                        addUser.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", c.getId());
                bundle.putString("Name", c.getName());
                bundle.putString("Phone", c.getPhone());
                bundle.putString("Image", c.getImages());

                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.idCall:
                Intent in = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + c.getPhone()));
                startActivity(in);

                break;


            case R.id.idSendemail:
                Intent intent2 = new Intent(Intent.ACTION_SENDTO);
                intent2.setData(Uri.parse("mailto:"));
                intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{"email@addreess.com"});
                intent2.putExtra(Intent.EXTRA_SUBJECT, "Tiêu đề email");
                intent2.putExtra(Intent.EXTRA_TEXT, "Nội dung email");
                try{
                    startActivity(intent2);
                }
                catch(ActivityNotFoundException e){
                    Toast.makeText(this, "Ứng dụng Email không tồn tại trên thiết bị của bạn", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.idmessage:
                Intent intent1 = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto: " + c.getPhone()));
                intent1.putExtra("sms_body", "Nội dung tin nhắn");
                startActivity(intent1);
                break;

            case R.id.mnuDelete:
                new AlertDialog.Builder(this)
                        .setTitle("Delete contact")
                        .setMessage("Are you sure you want to delete this contact?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
//                                contactList.remove(selectedid);
                                db.deleteContact(c.getId());
                                lstContact.setAdapter(listAdapter);
                            }
                        })

                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();

                break;
            case R.id.idAlarm:
//                Intent intentAlarm = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
//                if (intentAlarm.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intentAlarm);
//                }else{
//                    Toast.makeText(this,"không mở được alarm",Toast.LENGTH_SHORT).show();
//                }
                Intent intentAlarm = new Intent(Intent.ACTION_MAIN);
                intentAlarm.setClassName("com.google.android.deskclock", "com.android.deskclock.DeskClock");
                startActivity(intentAlarm);

//                Intent intentAlarm = new Intent();
//                intentAlarm.setComponent(new ComponentName("com.android.deskclock", "com.android.deskclock.AlarmClock"));
//                startActivity(intentAlarm);
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        Contact newcontact = new Contact(id,"",name,phone);
        if(requestCode==100 && resultCode==200)
        {
            db.addContact(newcontact);
        }

        else if(requestCode==200 && resultCode==201)
            db.updateContact(id,newcontact);
        //cập nhật adapter
//        update adapter, cập nhật lại danh sách
        listAdapter.notifyDataSetChanged();
        lstContact.setAdapter(listAdapter);
    }
}