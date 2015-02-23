package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.DeleteCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;


public class EditAnnouncementActivity extends Activity {

    protected EditText announcementTitle;
    protected EditText announcementContext;
    protected Button announcementButton;

    protected String currentTitle;
    protected String currentContext;
    protected String objectId;

    protected String newTitle;
    protected String newContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_announcement);

        announcementTitle = (EditText) findViewById(R.id.announcement_title);
        announcementContext = (EditText) findViewById(R.id.announcement_context);
        announcementButton = (Button) findViewById(R.id.announcement_button);

    }


    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        currentTitle = intent.getStringExtra("title");
        currentContext = intent.getStringExtra("context");
        objectId = intent.getStringExtra("objectId");

        announcementTitle.setText(currentTitle);
        announcementContext.setText(currentContext);


        announcementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newTitle = announcementTitle.getText().toString().trim();
                newContext = announcementContext.getText().toString().trim();

                if (!(newTitle.isEmpty()) && !(newContext.isEmpty())) {

                    if ((currentTitle == newTitle) && (currentContext == newContext)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAnnouncementActivity.this);
                        builder.setMessage("Please update.")
                                .setTitle("Oops!")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    } else {

                        String currentAnnouncement = CurrentGroup.getCurrentGroupName() + ParseConstants.ANNOUNCEMENT;
                        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentAnnouncement);
                        query.whereEqualTo("objectId", objectId);
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject announcement, ParseException e) {
                                if (e == null) {
                                    announcement.put("title", newTitle);
                                    announcement.put("news", newContext);

                                    announcement.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e == null) {

                                                ParsePush push = new ParsePush();
                                                push.setChannel(CurrentGroup.getCurrentGroupName());
                                                push.setMessage(currentTitle + " has been updated");
                                                push.sendInBackground();


                                                AlertDialog.Builder builder = new AlertDialog.Builder(EditAnnouncementActivity.this);
                                                builder.setMessage("Updated.")
                                                        .setTitle("Yes!")
                                                        .setPositiveButton(android.R.string.ok, null);
                                                AlertDialog dialog = builder.create();

                                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                                    @Override
                                                    public void onDismiss(DialogInterface dialog) {

                                                        Intent intent = new Intent(EditAnnouncementActivity.this, MainActivity.class);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);

                                                    }
                                                });

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
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_announcement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            String currentAnnouncement = CurrentGroup.getCurrentGroupName() + ParseConstants.ANNOUNCEMENT;
            ParseQuery<ParseObject> query = ParseQuery.getQuery(currentAnnouncement);
            query.whereEqualTo("objectId", objectId);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject event, ParseException e) {
                    if (e == null) {


                        event.deleteInBackground(new DeleteCallback() {
                            @Override
                            public void done(ParseException e) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditAnnouncementActivity.this);
                                builder.setMessage("Deleted.")
                                        .setTitle("Yes!")
                                        .setPositiveButton(android.R.string.ok, null);
                                AlertDialog dialog = builder.create();


                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                        Intent intent = new Intent(EditAnnouncementActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);

                                    }
                                });

                                dialog.show();

                            }
                        });


                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditAnnouncementActivity.this);
                        builder.setMessage("Cannot delete.")
                                .setTitle("Oops!")
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                }
            });


            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
