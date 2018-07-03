package com.example.yuhei.shukatsuapp.todoDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoDatabaseOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE = "todo.db";
    public static final String TABLE ="todo";
    public static final int VERSION = 1;

    public TodoDatabaseOpenHelper(Context context){
        super(context,DATABASE,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE todo(date INTEGER PRIMARY KEY,todo TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
