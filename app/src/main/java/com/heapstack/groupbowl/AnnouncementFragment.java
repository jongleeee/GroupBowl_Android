package com.heapstack.groupbowl;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

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
public class AnnouncementFragment extends ListFragment {

    public static String groupName;
    protected List<ParseObject> mAnnouncements;
    private ProgressBar spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_announcement, container, false);
        spinner = (ProgressBar)rootView.findViewById(R.id.progressBarAnnouncement);

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
            String currentAnnouncement = CurrentGroup.getCurrentGroupName() + ParseConstants.ANNOUNCEMENT;

            // String parseAnnouncement = groupName.concat(ParseConstants.ANNOUNCEMENT);

            ParseQuery<ParseObject> query = ParseQuery.getQuery(currentAnnouncement);
            query.orderByAscending(ParseConstants.KEY_UPDATED);
            query.setLimit(1000);

            spinner.setVisibility(View.VISIBLE);

            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> announcements, ParseException e) {

                    spinner.setVisibility(View.GONE);

                    if (e == null) {
                        // success
                        mAnnouncements = announcements;
                        String[] announcementTitle = new String[mAnnouncements.size()];
                        String[] announcementNews = new String[mAnnouncements.size()];

                        int i = 0;
                        for (ParseObject announcement : mAnnouncements) {
                            announcementTitle[i] = (String) announcement.get("title");
                            announcementNews[i] = (String) announcement.get("news");
                            i++;
                        }


                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_list_item_1, announcementTitle);
                        setListAdapter(adapter);

                    } else {


                    }
                }
            });

        }




    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);


        String val = Integer.toString(position);

        // create new fragment with class
        Intent intent = new Intent(getActivity(), DetailAnnouncementActivity.class);
        intent.putExtra("title", (String) mAnnouncements.get(position).get("title"));
        intent.putExtra("context", (String) mAnnouncements.get(position).get("news"));


        startActivity(intent);


    }

}
