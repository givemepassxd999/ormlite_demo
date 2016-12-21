package com.example.givemepass.ormlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rick.wu on 2016/12/13.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "user";
    private static final int databaseVersion = 1;
    private Dao<User, Long> accountDao;
    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
        if(accountDao == null){
            try {
                accountDao = getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, User.class, false);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTable(){
        try {
            TableUtils.createTableIfNotExists(connectionSource, User.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> queryAllData(){
        List<User> users = new ArrayList<>();
        try {
            users = accountDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void createData(User user){
        try {
            accountDao.create(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editData(User user){
        try {
            accountDao.update(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delData(User user){
        try {
            accountDao.delete(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
