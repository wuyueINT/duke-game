package com.wuyue.countinggame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NamelistRecyclerAdapter extends RecyclerView.Adapter<NamelistRecyclerAdapter.VH> {

    List<String> nameList;
    Context context;
    NamelistRecyclerAdapter adapter;

    public NamelistRecyclerAdapter(Context context, List<String> nameList) {
        this.nameList = nameList;
        this.context = context;
        adapter = this;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, null);
        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, final int position) {
        holder.tv_name.setText(nameList.get(position));
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, nameList.size()-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private Button btn_delete;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
