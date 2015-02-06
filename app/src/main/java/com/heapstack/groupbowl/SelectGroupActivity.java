package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class SelectGroupActivity extends ListActivity {

    public static ArrayList userGroup;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_select_group);
    }


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {


        return super.onCreateView(name, context, attrs);
    }



    @Override
    public void onResume() {
        super.onResume();

        spinner = (ProgressBar) findViewById(R.id.progressBarGroup);
        spinner.setVisibility(View.GONE);



        String userEmail = CurrentMember.getUserEmail();

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", userEmail);
        spinner.setVisibility(View.VISIBLE);
        query.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {

                spinner.setVisibility(View.GONE);





                if (e == null) {



                    if (parseUser.get("groups")!= null) {

                        userGroup = (ArrayList) parseUser.get("groups");

                        String[] userGroups = new String[userGroup.size()];

                        int i = 0;
                        for (Object group : userGroup) {

                            userGroups[i] = (String) group;
                            i++;
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectGroupActivity.this,
                                android.R.layout.simple_list_item_1, userGroups);
                        setListAdapter(adapter);

                    }


                } else {

                }
            }

        });





    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        CurrentGroup.setCurrentGroupName((String) userGroup.get(position));



        String currentGroupMemberClass = userGroup.get(position) + ParseConstants.MEMBER;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentGroupMemberClass);
        query.whereEqualTo("email", CurrentMember.getUserEmail());

        spinner.setVisibility(View.VISIBLE);
        query.getFirstInBackground(new GetCallback<ParseObject>() {



            @Override
            public void done(ParseObject user, ParseException e) {

                spinner.setVisibility(View.GONE);

                if (e == null) {

                    CurrentGroup.setCurrentTitle((String) user.get("title"));
                    System.out.println(CurrentGroup.getCurrentTitle());

                    CurrentMember.setUser(user);
                    CurrentMember.setUserPhone((String) user.get("phone"));
                    CurrentMember.setUserEmail((String) user.get("email"));
                    CurrentMember.setUserName((String) user.get("name"));

                    Intent intent = new Intent(SelectGroupActivity.this, MainActivity.class);

                    startActivity(intent);
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

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            ParseUser.logOut();
            navigateToLogin();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
