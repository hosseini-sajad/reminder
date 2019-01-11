package com.example.sajad.reminder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by sajad on 7/3/2018.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    private DatePickerInterface mDatePickerInterface;
    public void setInterface(DatePickerInterface  pickerInterface) {
        mDatePickerInterface = pickerInterface;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

//        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
//        View view = layoutInflater.inflate(R.layout.activity_insert_reminder,null);

        return new DatePickerDialog(getActivity(), R.style.DialogTheme, this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView date = getActivity().findViewById(R.id.date_text);
        mDatePickerInterface.getDate(year , month , day);
        date.setText((view.getMonth()+1) + " / " + view.getDayOfMonth() + " / " +  view.getYear());
    }
}
