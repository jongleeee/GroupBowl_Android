package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;


public class CreateEventActivity extends Activity {

    protected EditText mEventTitle;
    protected EditText mEventContext;
    protected TextView mEventDate;
    protected TextView mEventTime;
    protected Button mEventDateButton;
    protected Button mEventTimeButton;
    protected Calendar mCalendar;
    protected Button mEventSubmit;
    protected int mSelectedMonth, mSelectedDay, mSelectedYear;
    private int mSelectedHour;
    private int mSelectedMinutes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);


        mEventTitle = (EditText)findViewById(R.id.eventTitleField);
        mEventContext = (EditText)findViewById(R.id.eventContextField);

        mEventSubmit = (Button)findViewById(R.id.eventPaymentSubmit);
        mEventDateButton = (Button)findViewById(R.id.setDateButton);
        mEventTimeButton = (Button)findViewById(R.id.setTimeButton);

        mEventDate = (TextView) findViewById(R.id.dateSelected);
        mEventTime = (TextView) findViewById(R.id.timeSelected);
        mCalendar = Calendar.getInstance();
        mSelectedYear = mCalendar.get(Calendar.YEAR);
        mSelectedMonth = mCalendar.get(Calendar.MONTH);
        mSelectedDay = mCalendar.get(Calendar.DAY_OF_MONTH);
        mSelectedHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mSelectedMinutes = mCalendar.get(Calendar.MINUTE);

        updateDateUI();
        updateTimeUI();

        mEventDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mSelectedYear, mSelectedMonth, mSelectedDay, mOnDateSetListener);
            }
        });


        mEventTimeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(mSelectedHour, mSelectedMinutes, true, mOnTimeSetListener);
            }
        });

        mEventSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = mEventTitle.getText().toString().trim();
                String context = mEventContext.getText().toString().trim();

                if (title.isEmpty() || context.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                    builder.setMessage("Please fill out all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {


                    String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
                    ParseObject event = new ParseObject(currentEvent);

                    Date date = new Date(mSelectedYear-1900, mSelectedMonth, mSelectedDay, mSelectedHour, mSelectedMinutes);



                    event.put("title", title);
                    event.put("payment", "NO");
                    event.put("contents", context);
                    event.put("date", date);




                    event.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Intent intent = new Intent(CreateEventActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                                builder.setMessage("Event has been created.")
                                        .setTitle("Yes!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventActivity.this);
                                builder.setMessage("Error in saving.")
                                        .setTitle("Oops!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                            }
                        }
                    });


                }


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private DatePickerDialog.OnDateSetListener mOnDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mSelectedDay = dayOfMonth;
            mSelectedMonth = monthOfYear;
            mSelectedYear = year;

            updateDateUI();
        }
    };


    private TimePickerDialog.OnTimeSetListener mOnTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // update the current variables (hour and minutes)
            mSelectedHour = hourOfDay;
            mSelectedMinutes = minute;

            // update txtTime with the selected time
            updateTimeUI();
        }
    };


    private void updateDateUI() {
        String month = (((mSelectedMonth+1) > 9) ? ""+(mSelectedMonth+1): "0"+(mSelectedMonth+1));
        String day = (((mSelectedDay) < 10) ? "0"+(mSelectedDay): ""+(mSelectedDay));
        mEventDate.setText(month+"/"+day+"/"+mSelectedYear);
    }

    private void updateTimeUI() {
        String hour = (mSelectedHour > 9) ? ""+mSelectedHour: "0"+mSelectedHour ;
        String minutes = (mSelectedMinutes > 9) ?""+mSelectedMinutes : "0"+mSelectedMinutes;
        mEventTime.setText(hour + ":" + minutes);
    }


    // initialize the DatePickerDialog
    private DatePickerDialog showDatePickerDialog(int initialYear, int initialMonth, int initialDay, DatePickerDialog.OnDateSetListener listener) {
        DatePickerDialog dialog = new DatePickerDialog(this, listener, initialYear, initialMonth, initialDay);
        dialog.show();
        return dialog;
    }

    // initialize the TimePickerDialog
    private TimePickerDialog showTimePickerDialog(int initialHour, int initialMinutes, boolean is24Hour, TimePickerDialog.OnTimeSetListener listener) {
        TimePickerDialog dialog = new TimePickerDialog(this, listener, initialHour, initialMinutes, is24Hour);
        dialog.show();
        return dialog;
    }

}
