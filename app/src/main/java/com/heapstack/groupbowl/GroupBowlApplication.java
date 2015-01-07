package com.heapstack.groupbowl;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by Jong on 1/6/15.
 */
public class GroupBowlApplication extends Application{


    public void onCreate() {

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "nqi7esegIm4RP8lREeDJ2TAkVXFvaxZiKArce63Y", "BFSzX2cK1IiDXKKU9ax7pHu8fIOBfUcX1GwdZy4L");


    }

}
