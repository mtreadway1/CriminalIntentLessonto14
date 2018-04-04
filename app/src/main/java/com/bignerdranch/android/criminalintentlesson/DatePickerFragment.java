package com.bignerdranch.android.criminalintentlesson;

import android.animation.ArgbEvaluator;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class DatePickerFragment extends DialogFragment {
    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE = "com.bignerdranch.android.criminalintent.date";


    private DatePicker mDatePicker;

    private Date mDate;
    private Button mOKButton;
    private Button mCancelButton;


    private void sendResult(int resultCode, Date date){
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        if(getTargetFragment() == null){
            getActivity().setResult(resultCode, intent);
        }else{
            getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
        }
    }

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.data_picker, container, false);

        mDate = (Date)getArguments().getSerializable(ARG_DATE);

        if(mDate == null){
            mDate = new Date();
        }

        Calendar mCal = Calendar.getInstance();
        mDatePicker = (DatePicker)v.findViewById(R.id.date_picker);
        mDatePicker.init(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH),
                mCal.get(Calendar.DAY_OF_MONTH), null);

        mOKButton = (Button) v.findViewById(R.id.ok_button);
        mOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();

                Calendar mCal = Calendar.getInstance();
                mCal.set(year, month, day);
                mDate = mCal.getTime();
                sendResult(Activity.RESULT_OK, mDate);
                getActivity().finish();

            }
        });

        mCancelButton = (Button)v.findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().finish();
            }
        });
        return v;
    }




    @Override public Dialog onCreateDialog(Bundle savedInstanceState){
        Date date  = (Date) getArguments().getSerializable(ARG_DATE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year, month, day, null);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int year = mDatePicker.getYear();
                        int month = mDatePicker.getMonth();
                        int day = mDatePicker.getDayOfMonth();
                        Date date = new GregorianCalendar(year, month, day).getTime();
                        sendResult(Activity.RESULT_OK, date);
                    }
                })
                .create();
    }
}
