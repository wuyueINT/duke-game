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
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private com.gjiazhe.wavesidebar.WaveSideBar mSideBar;
    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private List<String> nameList;
    private List<String> stateList;
    private List<String> questionList;
    private ScrollView scroll;
    public NamelistRecyclerAdapter adapter;
    public StatelistRecyclerAdapter adapter2;
    public QuestionlistRecyclerAdapter adapter3;
    public Timer timer;

    EditText et_name;
    Button btn_addpl;
    Button btn_start;
    Button btn_create;
    Button btn_laststate;
    Button btn_nextstate;
    Button btn_lastquestion;
    Button btn_nextquestion;
    Button btn_time;

    //发言人序号
    private int i;
    String lastquestion = null;
    int total_time = 300;
    private boolean timerIsCanceled = true;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameList = new ArrayList<>();
        stateList = new ArrayList<>();
        questionList = new ArrayList<>();
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
                        scroll.scrollTo(0, btn_create.getTop());
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
                scroll.scrollBy(0, btn_create.getTop());
                if (nameList.size()>0){
                    stateList.addAll(nameList);
                    stateList.set(0, stateList.get(0)+"*");
                    adapter2 = new StatelistRecyclerAdapter(MainActivity.this, questionList, stateList, adapter3);
                    mRecyclerView2.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    mRecyclerView2.setAdapter(adapter2);
                }
            }
        });
        i = 0;

        //第二页的内容
        //随机为玩家产生发言名单
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nameList.size()>0){
                    //打乱列表的顺序并显示
                    Collections.shuffle(stateList);
                    adapter2.notifyDataSetChanged();
                }
            }
        });

        //选择下一个发言人
        btn_nextstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i<stateList.size()-1){
                    String s = stateList.get(i);
                    stateList.set(i, s.substring(0, s.length()-1));
                    adapter2.notifyItemChanged(i);
                    i++;
                    stateList.set(i, stateList.get(i)+"*");
                    adapter2.notifyItemChanged(i);
                }
            }
        });

        //选择上一个发言人
        btn_laststate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i>0){
                    String s = stateList.get(i);
                    stateList.set(i, s.substring(0, s.length()-1));
                    adapter2.notifyItemChanged(i);
                    i--;
                    stateList.set(i, stateList.get(i)+"*");
                    adapter2.notifyItemChanged(i);
                }
            }
        });

        //提问人名单
        adapter3 = new QuestionlistRecyclerAdapter(this, questionList);
        mRecyclerView3.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView3.setAdapter(adapter3);

        //倒数计时模块，这里用一个button上的text来显示时间
        //点击开始计时
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerIsCanceled){
                    timerIsCanceled = false;
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    total_time--;
                                    String s = total_time + "s";
                                    btn_time.setText(s);
                                    if (total_time<=0){
                                        timer.cancel();
                                        timerIsCanceled = true;
                                    }
                                }
                            });
                        }
                    }, 1000, 1000);
                }
            }
        });

        //长按重置时间
        btn_time.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                timer.cancel();
                timerIsCanceled = true;
                btn_time.setText("300s");
                total_time = 300;
                return true;
            }
        });


        //上一个提问人
        btn_lastquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastquestion!=null){
                    questionList.add(0, lastquestion);
                    adapter3.notifyItemInserted(0);
                    adapter3.notifyItemRangeChanged(1, questionList.size()-1);
                }
            }
        });

        //下一个提问人
        btn_nextquestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastquestion = questionList.get(0);
                questionList.remove(0);
                adapter3.notifyItemRemoved(0);
                adapter3.notifyItemRangeChanged(1, questionList.size()-1);
            }
        });

    }

    //模拟玩家数据
    private void initData() {
        nameList.add("咸鱼");
        nameList.add("婷婷");
        nameList.add("佳玉");
        nameList.add("元甲");
        nameList.add("商商");
        nameList.add("灰岩");
    }

    private void initView() {
        mSideBar = findViewById(R.id.side_bar);
        mRecyclerView = findViewById(R.id.recycler_namelist);
        mRecyclerView2 = findViewById(R.id.recycler_statelist);
        mRecyclerView3 = findViewById(R.id.recycler_questionlist);
        btn_addpl = findViewById(R.id.btn_addpl);
        btn_start = findViewById(R.id.btn_start);
        et_name = findViewById(R.id.et_pl);
        scroll = findViewById(R.id.scroll);
        btn_create = findViewById(R.id.btn_create);
        btn_laststate = findViewById(R.id.btn_laststate);
        btn_nextstate = findViewById(R.id.btn_nextstate);
        btn_lastquestion = findViewById(R.id.btn_lastquestion);
        btn_nextquestion = findViewById(R.id.btn_nextquestion);
        btn_time = findViewById(R.id.btn_time);
    }


}
