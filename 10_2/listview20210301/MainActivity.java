package com.example.listview20210301;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private ListView lvContact;
    private EditText etSearch;
    //ArrayList chua du lieu cho listview
    ArrayList<String> Contacts;
    ArrayAdapter<String> lvAddapter;

    //để lưu dữ liệu danh sách các User
    //khai báo một ArrayList<User>
    ArrayList<User> listUser;
    //Adapter của listview hiển thị danh sách User
    MyAdapter listUserAdapter;
    int selectedid=-1;
    MyDB mysqlitedb;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Tạo đối tượng phân tích menu layout
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menuitem, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuSort:
                Toast.makeText(MainActivity.this, "Sort",
                    Toast.LENGTH_SHORT).show();
                break;
            case R.id.mnuAdd:
                //Tạo đối tượng Intent để gọi tới AddNew
                Intent intent = new Intent(MainActivity.this,
                            AddUser.class);
                //Có 2 cacash gọi tới AddNew
                //Gọi không cần chờ dữ liệu phản hồi dùng hàm startActivity
                //Gọi nhưng chờ dữ liệu phản hồi dùng hàm startActivityForResult
                //Trường hợp này muốn chờ dữ liệu Contact mới nên chọn cách 2
                //tham số thứ nhất là intent
                //tham số thứ hai là requestCode để đánh dấu lần gọi
                startActivityForResult(intent, 100);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.contextmenuitem, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuEdit:
                //Tạo đối tượng Intent để gọi tới AddNew
                Intent intent = new Intent(MainActivity.this,
                        AddUser.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Id", listUser.get(selectedid).getId());
                bundle.putString("Name", listUser.get(selectedid).getName());
                bundle.putString("Phone", listUser.get(selectedid).getPhoneNum());
                intent.putExtras(bundle);
                startActivityForResult(intent, 200);
                break;
            case R.id.mnuDelete:
                Toast.makeText(this,
                        "Delete: " + listUser.get(selectedid).getName(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //lấy dữ liệu từ NewContact gửi về
        Bundle bundle = data.getExtras();
        int id = bundle.getInt("Id");
        String name = bundle.getString("Name");
        String phone = bundle.getString("Phone");
        if(requestCode==100 && resultCode==200)
        {
            //đặt vào listData
            listUser.add(new User(id, name, phone, ""));
            mysqlitedb.addContact(new User(id, name, phone, ""));
        }

        else if(requestCode==200 && resultCode==201)
            listUser.set(selectedid, new User(id, name, phone, ""));
        //cập nhật adapter
        listUserAdapter.notifyDataSetChanged();
        lvContact.setAdapter(listUserAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //tham chiếu tới listview và editext
        lvContact = findViewById(R.id.lvContact);
        etSearch = findViewById(R.id.etSearch);
        //Toast.makeText(this, "adfa", Toast.LENGTH_SHORT).show();
        etSearch.setText("");
        registerForContextMenu(lvContact);
        //Lấy dữ liệu cho listview: có thể từ csdl
        //Trong ví dụ này để đơn giản gán sẵn dữ liệu
        //Contacts = new ArrayList<>();
        //Contacts.add("Nam");
        //Contacts.add("Hải");
        //Contacts.add("Nguyên");
        //Tạo đối tượng ArrayAdapter để điều khiển hiển thị dữ liệu lên listvEach student has an Office 365 account to take online courses through the Microsoft Team system.iew
        //lvAddapter = new ArrayAdapter<>(this,
        //        R.layout.support_simple_spinner_dropdown_item,
        //        Contacts);
        //gắn lvaddapter vào listview
        //lvContact.setAdapter(lvAddapter);
        mysqlitedb = new MyDB(this, "ContactDB", null, 1);
        mysqlitedb.addContact(new User(1, "Nam", "09893838", ""));
        mysqlitedb.addContact(new User(2, "Bich", "03393039", ""));
        mysqlitedb.addContact(new User(3, "Hai", "098789089", ""));

        listUser = mysqlitedb.getAllContact();

        //listUser = new ArrayList<>();
        //listUser.add(new User(1, "Nam", "09893838", ""));
        //listUser.add(new User(2, "Bich", "03393039", ""));
        //listUser.add(new User(3, "Hai", "098789089", ""));

        //Tạo Adapter để đặt dữ liệu cho listview
        listUserAdapter = new MyAdapter(this, listUser);
        //Gắn Addapter vào cho listview
        lvContact.setAdapter(listUserAdapter);
        lvContact.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                selectedid = position;
                return false;
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                listUserAdapter.getFilter().filter(s.toString());
                listUserAdapter.notifyDataSetChanged();
                //lvContact.setAdapter(listUserAdapter);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}
