package com.example.listenmusic;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.listenmusic.db.User;
import org.litepal.LitePal;
import java.util.List;

public class LoginFragment extends Fragment {

    private EditText inputName, inputPassword;

    private Button login, register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        inputName = view.findViewById(R.id.et_name);
        inputPassword = view.findViewById(R.id.et_password);
        login = view.findViewById(R.id.bt_login);
        register = view.findViewById(R.id.bt_register);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 登录按钮点击事件
         */
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = inputName.getText().toString();
                String userPassword = inputPassword.getText().toString();
                //通过用户名获取密码
                String pwd = LitePal.where()
                List<User> users = LitePal.where("name=?", userName).find(User.class);
                if (users == null || users.size() == 0) {
                    Toast.makeText(getActivity(),"用户名不存在",Toast.LENGTH_SHORT).show();
                }else if ()
            }
        });

        /**
         * 注册按钮点击事件
         */
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, 1);
                getActivity().finish();
            }
        });
    }

    private String readPwd(String userName) {
        SharedPreferences preferences = getActivity().getSharedPreferences("ListenMusic", Context.MODE_PRIVATE);
        return preferences.getString(userName, "");
    }
}
