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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AccountOpenDatabaseHelper mAccountOpenDatabaseHelper;
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
        mAccountOpenDatabaseHelper = new AccountOpenDatabaseHelper(MainActivity.this);
        mAccountOpenDatabaseHelper.createTable();
        dao = mAccountOpenDatabaseHelper.getAccountDao();
        usersData = new ArrayList<>();
        usersData.addAll(queryDb());
    }

    private List<User> queryDb(){
        List<User> users = new ArrayList<>();
        try {
            users = dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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
                try {
                    dao.create(user);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                usersData.add(user);
                adapter.setData(usersData);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
    }
}
