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

import com.heapstack.groupbowl.R;
import com.parse.FunctionCallback;
import com.parse.GetCallback;
import com.parse.ParseCloud;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.HashMap;

public class AddMemberActivity extends Activity {

    protected EditText mMemberEmail;
    protected Button mMemberSubmit;
    protected String currentMember;
    protected String memberEmail;
    protected ArrayList<String> groupList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        mMemberEmail = (EditText)findViewById(R.id.memberEmailField);
        mMemberSubmit = (Button)findViewById(R.id.addMemberButton);

        mMemberSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                memberEmail = mMemberEmail.getText().toString().trim();

                if (memberEmail.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                    builder.setMessage("Please fill out all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    // check if there is a user
                    ParseQuery<ParseUser> userQuery = ParseUser.getQuery();
                    userQuery.whereEqualTo("email", memberEmail);
                    userQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                        @Override
                        public void done(final ParseUser user, ParseException e) {
                            if (user != null) {

                                // check if the user already added
                                currentMember = CurrentGroup.getCurrentGroupName() + ParseConstants.MEMBER;

                                groupList = (ArrayList) user.get("groups");
                                if (groupList == null) {
                                    groupList = new ArrayList<String>();
                                    groupList.add(CurrentGroup.getCurrentGroupName());
                                } else {
                                    groupList.add(CurrentGroup.getCurrentGroupName());
                                }

                                ParseQuery<ParseObject> objectQuery = ParseQuery.getQuery(currentMember);
                                objectQuery.whereEqualTo("email", memberEmail);
                                objectQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(final ParseObject parseObject, ParseException e) {
                                        if (e == null) {

                                            AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                                            builder.setMessage("Already added")
                                                    .setTitle("Oops!")
                                                    .setPositiveButton(android.R.string.ok, null);
                                            AlertDialog dialog = builder.create();
                                            dialog.show();

                                        } else {

                                            // Change user groupList
                                            HashMap<String, Object> params = new HashMap<String, Object>();
                                            params.put("userID", memberEmail);
                                            params.put("groupList", groupList);

                                            ParseCloud.callFunctionInBackground("updateMember", params, new FunctionCallback<Object>() {
                                                @Override
                                                public void done(Object o, ParseException e) {
                                                    if (e == null) {

                                                        // add User to the group
                                                        ParseObject groupMember = new ParseObject(currentMember);

                                                        groupMember.put("name", user.get("name"));
                                                        groupMember.put("email", user.get("email"));
                                                        groupMember.put("phone", user.get("phone"));
                                                        groupMember.put("title", "Member");

                                                        groupMember.saveInBackground(new SaveCallback() {
                                                            @Override
                                                            public void done(ParseException e) {
                                                                if (e == null) {

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                                                                    builder.setMessage("Member has been added!")
                                                                            .setTitle("Succeed!")
                                                                            .setPositiveButton(android.R.string.ok, null);
                                                                    AlertDialog dialog = builder.create();
                                                                    dialog.show();

                                                                    Intent intent = new Intent(AddMemberActivity.this, MainActivity.class);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                    startActivity(intent);



                                                                } else {

                                                                    AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                                                                    builder.setMessage("Error. Contact the administrator.")
                                                                            .setTitle("Oops!")
                                                                            .setPositiveButton(android.R.string.ok, null);
                                                                    AlertDialog dialog = builder.create();
                                                                    dialog.show();

                                                                }
                                                            }
                                                        });


                                                    } else {

                                                        AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                                                        builder.setMessage("Error. Please try again.")
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


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddMemberActivity.this);
                                builder.setMessage("Not Found")
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
        getMenuInflater().inflate(R.menu.add_member, menu);
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
