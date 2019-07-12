package com.example.listenmusic;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.listenmusic.db.MyDataBaseHelper;
import com.example.listenmusic.db.User;
import org.litepal.LitePal;


import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText rgName, rgPassword;

    private RadioButton rbMale, rbFemale;

    private RadioGroup rgGender;

    private Button btRegister;

    private MyDataBaseHelper dbHelper;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {
        //初始化各个控件
        rgName = findViewById(R.id.rg_name);
        rgPassword = findViewById(R.id.rg_password);
        rbMale = findViewById(R.id.rb_male);
        rbFemale = findViewById(R.id.rb_female);
        btRegister = findViewById(R.id.bt_register);
        rgGender = findViewById(R.id.rg_gender);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框中输入的内容
                String name = rgName.getText().toString().trim();
                String password = rgPassword.getText().toString().trim();
                //获取单选按钮的选定值
                String gender = null;
                int genderId = rgGender.getCheckedRadioButtonId();
                switch (genderId) {
                    case R.id.rb_male:
                        gender = "男";
                        break;
                    case R.id.rb_female:
                        gender = "女";
                        break;
                    default:
                        break;
                }

//                List<User> users = LitePal.where("name=?", name).find(User.class);

                db = dbHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from user where name=?", new String[]{name});

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(gender)) {
                    Toast.makeText(RegisterActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                } else if (cursor.getCount() > 0) {
                    Toast.makeText(RegisterActivity.this, "用户名已存在", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    //保存注册信息
                    saveRegisterInfo(db, name, password, gender);
                    //返回至登录界面
                    Intent intent = new Intent();
                    intent.putExtra("userName", name);
                    setResult(RESULT_OK, intent);
                    RegisterActivity.this.finish();
                }
            }
        });

    }

    private void saveRegisterInfo(SQLiteDatabase db, String userName, String userPassword, String userGender) {
//        User user = new User();
//        user.setName(userName);
//        user.setPassword(userPassword);
//        user.setGender(userGender);
//        user.save();
        ContentValues values = new ContentValues();
        values.put("name", userName);
        values.put("password", userPassword);
        values.put("gender", userGender);
        db.insert("user", null, values);
    }
}
