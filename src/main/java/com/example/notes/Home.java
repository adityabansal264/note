package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    RecyclerView mRcView;
    DbHelper database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRcView=findViewById(R.id.rv_view);
        mRcView.setLayoutManager(new GridLayoutManager(this,2));
        database=new DbHelper(this);

        getData();


    }

    private void getData(){
        ArrayList<RemainderItem> item=database.getData(database.getReadableDatabase());
        RecyclerViewAdapter adapter=new RecyclerViewAdapter(this,item);
        mRcView.setAdapter(adapter);
    }
}