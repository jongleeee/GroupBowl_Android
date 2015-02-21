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
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


public class CreateAnnouncementActivity extends Activity {


    protected EditText announcementTitle;
    protected EditText announcementContext;
    protected Button announcementSubmit;

    protected String newTitle;
    protected String newContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);

        announcementTitle = (EditText) findViewById(R.id.announcement_title);
        announcementContext = (EditText) findViewById(R.id.announcement_context);
        announcementSubmit = (Button) findViewById(R.id.announcement_submit);

        announcementSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newTitle = announcementTitle.getText().toString().trim();
                newContext = announcementContext.getText().toString().trim();



                if ((newTitle == null) && (newContext == null)) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(CreateAnnouncementActivity.this);
                    builder.setMessage("Please fill all the information.")
                            .setTitle("Oops!")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {

                    String currentAnnouncement = CurrentGroup.getCurrentGroupName() + ParseConstants.ANNOUNCEMENT;
                    ParseObject newAnnouncement = new ParseObject(currentAnnouncement);

                    newAnnouncement.put("title", newTitle);
                    newAnnouncement.put("news", newContext);

                    newAnnouncement.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateAnnouncementActivity.this);
                                builder.setMessage("Announcement created.")
                                        .setTitle("Yes!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();
                                dialog.show();

                                Intent intent = new Intent(CreateAnnouncementActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);


                            } else {
                                System.out.println(e);
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
        getMenuInflater().inflate(R.menu.menu_create_announcement, menu);
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
