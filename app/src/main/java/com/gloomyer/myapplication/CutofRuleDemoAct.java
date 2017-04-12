package com.gloomyer.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Gloomy on 2017/4/12.
 */

public class CutofRuleDemoAct extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutofrule);
        recyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        //纵向
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new VMyAdapter()); //纵向adapter


        //横向
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(new HMyAdapter()); //横向adapter


        //表格
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(new GMyAdapter()); //表格adapter

        recyclerView.addItemDecoration(new CutofRuleItemDecoration(this));
    }


    private class Holder extends RecyclerView.ViewHolder {

        private TextView tv;

        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView;
        }
    }


    private class GMyAdapter extends RecyclerView.Adapter<Holder> {
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(CutofRuleDemoAct.this).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText("pos:" + position);
        }

        @Override
        public int getItemCount() {
            return 90;
        }
    }

    private class HMyAdapter extends RecyclerView.Adapter<Holder> {
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            Holder holder = new Holder(LayoutInflater.from(CutofRuleDemoAct.this).inflate(android.R.layout.simple_list_item_1, parent, false));
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.tv.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.tv.setLayoutParams(layoutParams);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText("item,pos:" + position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }

    private class VMyAdapter extends RecyclerView.Adapter<Holder> {
        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(CutofRuleDemoAct.this).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.tv.setText("item,pos:" + position);
        }

        @Override
        public int getItemCount() {
            return 30;
        }
    }
}
