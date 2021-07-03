package com.raise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.raise.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegActivity extends AppCompatActivity {


    private TextView txtBack;
    private EditText editUsername;
    private EditText editPassword;
    private EditText editRepPassword;
    private Button btnReg;
    private RadioGroup radioRegLevel;
    private RadioButton radioLevel;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);

        // 设置沉浸式状态栏
        StatusBarUtil.setTransparentForWindow(this);
        // 设置状态栏颜色为黑色模式
        StatusBarUtil.setDarkMode(this);

        txtBack = findViewById(R.id.rtxtBack);

        editUsername = findViewById(R.id.reditUsername);
        editPassword = findViewById(R.id.reditPassword);
        editRepPassword = findViewById(R.id.reditRepPassword);
        radioRegLevel = findViewById(R.id.radioRegLevel);

        btnReg = findViewById(R.id.btnReg);

        //  左上角返回
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radioLevel = findViewById(radioRegLevel.getCheckedRadioButtonId());

                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String repPassword = editRepPassword.getText().toString().trim();
                String level = radioLevel.getText().toString();

                System.out.println("用户等级========================="+level);

                if (checkNull(username, password, repPassword)) {
                    if (checkPassword(password, repPassword)) {
                        regAccount(username, password, level);
                    } else {
                        Toast.makeText(RegActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegActivity.this, "请检查所有内容是否都已经输入", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean checkNull(String username, String password, String repPassword) {

        if (username.equals("") || password.equals("") || repPassword.equals("")) {
            return false;
        } else {
            return true;
        }

    }

    public boolean checkPassword(String password, String repPassword) {
        if (password.equals(repPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public void regAccount(String username, String password, String level) {


        Map map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("level", level);

        String param = gson.toJson(map);


        System.out.println("++++++++++++++++++++++++++:" + param);
        OkHttpClient client = new OkHttpClient.Builder().build();
        //FormBody 是RequestBody的子类
        RequestBody body = RequestBody.create(JSON, param);
        System.out.println("++++++++++++++++++++++++++:" + body.toString());
        String url = "http://47.111.127.219:8080/register";
        final Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("****************************" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                System.out.println(result);
                if (result.equals("注册成功")) {
                    Looper.prepare();
                    Toast.makeText(RegActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(RegActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }

            }
        });
    }

}
