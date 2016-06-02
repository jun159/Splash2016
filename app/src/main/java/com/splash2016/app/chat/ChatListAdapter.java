package com.splash2016.app.chat;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.splash2016.app.R;
import com.splash2016.app.objects.Message;

import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ChatListAdapter extends BaseAdapter {

    private Context context;
    private List<Message> messagesItems;

    public ChatListAdapter(Context context, List<Message> navDrawerItems) {
        this.context = context;
        this.messagesItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return messagesItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messagesItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (messagesItems.get(position).isSelf()) {
            convertView = inflater.inflate(R.layout.list_message_you, null);
        } else {
            convertView = inflater.inflate(R.layout.list_message_from, null);
        }

        TextView txtMsg = (TextView) convertView.findViewById(R.id.txtMsg);
        TextView txtTime = (TextView) convertView.findViewById(R.id.txtTime);
        Message message = messagesItems.get(position);
        txtMsg.setText(Html.fromHtml(message.getMessage() + " &#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;"));
        txtTime.setText(message.getTime().replaceAll("\\.", "").toUpperCase());

        return convertView;
    }
}
