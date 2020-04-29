package com.wuyue.countinggame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gjiazhe.wavesidebar.WaveSideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private com.gjiazhe.wavesidebar.WaveSideBar mSideBar;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private List<String> nameList;
    private ScrollView scroll;
    public NamelistRecyclerAdapter adapter;
    public NamelistRecyclerAdapter adapter2;

    EditText et_name;
    Button btn_addpl;
    Button btn_start;
    Button btn_create;
    TextView tv_statelist;
    Button btn_laststate;
    Button btn_nextstate;

    //发言人序号
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameList = new ArrayList<>();
        initView();
        initData();

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
        adapter = new NamelistRecyclerAdapter(this, nameList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        //开始游戏
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scroll.scrollBy(0, 1000);
            }
        });
        i = 1;

        //第二页的内容
        //随机为玩家产生发言名单
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameList.size()>0){
                    //打乱列表的顺序并显示
                    Collections.shuffle(nameList);
                    tv_statelist.setText(createStateList());
                    adapter2 = new NamelistRecyclerAdapter(MainActivity.this, nameList);
                    mRecyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mRecyclerView2.setAdapter(adapter2);
                }
            }
        });

        //选择下一个发言人
        btn_nextstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<nameList.size()){
                    i++;
                    tv_statelist.setText(createStateList());
                }
            }
        });

        //选择上一个发言人
        btn_laststate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>1){
                    i--;
                    tv_statelist.setText(createStateList());
                }
            }
        });




    }

    //模拟玩家数据
    private void initData() {
        nameList.add("aaaa");
        nameList.add("bbbbb");
        nameList.add("cccc");
        nameList.add("dddd");
        nameList.add("eee");
        nameList.add("ffffff");
    }

    private void initView() {
        mSideBar = findViewById(R.id.side_bar);
        mRecyclerView = findViewById(R.id.recycler_namelist);
        mRecyclerView2 = findViewById(R.id.recycler_statelist);
        btn_addpl = findViewById(R.id.btn_addpl);
        btn_start = findViewById(R.id.btn_start);
        et_name = findViewById(R.id.et_pl);
        scroll = findViewById(R.id.scroll);
        btn_create = findViewById(R.id.btn_create);
        tv_statelist = findViewById(R.id.tv_statelist);
        btn_laststate = findViewById(R.id.btn_laststate);
        btn_nextstate = findViewById(R.id.btn_nextstate);

    }

    //生成发言人列表
    private String createStateList(){

        StringBuilder sb = new StringBuilder();
        for (int j=0; j<nameList.size(); j++){
            if (i==j){
                sb.append(nameList.get(j)).append("*").append("\n");
            } else {
                sb.append(nameList.get(j)).append("\n");
            }
        }
        return sb.toString();
    }
}
