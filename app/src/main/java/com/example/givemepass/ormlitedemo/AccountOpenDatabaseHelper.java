package com.example.givemepass.ormlitedemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by rick.wu on 2016/12/13.
 */

public class AccountOpenDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String databaseName = "user";
    private static final int databaseVersion = 1;
    private Dao<User, Long> accountDao;
    public AccountOpenDatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
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

    public Dao<User, Long> getAccountDao(){
        if(accountDao == null){
            try {
                accountDao = getDao(User.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return accountDao;
    }
}
