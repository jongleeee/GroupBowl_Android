package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Date;


public class CreateEventPaymentActivity extends Activity {

    protected EditText mEventTitle;
    protected EditText mEventVenmoEmail;
    protected EditText mEventContext;
    protected EditText mEventFee;
    protected DatePicker mEventDate;
    protected TimePicker mEventTime;
    protected Button mEventSubmit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_payment);


        mEventTitle = (EditText)findViewById(R.id.eventTitleField);
        mEventVenmoEmail = (EditText)findViewById(R.id.eventVenmoEmailField);
        mEventContext = (EditText)findViewById(R.id.eventContextField);
        mEventSubmit = (Button)findViewById(R.id.eventPaymentSubmit);
        mEventFee = (EditText)findViewById(R.id.eventFeeField);
        mEventDate = (DatePicker)findViewById(R.id.eventDate);
        mEventTime = (TimePicker)findViewById(R.id.eventTime);


        mEventSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEventTitle.getText().toString().trim();
                String venmoEmail = mEventVenmoEmail.getText().toString().trim();
                String context = mEventContext.getText().toString().trim();
                String fee = mEventFee.getText().toString().trim();


                if (title.isEmpty() || venmoEmail.isEmpty() || context.isEmpty() || fee.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventPaymentActivity.this);
                    builder.setMessage("Please fill out all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
                    ParseObject event = new ParseObject(currentEvent);

                    Date eventDate = new Date(mEventDate.getYear(), mEventDate.getMonth(), mEventDate.getDayOfMonth(), mEventTime.getCurrentHour(), mEventTime.getCurrentMinute());

                    event.put("title", title);
                    event.put("venmoId", venmoEmail);
                    event.put("payment", "YES");
                    event.put("fee", Integer.parseInt(fee));
                    event.put("contents", context);
                    event.put("date", eventDate);


                    event.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                Intent intent = new Intent(CreateEventPaymentActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateEventPaymentActivity.this);
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
        getMenuInflater().inflate(R.menu.menu_create_event_payment, menu);
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
}
