package com.example.yuhei.shukatsuapp.todoDatabase;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HelpTodo implements Serializable {


    private Date date1;
    private String text1;
    private boolean fullDisplayed1;
    private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy 'at' hh:mm aaa");

    public HelpTodo() {
        this.date1 = new Date();
    }

    public HelpTodo(long time, String text) {
        this.date1 = new Date(time);
        this.text1 = text;
    }

    public String getDate() {
        return dateFormat.format(date1);
    }

    public long getTime() {
        return date1.getTime();
    }

    public void setTime(long time) {
        this.date1 = new Date(time);
    }

    public void setText(String text) {
        this.text1 = text;
    }
    public String getText() {
        return this.text1;
    }

    public String getShortText() {
        String temp = text1.replaceAll("\n", " ");
        if (temp.length() > 25) {
            return temp.substring(0, 25) + "...";
        } else {
            return temp;
        }
    }

    public void setFullDisplayed(boolean fullDisplayed) {
        this.fullDisplayed1 = fullDisplayed;
    }

    public boolean isFullDisplayed() {
        return this.fullDisplayed1;
    }
    @Override
    public String toString() {
        return this.text1;
    }
}


