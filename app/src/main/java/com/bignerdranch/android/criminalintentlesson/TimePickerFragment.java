package com.bignerdranch.android.criminalintentlesson;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;



public class TimePickerFragment extends DialogFragment {

    public static final String EXTRA_TIME = "com.bignerdranch.jameselliott.criminalintent.time";
    private Date mTime;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }


    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, mTime);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mTime = (Date)getArguments().getSerializable(EXTRA_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mTime);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);

        TimePicker timePicker = (TimePicker)view.findViewById(R.id.dialog_time_picker);
        timePicker.setCurrentHour(hour);
        timePicker.setCurrentMinute(minutes);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(mTime);
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                mTime = cal.getTime();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(view)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
}
