package com.heapstack.groupbowl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Jong on 2/22/15.
 */
public class AnnouncementAdapter extends ArrayAdapter<ParseObject> {

    protected Context mContext;
    protected List<ParseObject> mAnnouncement;


    public AnnouncementAdapter(Context context, List<ParseObject> announcements) {
        super(context, R.layout.announcement_item, announcements);
        mContext = context;
        mAnnouncement = announcements;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(R.layout.announcement_item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.announcementTitle);
            holder.subtitle = (TextView) convertView.findViewById(R.id.subtitle);

        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        ParseObject announcement = mAnnouncement.get(position);
        holder.title.setText((String) announcement.get("title"));
        holder.subtitle.setText((String) announcement.get("news"));

        return convertView;
    }

    private static class ViewHolder {
        TextView title;
        TextView subtitle;
    }

}
