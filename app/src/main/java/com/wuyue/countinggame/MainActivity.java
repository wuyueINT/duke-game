package com.wuyue.countinggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.gjiazhe.wavesidebar.WaveSideBar mSideBar;
    private RecyclerView mRecyclerView;
    private List<String> nameList;
    private ScrollView scroll;
    public RecyclerAdapter adapter;

    EditText et_name;
    Button btn_addpl;
    Button btn_start;
    Button btn_create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameList = new ArrayList<>();
        initView();
        //initData();

        //设置导航栏
        mSideBar.setMaxOffset(100);
        mSideBar.setPosition(WaveSideBar.POSITION_LEFT);
        mSideBar.setTextAlign(WaveSideBar.TEXT_ALIGN_CENTER);
        mSideBar.setLazyRespond(true);
        mSideBar.setOnSelectIndexItemListener(new WaveSideBar.OnSelectIndexItemListener() {
            @Override
            public void onSelectIndexItem(String index) {
                switch (index){
                    case "新建":
                        scroll.scrollTo(0, 0);
                        break;
                    case "游戏":
                        scroll.scrollTo(0, 1000);
                        break;
                    default:
                        break;
                }
            }
        });
        mSideBar.setIndexItems("新建", "游戏");
        mSideBar.setLazyRespond(true);

        //准备数据
        btn_addpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString();
                nameList.add(name);
                adapter.notifyItemInserted(nameList.size());
            }
        });

        //数据添加到RecyclerView
        adapter = new RecyclerAdapter(this, nameList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        //开始游戏
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.scrollBy(0, 1000);
            }
        });



    }

    //模拟玩家数据
    private void initData() {
        nameList.add("aaaa");
        nameList.add("bbbbb");
        nameList.add("cccc");
    }

    private void initView() {
        mSideBar = findViewById(R.id.side_bar);
        mRecyclerView = findViewById(R.id.recycler_namelist);
        btn_addpl = findViewById(R.id.btn_addpl);
        btn_start = findViewById(R.id.btn_start);
        et_name = findViewById(R.id.et_pl);
        scroll = findViewById(R.id.scroll);
        btn_create = findViewById(R.id.btn_create);
    }
}
