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

import com.heapstack.groupbowl.R;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUpActivity extends Activity {

    protected EditText mEmail;
    protected EditText mPassword;
    protected EditText mName;
    protected EditText mPhoneNumber;
    protected Button mSignUpButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mEmail = (EditText)findViewById(R.id.signup_emailField);
        mPassword = (EditText)findViewById(R.id.signup_password);
        mName = (EditText)findViewById(R.id.signup_name);
        mPhoneNumber = (EditText)findViewById(R.id.signup_phone);
        mSignUpButton = (Button)findViewById(R.id.signupButton);

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String phoneNumber = mPhoneNumber.getText().toString().trim();


                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phoneNumber.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setMessage("Please fill out all the information.")
                        .setTitle("Oops!")
                        .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {

                    ParseUser newUser = new ParseUser();
                    newUser.setUsername(email);
                    newUser.setPassword(password);
                    newUser.setEmail(email);
                    newUser.put("name", name);
                    newUser.put("phone", phoneNumber);

                    newUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // Success
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                                builder.setMessage("Already registered user.")
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
        getMenuInflater().inflate(R.menu.sign_up, menu);
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
