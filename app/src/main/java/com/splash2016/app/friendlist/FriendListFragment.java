package com.splash2016.app.friendlist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

    private ListView listView;
    private ListAdapter adapter;

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
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        listView = (ListView) rootView.findViewById(android.R.id.list);
        adapter = new ListAdapter(getActivity(), R.layout.fragment_list, Friend.getFriendList(), null, false);
        listView.setAdapter(adapter);

        return rootView;
    }
}
