package com.heapstack.groupbowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

/**
 * Created by Jong on 1/7/15.
 */
public class MemberFragment extends ListFragment {

    protected List<ParseObject> mUsers;
    private ProgressBar spinner;
    protected SwipeRefreshLayout mSwipeRefreshLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_member, container, false);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarMember);

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
            retrieveMember();


        }



    }

    private void retrieveMember() {
        String currentMember = CurrentGroup.getCurrentGroupName() + ParseConstants.MEMBER;

        // String parseAnnouncement = groupName.concat(ParseConstants.ANNOUNCEMENT);

        ParseQuery<ParseObject> query = ParseQuery.getQuery(currentMember);
        query.orderByAscending(ParseConstants.KEY_NAME);
        query.setLimit(1000);

        spinner.setVisibility(View.VISIBLE);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> users, ParseException e) {

                spinner.setVisibility(View.GONE);

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (e == null) {
                    // success
                    mUsers = users;
                    String[] usernames = new String[mUsers.size()];
                    int i = 0;
                    for (ParseObject user : mUsers) {
                        usernames[i] = (String) user.get("name");
                        i++;
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                            android.R.layout.simple_list_item_1, usernames);
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
        System.out.println("Value is "+ (String) mUsers.get(position).get("email"));

        // create new fragment with class
        Intent intent = new Intent(getActivity(), DetailMemberActivity.class);
        intent.putExtra("name", (String) mUsers.get(position).get("name"));
        intent.putExtra("phone", (String) mUsers.get(position).get("phone"));
        intent.putExtra("email", (String) mUsers.get(position).get("email"));

        startActivity(intent);

    }


    protected SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            Toast.makeText(getActivity(), "Refreshing!", Toast.LENGTH_SHORT).show();
            retrieveMember();
        }
    };

}
