package com.heapstack.groupbowl;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

/**
 * Created by Jong on 2/22/15.
 */
public class Receiver extends ParsePushBroadcastReceiver {

    @Override
    public void onPushOpen(Context context, Intent intent) {

        System.out.println("***********");
        System.out.println("asdfsdf");
        System.out.println("***********");

        Log.e("Push", "Clicked");
        Intent i = new Intent(context, MainActivity.class);
        i.putExtras(intent.getExtras());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}