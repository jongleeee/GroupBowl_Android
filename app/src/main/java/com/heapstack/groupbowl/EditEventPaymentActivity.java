package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.Calendar;
import java.util.Date;


public class EditEventPaymentActivity extends Activity {

    protected String currentTitle;
    protected String currentContext;
    protected String currentDate;
    protected String currentFee;
    protected String currentVenmoId;
    protected String objectId;


    protected String newTitle;
    protected String newContext;
    protected String newDate;
    protected String newFee;
    protected String newVenmoId;


    protected TextView editEventTitle;
    protected TextView editEventContext;
    protected TextView editEventFee;
    protected TextView editEventVenmoId;
    protected Button editEventUpdate;


    protected TextView mEventDate;
    protected TextView mEventTime;
    protected Button mEventDateButton;
    protected Button mEventTimeButton;
    protected Calendar mCalendar;
    protected int mSelectedMonth, mSelectedDay, mSelectedYear;
    private int mSelectedHour;
    private int mSelectedMinutes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_payment);


        editEventTitle = (TextView)findViewById(R.id.editEventPaymentTitleField);
        editEventContext = (TextView)findViewById(R.id.editEventPaymentContextField);
        editEventFee = (TextView)findViewById(R.id.editEventPaymentFeeField);
        editEventVenmoId = (TextView)findViewById(R.id.editEventPaymentVenmoEmailField);
        editEventUpdate = (Button)findViewById(R.id.editEventPaymentUpdateButton);


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


        mEventDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(mSelectedYear, mSelectedMonth, mSelectedDay, mOnDateSetListener);
            }
        });


        mEventTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog(mSelectedHour, mSelectedMinutes, true, mOnTimeSetListener);
            }
        });


        editEventUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTitle = editEventTitle.getText().toString().trim();
                newContext = editEventContext.getText().toString().trim();
                newFee = editEventFee.getText().toString().trim();
                newVenmoId = editEventVenmoId.getText().toString().trim();

                if (!(newTitle.equals(currentTitle)) || !(newContext.equals(currentContext)) || !(newFee.equals(currentFee)) || !(newVenmoId.equals(currentVenmoId))) {

                    String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
                    ParseQuery<ParseObject> query = ParseQuery.getQuery(currentEvent);
                    query.whereEqualTo("objectId", objectId);
                    query.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject event, ParseException e) {
                            if (e == null) {


                                Date eventDate = new Date(mSelectedYear-1900, mSelectedMonth, mSelectedDay, mSelectedHour, mSelectedMinutes);


                                event.put("title", newTitle);
                                event.put("payment", "YES");
                                event.put("contents", newContext);
                                event.put("date", eventDate);
                                event.put("fee", Integer.parseInt(newFee));
                                event.put("venmoId", newVenmoId);


                                event.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {

                                            ParsePush push = new ParsePush();
                                            push.setChannel(CurrentGroup.getCurrentGroupName());
                                            push.setMessage(currentTitle + " has been updated");
                                            push.sendInBackground();

                                            Intent intent = new Intent(EditEventPaymentActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(EditEventPaymentActivity.this);
                                            builder.setMessage("Event has been updated.")
                                                    .setTitle("Yes!")
                                                    .setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        } else {

                                            System.out.println(e);

                                            AlertDialog.Builder builder = new AlertDialog.Builder(EditEventPaymentActivity.this);
                                            builder.setMessage("Error in saving.")
                                                    .setTitle("Oops!")
                                                    .setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        }
                                    }
                                });


                            } else {
                                System.out.println(e);
                            }
                        }
                    });





                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditEventPaymentActivity.this);
                    builder.setMessage("Please update information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_event_payment, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        currentTitle = intent.getStringExtra("title");
        currentContext = intent.getStringExtra("context");
        currentFee = intent.getStringExtra("fee");
        currentVenmoId = intent.getStringExtra("venmoId");
        objectId = intent.getStringExtra("objectId");

        editEventTitle.setText(currentTitle);
        editEventContext.setText(currentContext);
        editEventFee.setText(currentFee);
        editEventVenmoId.setText(currentVenmoId);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
            ParseQuery<ParseObject> query = ParseQuery.getQuery(currentEvent);
            query.whereEqualTo("objectId", objectId);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject event, ParseException e) {
                    if (e == null) {


                        event.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditEventPaymentActivity.this);
                                builder.setMessage("Deleted.")
                                        .setTitle("Yes!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();


                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                        Intent intent = new Intent(EditEventPaymentActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                });

                                dialog.show();

                            }
                        });


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditEventPaymentActivity.this);
                        builder.setMessage("Cannot delete.")
                                .setTitle("Oops!")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            });

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
