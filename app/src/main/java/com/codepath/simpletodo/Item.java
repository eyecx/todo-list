package com.codepath.simpletodo;

/**
 * Created by edmundye on 1/21/15.
 */
import android.database.Cursor;

import com.activeandroid.Cache;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

@Table(name = "Items")
public class Item extends Model {
    @Column(name = "Name")
    public String name;
    public Item(){
        super();
    }

    public Item(String name){
        super();
        this.name = name;
    }
    public static Cursor fetchResultCursor() {
        String tableName = Cache.getTableInfo(Item.class).getTableName();
        // Query all items without any conditions
        String resultRecords = new Select(tableName + ".*, " + tableName + ".Id as _id").
                from(Item.class).toSql();
        // Execute query on the underlying ActiveAndroid SQLite database
        Cursor resultCursor = Cache.openDatabase().rawQuery(resultRecords, null);
        return resultCursor;
    }
}