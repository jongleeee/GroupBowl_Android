package com.heapstack.groupbowl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Jong on 2/22/15.
 */
public class EventAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mEvent;


    public EventAdapter(Context context, List<ParseObject> events) {
        super(context, R.layout.event_item, events);

        mContext = context;
        mEvent = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EventViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.event_item, null);
        }

        holder = new EventViewHolder();
        holder.title = (TextView) convertView.findViewById(R.id.eventTitle);
        holder.date = (TextView) convertView.findViewById(R.id.eventDate);





        ParseObject event = mEvent.get(position);
        holder.title.setText(event.getString("title"));

        Date eventDate = (Date) event.get("date");
        DateFormat df = new SimpleDateFormat("E  MM/dd");
        String date = df.format(eventDate);
        holder.date.setText(date);





        return convertView;
    }



    private static class EventViewHolder {
        TextView title;
        TextView date;
    }
}
