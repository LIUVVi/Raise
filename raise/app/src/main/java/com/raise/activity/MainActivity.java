package com.raise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.leaf.library.StatusBarUtil;
import com.raise.R;
import com.raise.entity.User;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnIndexLogin;

    private Button btnIndexReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String level = sp.getString("level", null);

        if (username == null) {
            setContentView(R.layout.activity_main);
            // 第三方状态栏工具库 设置沉浸式状态栏
            StatusBarUtil.setTransparentForWindow(this);

            btnIndexLogin = findViewById(R.id.btnIndexLogin);
            btnIndexReg = findViewById(R.id.btnIndexReg);

            btnIndexLogin.setOnClickListener(this);
            btnIndexReg.setOnClickListener(this);
        } else {
            if (level.equals("管理员")) {
                jumpAdminIndex();
            } else {
                jumpUserIndex();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIndexLogin:
                jumpLogin();
                break;
            case R.id.btnIndexReg:
                jumpReg();
                break;
            default:
                break;
        }
    }

    public void jumpLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void jumpReg() {
        Intent intent = new Intent(this, RegActivity.class);
        startActivity(intent);
    }

    public void jumpAdminIndex() {
        Intent intent = new Intent(this, AdminIndexActivity.class);
        startActivity(intent);
    }

    public void jumpUserIndex() {
        Intent intent = new Intent(this, UserIndexActivity.class);
        startActivity(intent);
    }

}
