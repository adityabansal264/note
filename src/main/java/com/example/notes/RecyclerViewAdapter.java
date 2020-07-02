package com.example.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private Context context;
    private ArrayList<RemainderItem> values;

    public RecyclerViewAdapter(Context context, ArrayList<RemainderItem> values) {

        this.context = context;
        this.values = values;

    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cell, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        final RemainderItem currentItem=values.get(position);
        holder.mTvTitle.setText(currentItem.title);
        ArrayList<Items> viewItems=new ArrayList<Items>();
        try {
            JSONArray list=new JSONArray(currentItem.item_name);
            for (int i=0;i<list.length();i++){
                JSONObject jsonObj=list.optJSONObject(i);
                Items newItem=new Items();
                newItem.id=jsonObj.optInt("item_Id");
                newItem.name=jsonObj.optString("item_Name");
                newItem.isChecked=jsonObj.optBoolean("isChecked");

                viewItems.add(newItem);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.mLinear.removeAllViews();
        for (int i=0;i<viewItems.size();i++){
            View view=LayoutInflater.from(context).inflate(R.layout.dynamic_view,null);
            TextView mTvText=view.findViewById(R.id.tv_cell_item);
            CheckBox check=view.findViewById(R.id.cb_cell_view);

            Items item=viewItems.get(i);
            mTvText.setText(item.name);
            check.isChecked();
            holder.mLinear.addView(view);
        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView mTvTitle;
        private LinearLayout mLinear;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTvTitle=itemView.findViewById(R.id.tv_cell_title);
            mLinear=itemView.findViewById(R.id.ll_cell_dynamic);
        }
    }
}
