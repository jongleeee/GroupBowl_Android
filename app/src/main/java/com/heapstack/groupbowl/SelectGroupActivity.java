package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;


public class SelectGroupActivity extends ListActivity {

    public static Array userGroup;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spinner = (ProgressBar) findViewById(R.id.progressBarMember);
        spinner.setVisibility(View.GONE);
        setContentView(R.layout.activity_select_group);
    }



    @Override
    public void onResume() {
        super.onResume();


        String userEmail = CurrentMember.getUserEmail();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", userEmail);
        spinner.setVisibility(View.VISIBLE);
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                spinner.setVisibility(View.GONE);

                if (e == null) {
                    userGroup = (Array) parseUser.get("groups");

                    String[] userGroups = new String[Array.getLength(userGroup)];

                    for (int i = 0; i < Array.getLength(userGroup); i++) {

                        userGroups[i] = (String) Array.get(userGroup, i);
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectGroupActivity.this,
                            android.R.layout.simple_list_item_1, userGroups);
                    setListAdapter(adapter);



                } else {

                }
            }

        });





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_group, menu);
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
