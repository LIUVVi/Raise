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


public class AdminIndexActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new Handler();

    private ListView adminLv;
    private LinearLayout addGoodsInfoView;
    private RelativeLayout showPro;
    private RelativeLayout showAdd;

    private ImageView imgPro;
    private TextView txtPro;
    private ImageView imgAdd;
    private TextView txtAdd;
    private TextView txtAdminTitle;
    private Button btnQuitAdmin;

    private Button btnAdd;
    private RadioGroup rdgSelectGoods;
    private RadioButton radiotxt;

    private EditText editSelectLocation;
    private EditText editSelectDemand;

    private List<Goods> goodsList = new ArrayList<>();

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_index);
        // 设置沉浸式状态栏
        StatusBarUtil.setTransparentForWindow(this);

        adminLv = findViewById(R.id.adminLv);
        addGoodsInfoView = findViewById(R.id.addGoodsInfoView);

        showPro = findViewById(R.id.showPro);
        showAdd = findViewById(R.id.showAdd);

        imgPro = findViewById(R.id.imgPro);
        txtAdd = findViewById(R.id.txtAdd);
        imgAdd = findViewById(R.id.imgInfo);
        txtPro = findViewById(R.id.txtPro);

        btnAdd = findViewById(R.id.btnAdd);
        rdgSelectGoods = findViewById(R.id.rdgSelectGoods);
        editSelectLocation = findViewById(R.id.editSelectLocation);
        editSelectDemand = findViewById(R.id.editSelectDemand);
        txtAdminTitle = findViewById(R.id.txtAdminTitle);
        btnQuitAdmin = findViewById(R.id.btnQuitAdmin);

        getAllGoodsInfo();

        showPro.setOnClickListener(this);
        showAdd.setOnClickListener(this);

        btnAdd.setOnClickListener(this);
        btnQuitAdmin.setOnClickListener(this);
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
            case R.id.btnAdd:
                addGoods();
                break;
            case R.id.btnQuitAdmin:
                quitAdmin();
            default:
                break;
        }
    }


    public void showProgressView() {

        txtAdminTitle.setText("募集进度");

        adminLv.setVisibility(View.VISIBLE);
        addGoodsInfoView.setVisibility(View.GONE);

        imgPro.setImageResource(R.mipmap.progress_selected);
        txtPro.setTextColor(Color.parseColor("#33cc66"));

        imgAdd.setImageResource(R.mipmap.add_normal);
        txtAdd.setTextColor(Color.parseColor("#e2e2e2"));

        getAllGoodsInfo();
    }

    public void showAddView() {
        txtAdminTitle.setText("新增募集");

        addGoodsInfoView.setVisibility(View.VISIBLE);
        adminLv.setVisibility(View.GONE);

        imgPro.setImageResource(R.mipmap.progress_normal);
        txtPro.setTextColor(Color.parseColor("#e2e2e2"));

        imgAdd.setImageResource(R.mipmap.add_selected);
        txtAdd.setTextColor(Color.parseColor("#33cc66"));
    }

    public void addGoods() {
        radiotxt = findViewById(rdgSelectGoods.getCheckedRadioButtonId());
        String goodsName = radiotxt.getText().toString();
        String location = editSelectLocation.getText().toString().trim();
        String demandStr = editSelectDemand.getText().toString().trim();
        Integer demand;
        if(demandStr.equals("")){
            demand = 0;
        }else{
            demand= Integer.parseInt(demandStr);
        }
        String img_url = "";
        switch (goodsName) {
            case "口罩":
                img_url = "R.mipmap.kz";
                break;
            case "护目镜":
                img_url = "R.mipmap.hmj";
                break;
            case "防护服":
                img_url = "R.mipmap.fhf";
                break;
            case "消毒液":
                img_url = "R.mipmap.xdy";
                break;
            default:
                break;
        }

        if(location.equals("") || demand < 50){
            Toast.makeText(this,"请检查所有内容是否都已经输入且数量必须大于50",Toast.LENGTH_SHORT).show();
        }else {
            postAddGoods(goodsName, location, demand, img_url);
        }

    }

    // 提交添加
    public void postAddGoods(String goodsName, String location, Integer demand, String img_url) {
        Map map = new HashMap<>();
        map.put("goodsName", goodsName);
        map.put("location", location);
        map.put("demand", demand);
        map.put("img_url", img_url);

        String param = gson.toJson(map);

        OkHttpClient client = new OkHttpClient.Builder().build();

        RequestBody body = RequestBody.create(JSON, param);
        String url = "http://47.111.127.219:8080/addgoods";
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
                if (result.equals("新增成功")) {
                    Looper.prepare();
                    Toast.makeText(AdminIndexActivity.this, "新增成功！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } else {
                    Looper.prepare();
                    Toast.makeText(AdminIndexActivity.this, "新增失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

    // 获取物资信息
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
                        System.out.println("+++++++++++++++++++++++++++++++++++" + goods.getDemand());
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
                        System.out.println("++++++++++++++++++++++" + map.toString());
                    }
                    final SimpleAdapter adapter = new SimpleAdapter(AdminIndexActivity.this,
                            list, R.layout.admin_list_item, new String[]{"goodsName", "location", "total", "progressing", "img_url"},
                            new int[]{R.id.txtGoodsName,
                                    R.id.txtLocation,
                                    R.id.txtTotal,
                                    R.id.txtProgressing,
                                    R.id.ivAdmin});

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //此处更新View
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    adminLv.setAdapter(adapter);
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

    // 退出管理员
    public void quitAdmin(){
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

