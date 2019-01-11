package com.example.sajad.reminder.model;

import android.content.Context;

import com.example.sajad.reminder.database.DBHandler;

/**
 * Created by sajad on 5/26/2018.
 */
public class ReminderModel {

    private int id;
    private String title;
    private int hour;
    private int minute;
    private String date;
    private int repeate;

    public ReminderModel() {
    }

    public ReminderModel(int id, String title, int hour, int minute, String date, int repeate) {
        setId(id);
        setTitle(title);
        setHour(hour);
        setMinute(minute);
        setDate(date);
        setRepeate(repeate);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRepeate() {
        return repeate;
    }

    public void setRepeate(int repeate) {
        this.repeate = repeate;
    }

    public void save(Context con){
        DBHandler dbHandler = new DBHandler(con);
        dbHandler.addReminder(getTitle(), getHour(), getMinute(), getDate(), getRepeate());
    }
}
