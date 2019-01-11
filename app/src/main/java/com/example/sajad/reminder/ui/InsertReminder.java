package com.example.sajad.reminder.ui;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sajad.reminder.DatePickerFragment;
import com.example.sajad.reminder.DatePickerInterface;
import com.example.sajad.reminder.receiver.MyBroadcastReceiver;
import com.example.sajad.reminder.R;
import com.example.sajad.reminder.TimePickerFragment;
import com.example.sajad.reminder.model.ReminderModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class InsertReminder extends AppCompatActivity{

    private EditText title;
    private TextView date;
    private TextView time;
    private TextView repeat;
    private Button mButton;
    private LinearLayout mLinearLayoutDate;
    private LinearLayout mLinearLayouttime;
    private int mYear,mMonth,mDay;
    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_reminder);

         title = findViewById(R.id.edit_title);
         date = findViewById(R.id.date_text);
         time = findViewById(R.id.time_text);
         repeat = findViewById(R.id.repeat_text);

         mCalendar = Calendar.getInstance();
         mYear = mCalendar.get(Calendar.YEAR);
         mMonth = mCalendar.get(Calendar.MONTH);
         mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        String currenDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        date.setText(currenDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(Calendar.getInstance().getTime());
        time.setText(currentTime);

         mLinearLayoutDate = findViewById(R.id.linear_date);
         mLinearLayoutDate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DatePickerFragment newFragment = new DatePickerFragment();
                 newFragment.setInterface(new DatePickerInterface() {
                     @Override
                     public void getDate(int year, int month, int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;
                     }
                 });
                 newFragment.show(getSupportFragmentManager(), "date picker");
             }
         });

         mLinearLayouttime = findViewById(R.id.linear_time);
         mLinearLayouttime.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 DialogFragment newFragment = new TimePickerFragment();
                 newFragment.show(getSupportFragmentManager(), "time picker");
             }
         });


         mButton = findViewById(R.id.execute);
         mButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String string = time.getText().toString();
                 // give char string for hour and time
                 String hour = string.substring(0, 2);
                 String minute = string.substring(3, 5);


                 mCalendar.set(Calendar.YEAR , mYear);
                 mCalendar.set(Calendar.MONTH , mMonth);
                 mCalendar.set(Calendar.DAY_OF_MONTH , mDay);
                 mCalendar.set(Calendar.HOUR_OF_DAY , Integer.parseInt(hour));
                 mCalendar.set(Calendar.MINUTE , Integer.parseInt(minute));
                 mCalendar.set(Calendar.SECOND , 0);
                 mCalendar.set(Calendar.MILLISECOND , 0);

                 if(System.currentTimeMillis()< mCalendar.getTimeInMillis()){
                 ReminderModel reminderModel = new ReminderModel(0, title.getText().toString(), Integer.parseInt(hour),
                         Integer.parseInt(minute), date.getText().toString(), Integer.parseInt(repeat.getText().toString()));
              //save in database
                 reminderModel.save(getApplication());
                 new MyBroadcastReceiver().setAlarm(InsertReminder.this, mCalendar.getTimeInMillis());
                 finish();} else {
                     Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                 }
             }
         });
    }

}
