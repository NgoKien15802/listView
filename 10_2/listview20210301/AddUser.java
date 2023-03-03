package com.example.listview20210301;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddUser extends AppCompatActivity {
    EditText etId, etName, etPhone;
    Button btnOk, btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        etId = findViewById(R.id.etId);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null) {
            int id = bundle.getInt("Id");
            String name = bundle.getString("Name");
            String phone = bundle.getString("Phone");
            etId.setText(String.valueOf(id));
            etName.setText(name);
            etPhone.setText(phone);
            btnOk.setText("Edit");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tạo intent để trở về MainActivity
                Intent intent = new Intent();
                //Tạo bundle là đối tượng để chứa dữ liệu
                Bundle bundle = new Bundle();
                //bundle hoạt động như một Java Map các phần tử phân biệt theo key
                //bundle có các hàm put... trong đó ... là kiểu dữ liệu tương ứng
                bundle.putInt("Id", Integer.parseInt(etId.getText().toString()));
                bundle.putString("Name", etName.getText().toString());
                bundle.putString("Phone", etPhone.getText().toString());
                //có thể đặt cả đối tượng lên bundle bằng hàm putSerilizable
                //đặt bundle lên intent
                intent.putExtras(bundle);
                //trả về bằng hàm setResult
                //tham số thứ nhất là resultCode để quản lý phiên
                //tham số thứ hai  là intent chứa dữ liệu gửi về
                setResult(200, intent);
                //if(btnOk.getText()=="Edit")
                //  setResult(201, intent);
                //Kết thúc: đóng activity hiện thời.
                finish();
            }
        });
    }
}
