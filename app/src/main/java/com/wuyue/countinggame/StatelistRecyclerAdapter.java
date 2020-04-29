package com.wuyue.countinggame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StatelistRecyclerAdapter extends RecyclerView.Adapter<StatelistRecyclerAdapter.VH> {

    Context context;
    List<String> stateList;

    public StatelistRecyclerAdapter(Context context, List<String> stateList) {
        this.context = context;
        this.stateList = stateList;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item2, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.tv_item2.setText(stateList.get(position));
        holder.tv_item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //向提问列表中添加玩家名单
            }
        });
    }

    @Override
    public int getItemCount() {
        return stateList.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView tv_item2;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_item2 = itemView.findViewById(R.id.tv_item2);
        }
    }
}
