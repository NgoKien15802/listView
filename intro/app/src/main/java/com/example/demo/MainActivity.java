package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CheckBox;
import android.widget.RadioGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    // tạo queQuan để chọn quê quán
    String[] queQuan = { "Quê quán" , "Hà Nội" , "Nam Định", "Hà Nam", "Phú Thọ","Ninh Bình", "Thái Bình","Thái Nguyên" };
    // có ListView lv để add
    ListView lv;
    // tạo 1 ArrayList có kiểu String lưu trữ để add các info của sinh vào đây và add là lv
    List<String> results = new ArrayList<>();

    String result = "";
    String sex = "";
    String SoThich = "";
    String hometown = "";
    RadioGroup radioGrp;
    EditText etFullname;
    EditText etPhoneNumber;
    Button btnSubmit;
    ArrayAdapter<String> arr;
    CheckBox TheThao, CaNhac , Nhay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//         Spinner
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked


        // tạo spinner lấy id là id đc tạo bên design
        Spinner spino = findViewById(R.id.idHometown);
        //set on phần tử đc chọn.
        spino.setOnItemSelectedListener(this);
        // tạo arrayAdapter và có gia trị là biến danh sách các quê quán đc tạo biến ở trên
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                queQuan);


        //ad set DropDown View Resource
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        // setAdapter cho ad
        spino.setAdapter(ad);


        // lv là ListView là biến trên lấy id bên disgn là lvInfo
        lv = findViewById(R.id.lvInfo);

        //results là ArrayList biến tạo ở trên để add vào listView
        results.add("Ngô Trung Kiên - 02472342342 - Nam - Nam Định");

        //
        showList(lv);



//        radio button
        // on below line we are initializing our variables.
        radioGrp = findViewById(R.id.idRadioGroup);

        radioGrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // lấy đc radioButton đc chọn
                RadioButton radioButton = group.findViewById(checkedId);
                sex = "";
                // gán giá trị đc chọn cho biến sexS
                sex+= radioButton.getText();
            }
        });




        // tìm btn có id là btnSubmit
        btnSubmit = findViewById(R.id.btnSubmit);
        // lắng nghe sự kiện click
        btnSubmit.setOnClickListener(this);
    }



    public void showList(ListView lv) {
        // tạo ArrayAdapter có results là ArrayList là biến trên đầu
        arr
                = new ArrayAdapter<String>(
                this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                results);

        // set listView có  adapter là arr để show cái info sinh viên ban đầu có sẵn
        lv.setAdapter(arr);

    }



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        hometown= "";
        // lấy đc quê quán được chọn
        hometown +=  queQuan[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    // dùng regex check xem có đúng định dạng số điện thoại không
    public static boolean isNumeric(String str) {
        return str.matches("(84|0[3|5|7|8|9])+([0-9]{8})\\b");
    }
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            // handle khi click vào nút add
            case R.id.btnSubmit:
                result = "";
                // lấy cái editView có id là etFullname gán cho biến đc tạo ở trên
                etFullname = findViewById(R.id.etFullname);
                // lấy cái editView có id là etPhoneNumber gán cho biến đc tạo ở trên
                etPhoneNumber = findViewById(R.id.etPhoneNumber);
                //check nếu etFullname rỗng thì báo cho user
                if(etFullname.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Họ và tên đang trống. Mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                //check nếu etPhoneNumber rỗng thì báo cho user
                if(etPhoneNumber.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Số điện thoại đang trống. Mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check nếu etPhoneNumber ko đúng định dạng là regex là hàm ở trên thì báo cho user
                if(isNumeric(String.valueOf(etPhoneNumber.getText())) == false){
                    Toast.makeText(MainActivity.this, "Số điện thoại không đúng định dạng(10 số). Mời bạn nhập lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check nếu giới tính ko đc chọn thì báo cho user
                if(sex.length()==0){
                    Toast.makeText(MainActivity.this, "Giới tính chưa chọn. Mời bạn chọn lại", Toast.LENGTH_SHORT).show();
                    return;
                }

                //check nếu quê quán ko đc chọn thì báo cho user
                if(hometown.equals("Quê quán")){
                    Toast.makeText(MainActivity.this, "Quê quán chưa chọn. Mời bạn chọn lại", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Getting instance of CheckBoxes and Button from the activty_main.xml file
                TheThao=(CheckBox)findViewById(R.id.idTheThao);
                CaNhac=(CheckBox)findViewById(R.id.idCaNhac);
                Nhay=(CheckBox)findViewById(R.id.idNhay);
                if(TheThao.isChecked()){
                    SoThich+= " Thể thao";
                }
                if(CaNhac.isChecked()){
                    SoThich+= " Ca nhạc";
                }
                if(Nhay.isChecked()){
                    SoThich+= " Nhảy";
                }

                // thỏa mãn hết đk thì gán hết vào string result là biến ở trên
                result += etFullname.getText() + " - ";
                result += etPhoneNumber.getText() + " - ";;
                result += sex + " - ";
                result += hometown + " ";
                result += SoThich;
                // sau đó add vào arrayList là biến trên
                results.add(result);
                // gọi lại hàm showList để add vào listView và show ra màn hình
                showList(lv);
                // thông báo add thành công
                Toast.makeText(MainActivity.this, "Bạn đã add thành công.", Toast.LENGTH_SHORT).show();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
    }
}