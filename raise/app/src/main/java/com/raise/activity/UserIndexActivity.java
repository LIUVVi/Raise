package com.raise.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.leaf.library.StatusBarUtil;
import com.raise.R;
import com.raise.entity.Goods;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class UserIndexActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler=new Handler();

    private ListView userLv;
    private LinearLayout userInfoView;
    private RelativeLayout showPro;
    private RelativeLayout showAdd;

    private ImageView imgPro;
    private TextView txtPro;
    private ImageView imgInfo;
    private TextView txtMy;

    private TextView txtUserName;
    private TextView txtUserLevel;
    private Button btnQuitUser;

    private LinearLayout header;
    private List<Goods> goodsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_index);
        // 设置沉浸式状态栏
        StatusBarUtil.setTransparentForWindow(this);

        userLv = findViewById(R.id.userLv);
        userInfoView = findViewById(R.id.userInfoView);

        showPro = findViewById(R.id.showPro);
        showAdd = findViewById(R.id.showAdd);

        imgPro = findViewById(R.id.imgPro);
        txtMy = findViewById(R.id.txtMy);
        imgInfo = findViewById(R.id.imgInfo);
        txtPro = findViewById(R.id.txtPro);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserLevel = findViewById(R.id.txtUserLevel);
        header = findViewById(R.id.header);
        btnQuitUser = findViewById(R.id.btnQuitUser);
        getAllGoodsInfo();

        showPro.setOnClickListener(this);
        showAdd.setOnClickListener(this);
        btnQuitUser.setOnClickListener(this);

        listOnclick();

        getUserInfo();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.showPro:
                showProgressView();
                break;
            case R.id.showAdd:
                showAddView();
                break;
            case R.id.btnQuitUser:
                quitUser();
                break;
            default:
                break;
        }
    }

    // 显示募集进度界面
    public void showProgressView() {

        userLv.setVisibility(View.VISIBLE);
        userInfoView.setVisibility(View.GONE);

        imgPro.setImageResource(R.mipmap.progress_selected);
        txtPro.setTextColor(Color.parseColor("#33cc66"));

        imgInfo.setImageResource(R.mipmap.userinfo_normal);
        txtMy.setTextColor(Color.parseColor("#e2e2e2"));

        header.setVisibility(View.VISIBLE);

        getAllGoodsInfo();

    }

    // 显示新增物资界面
    public void showAddView() {
        userInfoView.setVisibility(View.VISIBLE);
        userLv.setVisibility(View.GONE);
        header.setVisibility(View.GONE);

        imgPro.setImageResource(R.mipmap.progress_normal);
        txtPro.setTextColor(Color.parseColor("#e2e2e2"));

        imgInfo.setImageResource(R.mipmap.userinfo_selected);
        txtMy.setTextColor(Color.parseColor("#33cc66"));

    }

    // 获取所有物资信息
    public void getAllGoodsInfo() {

        String url = "http://47.111.127.219:8080/progressing";
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    Gson gson = new Gson();
                    Type goodsListType = new TypeToken<ArrayList<Goods>>() {
                    }.getType();
                    goodsList = gson.fromJson(response.body().string(), goodsListType);
                    System.out.println(goodsList);

                    ArrayList<HashMap<String, Object>> list = new ArrayList<>();

                    for (Goods goods : goodsList) {

                        HashMap<String, Object> map = new HashMap<>();

                        map.put("goodsName", goods.getGoodsName());
                        map.put("location", goods.getLocation());
                        map.put("total", goods.getRaised() + "/" + goods.getDemand());
                        map.put("progressing", (goods.getRaised() / (float) goods.getDemand()) * 100 + "%");

                        switch (goods.getImg_url()) {
                            case "R.mipmap.kz":
                                map.put("img_url", R.mipmap.kz);
                                break;
                            case "R.mipmap.fhf":
                                map.put("img_url", R.mipmap.fhf);
                                break;
                            case "R.mipmap.hmj":
                                map.put("img_url", R.mipmap.hmj);
                                break;
                            case "R.mipmap.xdy":
                                map.put("img_url", R.mipmap.xdy);
                                break;
                            default:
                                break;
                        }
                        list.add(map);
                    }
                    final SimpleAdapter adapter = new SimpleAdapter(UserIndexActivity.this,
                            list, R.layout.user_list_item, new String[]{"goodsName", "location", "total", "progressing", "img_url"},
                            new int[]{R.id.txtUserGoodsName,
                                    R.id.txtUserLocation,
                                    R.id.txtUserTotal,
                                    R.id.txtUserProgressing,
                                    R.id.ivUser});

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // 使用handler更新视图，防止线程崩溃
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    userLv.setAdapter(adapter);
                                }
                            });
                        }
                    }).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    // list_item点击事件，将信息传递给捐赠界面
    public void listOnclick(){
        userLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsList.get(i);
                Intent intent = new Intent(UserIndexActivity.this,RaiseActivity.class);
                intent.putExtra("id",goods.getId().toString());
                intent.putExtra("goodsName",goods.getGoodsName());
                intent.putExtra("location",goods.getLocation());
                intent.putExtra("img_url",goods.getImg_url());
                startActivity(intent);
            }
        });
    }

    // 获取用户信息
    public void getUserInfo(){
        SharedPreferences sp = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String username = sp.getString("username", null);
        String level = sp.getString("level", null);
        txtUserName.setText(username);
        txtUserLevel.setText(level);
    }

    public void quitUser(){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(this);
        alertdialogbuilder.setMessage("您确认要退出账户吗");
        alertdialogbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences preferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear().commit();
                System.exit(0);
            }
        });
        alertdialogbuilder.setNeutralButton("取消", null);
        final AlertDialog alertdialog= alertdialogbuilder.create();
        alertdialog.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        getAllGoodsInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent intent = new Intent();// 创建Intent对象
            intent.setAction(Intent.ACTION_MAIN);// 设置Intent动作
            intent.addCategory(Intent.CATEGORY_HOME);// 设置Intent种类
            startActivity(intent);// 将Intent传递给Activity
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

