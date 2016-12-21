package com.example.givemepass.ormlitedemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.j256.ormlite.dao.Dao;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DBHelper mDBHelper;
    private Dao<User, Long> dao;
    private Button addData;
    private EditText inputData;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<User> usersData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
    }

    private void initData() {
        mDBHelper = new DBHelper(MainActivity.this);
        mDBHelper.createTable();
        usersData = new ArrayList<>();
        usersData.addAll(mDBHelper.queryAllData());
    }

    private void initView() {
        inputData = (EditText) findViewById(R.id.input_data);
        addData = (Button) findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = inputData.getText().toString();
                if(data == null || data.length() <= 0){
                    Toast.makeText(MainActivity.this, "請輸入資料", Toast.LENGTH_SHORT).show();
                    return;
                }
                User user = new User();
                user.setName(data);
                mDBHelper.createData(user);
                usersData.add(user);
                adapter.setData(usersData);
                adapter.notifyDataSetChanged();
                inputData.setText("");
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(MainActivity.this, mDBHelper);
        adapter.setData(usersData);
        recyclerView.setAdapter(adapter);
    }

}
