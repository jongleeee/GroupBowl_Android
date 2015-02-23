package com.heapstack.groupbowl;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.heapstack.groupbowl.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import org.w3c.dom.Text;

public class LoginActivity extends Activity {

    protected TextView mSignUpButton;
    protected EditText mEmail;
    protected EditText mPassword;
    protected Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        mSignUpButton = (TextView)findViewById(R.id.signupButton);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });



        mEmail = (EditText)findViewById(R.id.login_emailField);
        mPassword = (EditText)findViewById(R.id.login_passwordfield);
        mLoginButton = (Button)findViewById(R.id.loginButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Loggin in...", Toast.LENGTH_SHORT).show();


                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();


                if (email.isEmpty() || password.isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setMessage("Please fill out all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                } else {

                    setProgressBarIndeterminateVisibility(true);
                    ParseUser.logInInBackground(email, password, new LogInCallback() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            setProgressBarIndeterminateVisibility(false);
                            if (e == null) {
                                // Success
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            } else {

                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Incorrect information.")
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
        getMenuInflater().inflate(R.menu.login, menu);
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
