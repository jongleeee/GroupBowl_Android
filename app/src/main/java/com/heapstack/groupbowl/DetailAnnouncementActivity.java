package com.heapstack.groupbowl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.heapstack.groupbowl.R;

public class DetailAnnouncementActivity extends Activity {

    protected TextView announcementTitle;
    protected TextView announcementContext;
    protected String title;
    protected String context;
    protected String objectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_announcement);
    }


    @Override
    protected void onResume() {
        super.onResume();

        announcementTitle = (TextView) findViewById(R.id.detailAnnouncementTitle);
        announcementContext = (TextView) findViewById(R.id.detailAnnouncementContext);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        context = intent.getStringExtra("context");
        objectId = intent.getStringExtra("objectId");

        announcementTitle.setText(title);
        announcementContext.setText(context);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_announcement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_announcement_edit) {

            Intent intent = new Intent(this, EditAnnouncementActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("context", context);
            intent.putExtra("objectId", objectId);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
