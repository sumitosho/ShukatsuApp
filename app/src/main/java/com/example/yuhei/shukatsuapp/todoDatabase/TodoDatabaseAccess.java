package com.example.yuhei.shukatsuapp.todoDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoDatabaseAccess {

    private SQLiteDatabase database2;
    private TodoDatabaseOpenHelper openHelper2;
    private static volatile TodoDatabaseAccess instance2;

    private TodoDatabaseAccess(Context context){
        this.openHelper2 = new TodoDatabaseOpenHelper(context);
    }

    public static synchronized TodoDatabaseAccess getInstance(Context context){
        if (instance2==null){
            instance2 = new TodoDatabaseAccess(context);
        }
        return instance2;
    }
    public void open(){
        this.database2 = openHelper2.getWritableDatabase();
    }

    public void close(){
        if (database2 != null) {
            database2.close();
        }
    }

    public void save(HelpTodo todo){
        ContentValues values = new ContentValues();
        values.put("date",todo.getTime());
        values.put("todo",todo.getText());
        database2.insert(TodoDatabaseOpenHelper.TABLE,null,values);
    }

    public void update(HelpTodo todo){
        ContentValues values = new ContentValues();
        values.put("date", new Date().getTime());
        values.put("todo", todo.getText());
        String date = Long.toString(todo.getTime());
        database2.update(TodoDatabaseOpenHelper.TABLE,values,"date = ?",new String[]{date});

    }
    public void delete(HelpTodo todo) {
        String date = Long.toString(todo.getTime());
        database2.delete(TodoDatabaseOpenHelper.TABLE,"date =?",new String[]{date});

    }
    public List getAllTodos(){
        List todos = new ArrayList<>();
        Cursor cursor = database2.rawQuery("SELECT * From todo ORDER BY date DESC",null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            long time = cursor.getLong(0);
            String text = cursor.getString(1);
            todos.add(new HelpTodo(time,text));
            cursor.moveToNext();
        }
        cursor.close();
        return todos;
    }

}
