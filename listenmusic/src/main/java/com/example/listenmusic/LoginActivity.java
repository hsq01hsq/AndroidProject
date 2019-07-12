package com.example.listenmusic;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listenmusic.db.MyDataBaseHelper;


public class LoginActivity extends AppCompatActivity {

    private EditText inputName, inputPassword;

    private Button login, register;

    private MyDataBaseHelper dbHelper;

    private SQLiteDatabase db;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        inputName = findViewById(R.id.et_name);
        inputPassword = findViewById(R.id.et_password);
        login = findViewById(R.id.bt_login);
        register = findViewById(R.id.bt_register);
        /**
         * 登录按钮点击事件
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getReadableDatabase();
                String userName = inputName.getText().toString();
                String userPassword = inputPassword.getText().toString();
                Cursor cursor = db.rawQuery("select * from user where name=?", new String[]{userName});
                int pwdColumnIndex = cursor.getColumnIndex("password");
                String pwd = cursor.getString(pwdColumnIndex);
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "用户名不能为空", Toast.LENGTH_SHORT).show();
                } else if (cursor.getCount() == 0) {
                    Toast.makeText(LoginActivity.this, "用户名不存在", Toast.LENGTH_SHORT).show();
                } else if (!userPassword.equals(pwd)) {
                    Toast.makeText(LoginActivity.this, "密码错误，请重新输入密码", Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * 注册按钮点击事件
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 1);
                LoginActivity.this.finish();
            }
        });
    }
}
