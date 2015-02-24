package com.heapstack.groupbowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jong on 1/7/15.
 */
public class EventFragment extends ListFragment {

    protected List<ParseObject> mEvents;
    private ProgressBar spinner;
    protected SwipeRefreshLayout mSwipeRefreshLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        System.out.println("HERE IS THE EVENT FRAGMENT");


        View rootView = inflater.inflate(R.layout.fragment_event, container, false);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarEvent);

        mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mSwipeRefreshLayout.setColorScheme(
                R.color.swipeRefresh1, R.color.swipeRefresh2,
                R.color.swipeRefresh3, R.color.swipeRefresh4
        );

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spinner.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (CurrentMember.getUserGroup() == null) {

        } else {
            retrieveEvent();


        }




    }

    private void retrieveEvent() {
        String currentEvent = CurrentGroup.getCurrentGroupName() + ParseConstants.EVENT;

        // String parseAnnouncement = groupName.concat(ParseConstants.ANNOUNCEMENT);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentEvent);
        query.orderByAscending(ParseConstants.KEY_NAME);
        Date currentDate = new Date();
        query.whereGreaterThanOrEqualTo("date", currentDate);
        query.setLimit(1000);

        spinner.setVisibility(View.VISIBLE);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> events, ParseException e) {

                spinner.setVisibility(View.GONE);

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (e == null) {
                    // success
                    mEvents = events;
//                        String[] eventTitle = new String[mEvents.size()];
//                        int i = 0;
//                        for (ParseObject event : mEvents) {
//                            eventTitle[i] = (String) event.get("title");
//                            i++;
//                        }
//                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
//                                android.R.layout.simple_list_item_1, eventTitle);

                    EventAdapter adapter = new EventAdapter(getActivity(), mEvents);

                    setListAdapter(adapter);

                } else {


                }
            }
        });
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        String val = Integer.toString(position);

        // create new fragment with class
        Intent intent = new Intent(getActivity(), DetailEventActivity.class);
        intent.putExtra("title", (String) mEvents.get(position).get("title"));
        // intent.putExtra("date", (String) mEvents.get(position).get("date"));

        // Create an instance of SimpleDateFormat used for formatting
        // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat tf = new SimpleDateFormat("hh:mm a");

        // Get the date today using Calendar object.
        Date eventDate = (Date) mEvents.get(position).get("date");


        // Using DateFormat format method we can create a string
        // representation of a date with the defined format.
        String date = df.format(eventDate);
        String time = tf.format(eventDate);

        intent.putExtra("context", (String) mEvents.get(position).get("contents"));
        intent.putExtra("date", date);
        intent.putExtra("time", time);
        String payment;
        if (mEvents.get(position).get("payment") == null) {
            payment = "NO";
        } else {
            payment = (String) mEvents.get(position).get("payment");
        }
        intent.putExtra("payment", payment);
        intent.putExtra("objectId", (String) mEvents.get(position).getObjectId());

        System.out.println("************");
        System.out.println(mEvents.get(position).getObjectId());
        System.out.println("************");

        if (payment.equals("YES")) {
            intent.putExtra("fee", Integer.toString((int) mEvents.get(position).get("fee")));
            intent.putExtra("venmoId", (String) mEvents.get(position).get("venmoId"));

        }


        startActivity(intent);

    }


    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Toast.makeText(getActivity(), "Refreshing!", Toast.LENGTH_SHORT).show();
            retrieveEvent();
        }
    };

}
