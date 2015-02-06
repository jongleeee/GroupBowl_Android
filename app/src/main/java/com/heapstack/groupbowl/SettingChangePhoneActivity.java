package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.SaveCallback;


public class SettingChangePhoneActivity extends Activity {

    protected EditText mPhoneField;
    protected Button mPhoneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_phone);

        mPhoneField = (EditText)findViewById(R.id.changePhoneField);
        mPhoneButton = (Button)findViewById(R.id.changePhoneButton);

        mPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone = mPhoneField.toString().trim();

                if (phone.isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingChangePhoneActivity.this);
                    builder.setMessage("Please fill out all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                } else {

                    CurrentMember.getUser().put("phone", phone);
                    CurrentMember.setUserPhone(phone);
                    CurrentMember.getUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingChangePhoneActivity.this);
                                builder.setMessage("Phone changed.")
                                        .setTitle("Done!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                Intent intent = new Intent(SettingChangePhoneActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(SettingChangePhoneActivity.this);
                                builder.setMessage("Please try again.")
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
        getMenuInflater().inflate(R.menu.menu_setting_change_phone, menu);
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
