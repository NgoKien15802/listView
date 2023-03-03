package com.example.listview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class addUser extends AppCompatActivity {
    private EditText etId;
    private EditText etName;
    private EditText etPhone;
    private ImageView ivImage;
    private Button btnOk;
    private Button btnCancel;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutuser);


        etId = findViewById(R.id.editId);
        etName = findViewById(R.id.editName);
        etPhone = findViewById(R.id.editPhone);
        ivImage = findViewById(R.id.ivImage);
        btnOk = findViewById(R.id.btnOk);
        btnCancel = findViewById(R.id.btnCancel);

//       intent: cấp phép cho cta 1 hoạt động này làm một hoạt động khác
        Intent intent = getIntent();
//        bundle: để sử dụng chưa cần biên dịch muốn chạy
        Bundle bundle = intent.getExtras( );
        if(bundle!=null){
            int id = bundle.getInt("Id");
            String image = bundle.getString("Image");
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
                if(btnOk.getText()=="Edit")
                  setResult(201, intent);
                //Kết thúc: đóng activity hiện thời.
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(addUser.this,
                        MainActivity.class);
                startActivityForResult(intent, 200);
            }
        });

    }
}
