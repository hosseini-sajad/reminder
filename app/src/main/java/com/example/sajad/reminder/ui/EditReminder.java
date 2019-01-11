package com.example.sajad.reminder.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.sajad.reminder.R;
import com.example.sajad.reminder.TimePickerFragment;
import com.example.sajad.reminder.database.DBHandler;
import com.example.sajad.reminder.receiver.MyBroadcastReceiver;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditReminder extends AppCompatActivity {

    private EditText mTittle;
    private TextView mTime;
    private TextView mDate;
    private TextView repeat;
    private Button mUpdate;
    private LinearLayout mLinearLayoutDate;
    private LinearLayout mLinearLayouttime;
    private int mYear, mMonth, mDay;
    private Calendar mCalendar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_reminder);

        mTittle = findViewById(R.id.edit_title);
        mTime = findViewById(R.id.time_text);
        mDate = findViewById(R.id.date_text);
        repeat = findViewById(R.id.repeat_text);
        mUpdate = findViewById(R.id.execute);

        mCalendar = Calendar.getInstance();
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH);
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH);

        final int id = getIntent().getIntExtra("id", 0);
        String title = getIntent().getStringExtra("title");
        String time = getIntent().getStringExtra("time");
        String date = getIntent().getStringExtra("date");
        mTittle.setText(title);
        mTime.setText(time);
        mDate.setText(date);

        String currenDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(new Date());
        mDate.setText(currenDate);

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String currentTime = dateFormat.format(Calendar.getInstance().getTime());
        mTime.setText(currentTime);

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

        mUpdate.setText("Update");
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string = mTime.getText().toString();
                // give char string for hour and time
                String hour = string.substring(0, 2);
                String minute = string.substring(3, 5);


                mCalendar.set(Calendar.YEAR, mYear);
                mCalendar.set(Calendar.MONTH, mMonth);
                mCalendar.set(Calendar.DAY_OF_MONTH, mDay);
                mCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
                mCalendar.set(Calendar.MINUTE, Integer.parseInt(minute));
                mCalendar.set(Calendar.SECOND, 0);
                mCalendar.set(Calendar.MILLISECOND, 0);

                if (System.currentTimeMillis() < mCalendar.getTimeInMillis()) {

                    //save in database
                    DBHandler db = new DBHandler(getApplication());
                    db.updateReminder(id, mTittle.getText().toString(), Integer.parseInt(hour),
                            Integer.parseInt(minute), mDate.getText().toString(), Integer.parseInt(repeat.getText().toString()));
                    new MyBroadcastReceiver().setAlarm(EditReminder.this, mCalendar.getTimeInMillis());
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
