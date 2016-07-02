package com.splash2016.app.friendlist;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.splash2016.app.R;
import com.splash2016.app.chat.ChatActivity;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.objects.Friend;
import com.splash2016.app.objects.Message;

import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ListAdapter extends ArrayAdapter<Friend> {

    private static final String TAG = ListAdapter.class.getSimpleName();
    private static final String TITLE_DELETE = "Delete chat";
    private static final String MESSAGE_DELETE = "Are you sure you want to delete chat?";
    private static final String MESSAGE_OK = "Ok";
    private static final String MESSAGE_CANCEL = "Cancel";
    private static final String KEY_FRIEND_NAME = "name";

    private static final int MYACTIVITY_REQUEST_CODE = 9000;

    private List<Friend> friendList;
    private Context context;
    private ChatDatabase chatDatabase;
    private boolean isChat;

    public ListAdapter(Context context, int resource, List<Friend> objects, boolean isChat) {
        super(context, resource, objects);
        this.context = context;
        this.friendList = objects;
        this.chatDatabase = new ChatDatabase(context);
        this.isChat = isChat;
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
    public View getView(final int position, final View convertView, ViewGroup parent) {
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
            final TextView friendLastChatTime = (TextView) view.findViewById(R.id.list_friend_lastChatTime);

            friendImage.setImageResource(getImageDrawable(position));
            friendName.setText(friend.getName());
            friendDescription.setText(friend.getDescription());

            String lastChatTime = friend.getLastMessageTime();
            if(lastChatTime != null) {
                lastChatTime = lastChatTime.replaceAll("\\.", "").toUpperCase();

                // Set time
                if(!lastChatTime.isEmpty()) {
                    friendLastChatTime.setText(lastChatTime);
                }
            }

            // Open chat activity
            rowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra(KEY_FRIEND_NAME, friend.getName());
                    Log.d(TAG, "onActivityResult call");
                    ((Activity) context).startActivityForResult(intent, MYACTIVITY_REQUEST_CODE);
                }
            });

            if(isChat) {
                rowLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        alertDialog(friend.getName(), position);
                        return true;
                    }
                });
            }
        }

        return view;
    }

    private void updateListView(String friendName, int position) {
        friendList.remove(position);
        notifyDataSetChanged();
        chatDatabase.deleteFriend(chatDatabase, friendName);
    }

    private void alertDialog(final String friendName, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setTitle(TITLE_DELETE);
        builder.setMessage(MESSAGE_DELETE);

        builder.setPositiveButton(MESSAGE_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateListView(friendName, position);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(MESSAGE_CANCEL, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
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
                imageResource = R.drawable.friend_dark_back;
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
