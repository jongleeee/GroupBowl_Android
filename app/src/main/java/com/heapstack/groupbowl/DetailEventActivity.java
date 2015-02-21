package com.heapstack.groupbowl;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.heapstack.groupbowl.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.SaveCallback;
import com.heapstack.groupbowl.VenmoLibrary.VenmoResponse;

public class DetailEventActivity extends Activity {

    protected TextView eventTitle;
    protected TextView eventContext;
    protected TextView eventDate;
    protected String payment;
    protected String fee;
    protected String venmoId;
    protected String title;
    protected String eventId;
    protected String context;
    protected String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
    }


    @Override
    protected void onResume() {
        super.onResume();

        eventTitle = (TextView) findViewById(R.id.detailEventTitle);
        eventContext = (TextView) findViewById(R.id.detailEventContext);
        eventDate = (TextView) findViewById(R.id.detailEventDate);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        context = intent.getStringExtra("context");
        date = intent.getStringExtra("date");
        eventId = intent.getStringExtra("objectId");

        payment = intent.getStringExtra("payment");
        if (payment == "YES") {
            fee = intent.getStringExtra("fee");
            venmoId = intent.getStringExtra("venmoId");
        }



        eventTitle.setText(title);
        eventContext.setText(context);
        eventDate.setText(date);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_event, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_event_attend) {

            // add self to the event attending
            String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;
            // if attend
            if (payment == "YES") {
                if (VenmoLibrary.isVenmoInstalled(getApplicationContext())) {
                    Intent venmoIntent = VenmoLibrary.openVenmoPayment("2220", "GroupBowl", venmoId, fee, title, "pay");
                    startActivityForResult(venmoIntent, 1);


                }


            } else {

                addToAttend(currentEvent);
            }

            return true;

        } else if (id == R.id.action_event_list) {

        } else if (id == R.id.action_event_edit) {

            if (CurrentGroup.getCurrentTitle() == "Leader") {

                if (payment == "YES") {

                    Intent intent = new Intent(this, EditEventPaymentActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("context", context);
                    intent.putExtra("date", date);
                    intent.putExtra("fee", fee);
                    intent.putExtra("venmoId", venmoId);
                    startActivity(intent);

                } else {

                    Intent intent = new Intent(this, EditEventActivity.class);
                    intent.putExtra("title", title);
                    intent.putExtra("context", context);
                    intent.putExtra("date", date);
                    startActivity(intent);
                }


            } else {

                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEventActivity.this);
                builder.setMessage("Must be a leader.")
                        .setTitle("Oops!")
                        .setPositiveButton(android.R.string.ok, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        switch(requestCode) {
            case 1: {
                if(resultCode == RESULT_OK) {
                    String signedrequest = data.getStringExtra("signedrequest");
                    if(signedrequest != null) {
                        VenmoResponse response = (new VenmoLibrary()).validateVenmoPaymentResponse(signedrequest, "UaNkRnQg2tHtpbmFX6zsvsWjp9bRVPg7");
                        if(response.getSuccess().equals("1")) {
                            //Payment successful.  Use data from response object to display a success message
                            String note = response.getNote();
                            String amount = response.getAmount();
                        }
                    }
                    else {
                        String error_message = data.getStringExtra("error_message");
                        //An error ocurred.  Make sure to display the error_message to the user
                    }
                }
                else if(resultCode == RESULT_CANCELED) {
                    //The user cancelled the payment
                }
                break;
            }
        }
    }


    public void addToAttend(String currentEvent) {



        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentEvent);
        query.whereEqualTo("objectId", eventId);

        System.out.println("************");
        System.out.println(eventId);
        System.out.println("************");

        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject parseObject, ParseException e) {


                if (e == null) {

                    ParseRelation<ParseObject> relation = parseObject.getRelation("attend");
                    relation.add(CurrentMember.getUser());



                    parseObject.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {



                            if (e == null) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DetailEventActivity.this);
                                builder.setMessage("Thanks for attending.")
                                        .setTitle("Oops!")
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
