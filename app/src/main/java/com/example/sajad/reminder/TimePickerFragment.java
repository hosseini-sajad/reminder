package com.example.sajad.reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by sajad on 7/3/2018.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        TextView time = getActivity().findViewById(R.id.time_text);
        time.setText(String.format("%02d:%02d", timePicker.getCurrentHour(), timePicker.getCurrentMinute()));
    }
}
