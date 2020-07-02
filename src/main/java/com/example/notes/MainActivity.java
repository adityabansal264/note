package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText mEtTitle;
    private LinearLayout mLinear;
    private RelativeLayout mRelative;
    private ArrayList<Items> addItem;
    ArrayList<Items> retrievedRemainderItemValues;
    DbHelper dataItem;
    private int row;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEtTitle = findViewById(R.id.et_title);
        mLinear = findViewById(R.id.ll_dynamic);
        mRelative = findViewById(R.id.rl_add_item);
        dataItem = new DbHelper(this);

        mRelative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                additem();
            }
        });

        addItem = new ArrayList<>();
        retrievedRemainderItemValues = new ArrayList<>();

    }

    private void additem() {
        mRelative.setEnabled(false);
        row++;
        final View view = LayoutInflater.from(this).inflate(R.layout.add_item, null);
        CheckBox check = view.findViewById(R.id.cb_add);
        final EditText blank = view.findViewById(R.id.et_blank);
        final ImageView image = view.findViewById(R.id.iv_done_add);

        blank.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 1) {
                    image.setVisibility(View.VISIBLE);
                } else {
                    image.setVisibility(view.INVISIBLE);
                }

            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRelative.setEnabled(true);
                blank.setEnabled(false);
                image.setVisibility(View.VISIBLE);

                Items item = new Items();
                item.id = row;
                item.name = blank.getText().toString();
                item.isChecked = false;

                addItem.add(item);

            }
        });

        mLinear.addView(view);
    }

    public void save(View view) {
        if (addItem.size() > 1) {
            //for adding an arrayList to JSON
            JSONArray itemsArray = new JSONArray();
            for (Items item : addItem) {

                try {
                    JSONObject obj = new JSONObject();
                    obj.put("itemId", item.id);
                    obj.put("itemName", item.name);
                    obj.put("checked", item.isChecked);
                    itemsArray.put(obj);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            //retrieving as a string from databse and convert it to json and convert it to arraylist
            String itemValue = itemsArray.toString();
            try {
                JSONArray retrievedItems = new JSONArray(itemValue);
                for (int i = 0; i < retrievedItems.length(); i++) {
                    JSONObject currentObj = retrievedItems.getJSONObject(i);

                    Items retrieved = new Items();
                    retrieved.id = currentObj.optInt("itemId");
                    retrieved.name = currentObj.optString("itemName");
                    retrieved.isChecked = currentObj.optBoolean("checked");

                    retrievedRemainderItemValues.add(retrieved);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //insertion

            String title = mEtTitle.getText().toString();
            RemainderItem notes = new RemainderItem();
            notes.title = title;
            notes.item_name = itemValue;

            dataItem.insertData(notes, dataItem.getWritableDatabase());


        }
    }
}