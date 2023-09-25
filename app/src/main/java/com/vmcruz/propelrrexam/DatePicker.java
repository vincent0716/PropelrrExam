package com.vmcruz.propelrrexam;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    EditText edt_bdate;
    TextView tv_age;
    int month;

    public DatePicker(EditText edt_bdate,TextView tv_age) {
        this.edt_bdate = edt_bdate;
        this.tv_age=tv_age;
    }

    @NonNull
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(requireActivity(), R.style.CalendarDialogStyle , this, year, month, day);
        dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
        return  dialog;
    }


    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        month=month+1;
        edt_bdate.setText(new StringBuilder().append(i1).append("/")
                .append(i2).append("/").append(i));
        tv_age.setText(String.valueOf(calculateAge(edt_bdate.getText().toString())));

    }

    public static int calculateAge(String dateString) {
        try {
            // Define the date format for parsing
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

            Date dateOfBirth = sdf.parse(dateString);

            // Get the current date
            Calendar currentDate = Calendar.getInstance();

            // Get the birth date as a Calendar object
            Calendar birthDate = Calendar.getInstance();
            if (dateOfBirth != null) {
                birthDate.setTime(dateOfBirth);
            }

            // Calculate the age
            int age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

            // Check if the birth date has occurred this year
            if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            return age;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1; // Return -1 to indicate an error
        }
    }
}
