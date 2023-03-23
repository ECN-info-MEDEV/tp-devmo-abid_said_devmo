package com.ecn.birthday.ui.input;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    public static final String REQUEST_DATE_KEY = "date-picker";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // get date from savedInstanceState if it exists
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = 1990;
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        // create a date object
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Bundle result = new Bundle();
        result.putSerializable("date", calendar.getTime());
        getParentFragmentManager().setFragmentResult(REQUEST_DATE_KEY, result);
    }
}
