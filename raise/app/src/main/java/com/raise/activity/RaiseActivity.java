package com.raise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.leaf.library.StatusBarUtil;
import com.raise.R;
import com.raise.entity.Goods;

import org.w3c.dom.Text;

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

public class RaiseActivity extends AppCompatActivity {

    private ImageView imgRaiseGoods;
    private TextView txtRaiseBack;
    private TextView txtRaiseId;
    private TextView txtRaiseGoodsName;
    private TextView txtRaiseLocation;
    private Button btnConfirmRaise;
    private EditText editRaiseNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise);

        // 设置沉浸式状态栏
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);

        imgRaiseGoods = findViewById(R.id.imgRaiseGoods);
        txtRaiseBack = findViewById(R.id.txtRaiseBack);
        txtRaiseId = findViewById(R.id.txtRaiseId);
        txtRaiseGoodsName = findViewById(R.id.txtRaiseGoodsName);
        txtRaiseLocation = findViewById(R.id.txtRaiseLocation);
        btnConfirmRaise = findViewById(R.id.btnConfirmRaise);
        editRaiseNum =findViewById(R.id.editRaiseNum);
        setGoodsInfo();

        txtRaiseBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirmRaise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postRaise();
            }
        });
    }

    public void setGoodsInfo() {

        Intent intent = getIntent();

        String img_url = intent.getStringExtra("img_url");
        switch (img_url) {
            case "R.mipmap.kz":
                imgRaiseGoods.setImageResource(R.mipmap.kz);
                break;
            case "R.mipmap.fhf":
                imgRaiseGoods.setImageResource(R.mipmap.fhf);
                break;
            case "R.mipmap.hmj":
                imgRaiseGoods.setImageResource(R.mipmap.hmj);
                break;
            case "R.mipmap.xdy":
                imgRaiseGoods.setImageResource(R.mipmap.xdy);
                break;
            default:
                break;
        }

        txtRaiseId.setText(intent.getStringExtra("id"));
        txtRaiseGoodsName.setText(intent.getStringExtra("goodsName"));
        txtRaiseLocation.setText(intent.getStringExtra("location"));
    }

    public void postRaise(){
        String goodId = txtRaiseId.getText().toString();
        String raiseNum = editRaiseNum.getText().toString();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();

        Map map = new HashMap<>();
        map.put("id", goodId);
        map.put("raised", raiseNum);

        String param = gson.toJson(map);

        OkHttpClient client = new OkHttpClient.Builder().build();
        //FormBody 是RequestBody的子类
        RequestBody body = RequestBody.create(JSON, param);
        String url = "http://47.111.127.219:8080/raise";
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
                if (result.equals("捐赠成功")) {
                    Looper.prepare();
                    Toast.makeText(RaiseActivity.this, "捐赠成功！", Toast.LENGTH_SHORT).show();
                    finish();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(RaiseActivity.this, "捐赠失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }
}
