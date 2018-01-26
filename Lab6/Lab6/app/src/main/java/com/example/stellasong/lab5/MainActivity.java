package com.example.stellasong.lab5;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.stellasong.lab5.wasabeef.OvershootInLeftAnimator;
import com.example.stellasong.lab5.wasabeef.ScaleInAnimationAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.example.stellasong.lab5.R.id.car;

public class MainActivity extends AppCompatActivity {

    private DynamicReceiver dynamicReceiver = new DynamicReceiver();
    private RecyclerView mRecyclerView;
    private ListView mListView;
    private List<Goods> goods;
    private List<Goods> cargoods;
    private com.example.stellasong.lab5.ShopcarAdapter shopAdapter;
    private com.example.stellasong.lab5.ShopAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置购物车界面不可见
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.secondview);
        linearLayout.setVisibility(View.INVISIBLE);

        // 设置物品清单
        final String[] Name = new String[]{"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        final String[] Price = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
        final String[] Info = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};
        final int[] stu_pic = {
                R.mipmap.enchatedforest,
                R.mipmap.arla,
                R.mipmap.devondale,
                R.mipmap.kindle,
                R.mipmap.waitrose,
                R.mipmap.mcvitie,
                R.mipmap.ferrero,
                R.mipmap.maltesers,
                R.mipmap.lindt,
                R.mipmap.borggreve
        };
        goods = new ArrayList<Goods>();
        cargoods = new ArrayList<Goods>();
        for (int i = 0; i < Name.length; i++)
            goods.add(new Goods(Name[i], Info[i], Price[i]));

        // 将物品清单装填到RecyclerView中
        mRecyclerView = (RecyclerView)findViewById(R.id.shop);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new com.example.stellasong.lab5.ShopAdapter(goods, MainActivity.this);
        ScaleInAnimationAdapter animationAdapter = new ScaleInAnimationAdapter(mAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());

        // 将购物车清单装填到ListView中
        cargoods.add(new Goods("购物车", "", "价格"));
        shopAdapter = new com.example.stellasong.lab5.ShopcarAdapter(cargoods, MainActivity.this);
        mListView = (ListView)findViewById(R.id.shopcaritem);
        mListView.setAdapter(shopAdapter);

        // 注册订阅者
        EventBus.getDefault().register(this);

        // 发送静态广播
        Random random = new Random();
        int n = random.nextInt(goods.size()); // 获得由一个随机数
        Intent intent = new Intent("com.example.stellasong.lab5.staticreceiver");
        intent.putExtra("ItemImage", stu_pic[n]);
        intent.putExtra("Name", goods.get(n).getName());
        intent.putExtra("Price", goods.get(n).getprice());
        intent.putExtra("Info", goods.get(n).getInfo());
        sendBroadcast(intent);

        // 注册动态广播
        IntentFilter dynamic_filter = new IntentFilter();
        dynamic_filter.addAction("com.example.stellasong.lab5.dynamicreceiver");
        registerReceiver(dynamicReceiver, dynamic_filter);

        // 设置商店列表监听事件
        mAdapter.setOnItemClickListener(new com.example.stellasong.lab5.ShopAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("Name", goods.get(position).getName());
                intent.putExtra("Price", goods.get(position).getprice());
                intent.putExtra("Info", goods.get(position).getInfo());
                startActivityForResult(intent, 0);
            }

            @Override
            public void onLongClick(int position) {
                goods.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "移除第" + (int)(position + 1) + "个商品", Toast.LENGTH_SHORT).show();
            }
        });

        // 设置购物车点击监听
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                Intent intent = new Intent(MainActivity.this, ShowDetails.class);
                intent.putExtra("Name", cargoods.get(i).getName());
                intent.putExtra("Price", cargoods.get(i).getprice());
                intent.putExtra("Info", cargoods.get(i).getInfo());
                startActivityForResult(intent,1);
            }
        });

        // 设置购物车长按监听
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view, final int i,long l){
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品").setMessage("从购物车移除" + Name[i] + "?").
                        setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (cargoods.remove(i) != null )
                            shopAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).show();
                return true;
            }
        });

        // 设置购物车图标转换
        final ImageButton imb = (ImageButton)findViewById(car);
        imb.setTag("0");
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imb.getTag() == "0") {
                    imb.setImageResource(R.mipmap.mainpage);
                    imb.setTag("1");
                    linearLayout.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                } else {
                    imb.setImageResource(R.mipmap.shoplist);
                    imb.setTag("0");
                    linearLayout.setVisibility(View.INVISIBLE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    // 购物车接受添加物品信息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Goods event) {
        cargoods.add(event);
        shopAdapter.notifyDataSetChanged();
        // 发送动态广播
        Intent intent = new Intent("com.example.stellasong.lab5.dynamicreceiver");
        intent.putExtra("Name", event.getName());
        sendBroadcast(intent);
    };

    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(dynamicReceiver);
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        final LinearLayout linearLayout = (LinearLayout)findViewById(R.id.secondview);
        final ImageButton imb = (ImageButton)findViewById(car);
        linearLayout.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        imb.setImageResource(R.mipmap.mainpage);
        imb.setTag("1");
    }
}

