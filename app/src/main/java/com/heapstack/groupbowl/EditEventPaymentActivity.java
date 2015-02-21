package com.heapstack.groupbowl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class EditEventPaymentActivity extends Activity {

    protected String title;
    protected String context;
    protected String date;
    protected String fee;
    protected String venmoId;


    protected TextView editEventTitle;
    protected TextView editEventContext;
    protected TextView editEventDate;
    protected TextView editEventFee;
    protected TextView editEventVenmoId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event_payment);
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

        editEventTitle = (TextView)findViewById(R.id.editEventTitleField);
        editEventContext = (TextView)findViewById(R.id.editEventContextField);
        editEventFee = (TextView)findViewById(R.id.editEventFeeField);
        editEventVenmoId = (TextView)findViewById(R.id.editEventVenmoEmailField);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        context = intent.getStringExtra("context");
        date = intent.getStringExtra("date");
        fee = intent.getStringExtra("fee");
        venmoId = intent.getStringExtra("venmoId");


        editEventTitle.setText(title);
        editEventContext.setText(context);
        editEventFee.setText(fee);
        editEventVenmoId.setText(venmoId);

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
