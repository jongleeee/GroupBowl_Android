package com.heapstack.groupbowl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.heapstack.groupbowl.R;

import org.w3c.dom.Text;

public class DetailMemberActivity extends Activity {

    protected TextView memberName;
    protected TextView memberPhone;
    protected TextView memberEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail_member);
    }




    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {

        return super.onCreateView(name, context, attrs);
    }


    @Override
    protected void onResume() {
        super.onResume();

        memberName = (TextView) findViewById(R.id.detailMemberName);
        memberPhone = (TextView) findViewById(R.id.detailMemberPhone);
        memberEmail = (TextView) findViewById(R.id.detailMemberEmail);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String email = intent.getStringExtra("email");

        memberName.setText(userName);
        memberPhone.setText(phone);
        memberEmail.setText(email);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail_member, menu);
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
