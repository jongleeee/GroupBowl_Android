package com.heapstack.groupbowl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class CreateEventOptionActivity extends Activity {

    protected RadioGroup mEventOptionRadioGroup;
    protected RadioButton mEventOptionRadio;
    protected Button mEventSelectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_option);

        addRadioButtonListener();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_create_event_option, menu);
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

    public void addRadioButtonListener() {

        mEventOptionRadioGroup = (RadioGroup)findViewById(R.id.eventRadioGroup);
        mEventSelectButton = (Button)findViewById(R.id.eventOptionNextButton);

        mEventSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedEvent = mEventOptionRadioGroup.getCheckedRadioButtonId();

                mEventOptionRadio = (RadioButton)findViewById(selectedEvent);

                String eventOption = mEventOptionRadio.getText().toString();

                if (eventOption.equals("Normal")) {

                    Intent intent = new Intent(CreateEventOptionActivity.this, CreateEventActivity.class);
                    startActivity(intent);


                } else {

                    Intent intent = new Intent(CreateEventOptionActivity.this, CreateEventPaymentActivity.class);
                    startActivity(intent);

                }

            }
        });
    }

}
