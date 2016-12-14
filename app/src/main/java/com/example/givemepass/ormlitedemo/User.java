package com.example.givemepass.ormlitedemo;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by rick.wu on 2016/12/13.
 */
@DatabaseTable(tableName = "user")
public class User {
    @DatabaseField(generatedId = true)
    private long id;
    @DatabaseField
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
