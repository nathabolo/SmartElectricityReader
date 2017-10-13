package com.example.android.electricreader;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


/**
 * A simple {@link DialogFragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private int day;
    private int year;
    private int month;
    private int currentYear = 2017;
    private int currentMonth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, day, month, year);
    }

    @Override
    public void onDateSet(DatePicker view, int dayOfMonth, int month, int year) {

        // Do something with the date chosen by the user
        EditText calender= (EditText) getActivity().findViewById(R.id.selectDate);

        // Show selected date
        calender.setText(new StringBuilder().append(dayOfMonth)
                .append("-").append(month + 1).append("-").append(year)
                .append(" "));

        if (year != currentYear){

            Toast.makeText(getContext(), "Please select the current year", Toast.LENGTH_SHORT).show();
            return;
        }

        if (month > currentMonth){

            Toast.makeText(getContext(), "Please select the current months", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            Toast.makeText(getContext(), "Thhanks for your entry", Toast.LENGTH_SHORT).show();
            return;
        }

    }

}
