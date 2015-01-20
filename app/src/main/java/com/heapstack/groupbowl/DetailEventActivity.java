package com.heapstack.groupbowl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.heapstack.groupbowl.R;

public class DetailEventActivity extends Activity {

    protected TextView eventTitle;
    protected TextView eventContext;
    protected TextView eventDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
    }


    @Override
    protected void onResume() {
        super.onResume();

        eventTitle = (TextView) findViewById(R.id.detailEventTitle);
        eventContext = (TextView) findViewById(R.id.detailEventContext);
        eventDate = (TextView) findViewById(R.id.detailEventDate);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String context = intent.getStringExtra("context");
        String date = intent.getStringExtra("date");


        eventTitle.setText(title);
        eventContext.setText(context);
        eventDate.setText(date);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
