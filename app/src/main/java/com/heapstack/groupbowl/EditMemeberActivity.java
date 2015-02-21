package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;


public class EditMemeberActivity extends Activity {

    protected RadioGroup mMemberOptionRadioGroup;
    protected RadioButton mMemberOptionRadio;
    protected Button mMemberSelectButton;
    protected String memberEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_memeber);

        addRadioButtonListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        memberEmail = intent.getStringExtra("email");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_memeber, menu);
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

        mMemberOptionRadioGroup = (RadioGroup)findViewById(R.id.memberRadioGroup);
        mMemberSelectButton = (Button)findViewById(R.id.memberOptionUpdateButton);

        mMemberSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedEvent = mMemberOptionRadioGroup.getCheckedRadioButtonId();

                mMemberOptionRadio = (RadioButton)findViewById(selectedEvent);

                String memberOption = mMemberOptionRadio.getText().toString();

                if (memberOption.equals("Leader")) {

                    changeTitle("Leader");


                } else {

                    changeTitle("Member");


                }

            }
        });
    }


    public void changeTitle(String title) {

        final String newTitle = title;

        String currentMember = CurrentGroup.getCurrentGroupName() + ParseConstants.MEMBER;

        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentMember);
        query.whereEqualTo("email", memberEmail);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject user, ParseException e) {
                if (e == null) {

                    user.put("title", newTitle);
                    user.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(EditMemeberActivity.this);
                                builder.setMessage("Title has been fixed.")
                                        .setTitle("Yes!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

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


}

