package com.splash2016.app.friendlist;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.splash2016.app.R;
import com.splash2016.app.objects.Friend;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class FriendListFragment extends Fragment {

    // TODO: Replace with actual friend name
    private static final String FRIEND_NAME_1 = "Friend 1";
    private static final String FRIEND_NAME_2 = "Friend 2";
    private static final String FRIEND_NAME_3 = "Friend 3";
    private static final String FRIEND_NAME_4 = "Friend 4";
    private static final String FRIEND_NAME_5 = "Friend 5";
    private static final String FRIEND_NAME_6 = "Friend 6";
    private static final String FRIEND_NAME_7 = "Friend 7";
    private static final String FRIEND_NAME_8 = "Friend 8";
    private static final String FRIEND_NAME_9 = "Friend 9";
    private static final String FRIEND_NAME_10 = "Friend 10";
    private static final String FRIEND_DESCRIPTION= "Hi, nice to meet you >:)";

    private ListView listView;
    private FriendListAdapter adapter;

    public FriendListFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_friendlist, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        adapter = new FriendListAdapter(getActivity(), R.layout.fragment_friendlist, getFriendList());
        listView.setAdapter(adapter);

        return rootView;
    }

    private List<Friend> getFriendList() {
        List<Friend> friendList = new ArrayList<>();
        int id = 1;

        friendList.add(new Friend(id++, FRIEND_NAME_1, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_2, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_3, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_4, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_5, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_6, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_7, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_8, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id++, FRIEND_NAME_9, FRIEND_DESCRIPTION));
        friendList.add(new Friend(id, FRIEND_NAME_10, FRIEND_DESCRIPTION));

        return friendList;
    }
}
