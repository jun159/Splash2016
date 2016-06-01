package com.splash2016.app.friendlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.splash2016.app.R;
import com.splash2016.app.chat.ChatActivity;
import com.splash2016.app.objects.Friend;

import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ListAdapter extends ArrayAdapter<Friend> {

    private static final String KEY_FRIEND_NAME = "name";
    private List<Friend> friendList;
    private Context context;

    public ListAdapter(Context context, int resource,
                          List<Friend> objects) {
        super(context, resource, objects);

        this.context = context;
        this.friendList = objects;
    }

    @Override
    public int getCount() {
        return ((null != friendList) ?
                friendList.size() : 0);
    }

    @Override
    public Friend getItem(int position) {
        return ((null != friendList) ?
                friendList.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(null == view) {
            view = layoutInflater.inflate(R.layout.list_friend, null);
        }

        final Friend friend = friendList.get(position);

        if(null != friend) {
            final CardView rowLayout = (CardView) view.findViewById(R.id.row_layout);
            final ImageView friendImage = (ImageView) view.findViewById(R.id.list_friend_image);
            final TextView friendName = (TextView) view.findViewById(R.id.list_friend_name);
            final TextView friendDescription = (TextView) view.findViewById(R.id.list_friend_description);

            friendImage.setImageResource(getImageDrawable(position));
            friendName.setText(friend.getName());
            friendDescription.setText(friend.getDescription());

            // Open chat activity
            rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra(KEY_FRIEND_NAME, friend.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

        return view;
    }

    private int getImageDrawable(int friendID) {
        int imageResource = 0;
        switch (friendID % 6) {
            case 0:
                imageResource = R.drawable.friend_red;
                break;
            case 1:
                imageResource = R.drawable.friend_green;
                break;
            case 2:
                imageResource = R.drawable.friend_dark_blue;
                break;
            case 3:
                imageResource = R.drawable.friend_orange;
                break;
            case 4:
                imageResource = R.drawable.friend_purple;
                break;
            case 5:
                imageResource = R.drawable.friend_light_blue;
                break;
        }

        return imageResource;
    }
}
