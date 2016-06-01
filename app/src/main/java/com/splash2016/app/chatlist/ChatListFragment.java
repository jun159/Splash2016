package com.splash2016.app.chatlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.splash2016.app.R;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.friendlist.ListAdapter;
import com.splash2016.app.objects.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 2/6/16.
 */
public class ChatListFragment extends Fragment {

    private ListView listView;
    private ListAdapter adapter;
    private ChatDatabase chatDatabase;

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
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        chatDatabase = new ChatDatabase(getActivity());
        listView = (ListView) rootView.findViewById(android.R.id.list);
        adapter = new ListAdapter(getActivity(), R.layout.fragment_list, getFriendChatList());
        listView.setAdapter(adapter);

        return rootView;
    }

    private List<Friend> getFriendChatList() {
        List<Friend> friendList = Friend.getFriendList();
        List<Friend> friendChatList = new ArrayList<>();

        for(Friend friend : friendList) {
            int size = chatDatabase.getMessages(chatDatabase, friend.getName()).size();
            if(size > 0) {
                // Set description as the latest message
                friend.setDescription(chatDatabase.getLastMessage(chatDatabase, friend.getName()));
                friendChatList.add(friend);
            }
        }

        return friendChatList;
    }
}
