package com.raise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    private TextView txtBack;

    private EditText editUsername;
    private EditText editPassword;
    private Button btnLogin;
    private RadioGroup radioLevel;
    private RadioButton radioLevelSelect;
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 设置沉浸式状态栏
        StatusBarUtil.setTransparentForWindow(this);
        // 设置状态栏颜色为黑色模式
        StatusBarUtil.setDarkMode(this);

        txtBack = findViewById(R.id.ltxtBack);

        editUsername = findViewById(R.id.leditUsername);
        editPassword = findViewById(R.id.leditPassword);
        btnLogin = findViewById(R.id.btnLogin);
        radioLevel = findViewById(R.id.radioLevel);

        //  左上角返回
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioLevelSelect = findViewById(radioLevel.getCheckedRadioButtonId());

                String username = editUsername.getText().toString().trim();
                String password = editPassword.getText().toString().trim();
                String level = radioLevelSelect.getText().toString();
                login(username, password,level);
                System.out.println("登录按钮被点击");
            }
        });
    }

    public void login(final String username, String password, final String level) {

        Map map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        map.put("level",level);

        String param = gson.toJson(map);

        OkHttpClient client = new OkHttpClient.Builder().build();
        //FormBody 是RequestBody的子类
        RequestBody body = RequestBody.create(JSON, param);
        String url = "http://47.111.127.219:8080/login";
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
                if (result.equals("登录成功")) {
                    Looper.prepare();
                    SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", username);
                    editor.putString("level", level);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                    Intent intent;
                    if(level.equals("管理员")){
                        intent = new Intent(LoginActivity.this, AdminIndexActivity.class);
                    }else {
                        intent = new Intent(LoginActivity.this, UserIndexActivity.class);
                    }
                    startActivity(intent);
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}
