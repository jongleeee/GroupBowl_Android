package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;

import java.util.List;


public class EventAttendingListActivity extends ListActivity {

    protected List<ParseObject> mAttendee;
    private ProgressBar spinner;

    protected String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_attending_list);

        Intent intent = getIntent();
        objectId = intent.getStringExtra("objectId");
        spinner = (ProgressBar) findViewById(R.id.progressBarEventAttendingList);


    }


    @Override
    protected void onResume() {
        spinner.setVisibility(View.GONE);
        super.onResume();

        String attendingList = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
        ParseQuery<ParseObject> query = ParseQuery.getQuery(attendingList);
        query.whereEqualTo("objectId", objectId);

        spinner.setVisibility(View.VISIBLE);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject event, ParseException e) {

                spinner.setVisibility(View.GONE);

                if (e == null) {

                    ParseRelation<ParseObject> attendeeRelation = (ParseRelation) event.get("attend");
                    System.out.println(attendeeRelation);
                    ParseQuery<ParseObject> attendee = attendeeRelation.getQuery();
                    attendee.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> attendees, ParseException e) {
                            if (e == null) {
                                mAttendee = attendees;
                                String[] attendeeNames = new String[mAttendee.size()];
                                int i = 0;
                                for (ParseObject attendee : mAttendee) {
                                    attendeeNames[i] = (String) attendee.get("name");
                                    i++;
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                                        EventAttendingListActivity.this,
                                        android.R.layout.simple_list_item_1,
                                        attendeeNames
                                );

                                setListAdapter(adapter);

                            } else {
                                System.out.println(e);
                            }
                        }
                    });

                } else {
                    System.out.println(e);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_attending_list, menu);
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
