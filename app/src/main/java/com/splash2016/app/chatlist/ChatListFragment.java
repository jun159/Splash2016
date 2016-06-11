package com.splash2016.app.chatlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.splash2016.app.R;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.friendlist.ListAdapter;
import com.splash2016.app.objects.Friend;
import com.splash2016.app.objects.Message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by BAOJUN on 2/6/16.
 */
public class ChatListFragment extends Fragment {

    private static final int MYACTIVITY_REQUEST_CODE = 9000;

    private ListView listView;
    private ListAdapter adapter;
    private ChatDatabase chatDatabase;
    private LinearLayout linearLayout;

    public ChatListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        chatDatabase = new ChatDatabase(getActivity());
        List<Friend> friendList = getFriendChatList();
        listView = (ListView) rootView.findViewById(android.R.id.list);
        adapter = new ListAdapter(getActivity(), R.layout.fragment_list, friendList, true);
        listView.setAdapter(adapter);

        linearLayout = (LinearLayout) rootView.findViewById(R.id.list_empty);
        linearLayout.setVisibility(View.GONE);
        if(friendList.isEmpty()) {
            linearLayout.setVisibility(View.VISIBLE);
        } else {
            linearLayout.setVisibility(View.GONE);
        }

        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == MYACTIVITY_REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            List<Friend> friendList = getFriendChatList();
            adapter = new ListAdapter(getActivity(), R.layout.fragment_list, friendList, true);
            adapter.notifyDataSetChanged();
            listView.setAdapter(adapter);
            if(friendList.isEmpty()) {
                linearLayout.setVisibility(View.VISIBLE);
            } else {
                linearLayout.setVisibility(View.GONE);
            }
        }
    }

    private List<Friend> getFriendChatList() {
        List<Friend> friendList = Friend.getFriendList();
        List<Friend> friendChatList = new ArrayList<>();

        for(Friend friend : friendList) {
            int size = chatDatabase.getMessages(chatDatabase, friend.getName()).size();
            if(size > 0) {
                // Set description as the latest message
                Message message = chatDatabase.getLastMessage(chatDatabase, friend.getName());
                Friend addFriend = new Friend(friend.getName(), message.getMessage(), message.getDate(), message.getTime(), message.getDateTime());
                friendChatList.add(addFriend);
            }
        }

        // Sort chat according to most current chats
        Collections.sort(friendChatList);
        return friendChatList;
    }
}
