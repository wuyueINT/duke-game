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

public class QuestionlistRecyclerAdapter extends RecyclerView.Adapter<QuestionlistRecyclerAdapter.VH> {

    private List<String> questionList;
    Context context;
    QuestionlistRecyclerAdapter adapter;

    public QuestionlistRecyclerAdapter(Context context, List<String> questionList) {
        this.questionList = questionList;
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
        holder.tv.setText(questionList.get(position));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, questionList.size()-1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }

    public class VH extends RecyclerView.ViewHolder{

        private TextView tv;
        private Button btn;

        public VH(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_name);
            btn = itemView.findViewById(R.id.btn_delete);
        }
    }

}
