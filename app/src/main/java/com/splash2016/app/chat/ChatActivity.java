package com.splash2016.app.chat;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.splash2016.app.R;
import com.splash2016.app.objects.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ChatActivity extends AppCompatActivity {

    private static final String KEY_FRIEND_NAME = "name";

    private EditText editTextMessage;
    private ImageButton buttonSend;
    private ChatListAdapter adapter;
    private List<Message> messageList;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Display back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getTestMessage();

        // Set name
        Bundle extras = getIntent().getExtras();
        String friendName = extras.getString(KEY_FRIEND_NAME);
        getSupportActionBar().setTitle(friendName);

        editTextMessage = (EditText) findViewById(R.id.edittext_message);
        buttonSend = (ImageButton) findViewById(R.id.button_send);
        listView = (ListView) findViewById(R.id.list_messages);
        adapter = new ChatListAdapter(getApplicationContext(), messageList);
        listView.setAdapter(adapter);

        setButtonSend();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        return super.onCreateOptionsMenu(menu);
    }

    private void setButtonSend() {
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                saveMessage(message);
                editTextMessage.setText("");
            }
        });
    }

    private void saveMessage(String message) {

    }

    private void getTestMessage() {
        messageList = new ArrayList<>();
        messageList.add(new Message("Friend 1", "Hey dude", false));
        messageList.add(new Message("Friend 1", "How are you?", false));
        messageList.add(new Message("Friend 1", "Don't talk to me, go away!", true));
        messageList.add(new Message("Friend 1", "Hey, come back!", false));
        messageList.add(new Message("Friend 1", "Lame", true));
        messageList.add(new Message("Friend 1", "Ahaha", true));
        messageList.add(new Message("Friend 1", "Bye", true));
        messageList.add(new Message("Friend 1", "Zzz", false));
        messageList.add(new Message("Friend 1", "Hello hello hello hello hello hello hello hello hello hello hello hello hello ", false));
        messageList.add(new Message("Friend 1", "Dudeeeeeeeeee!!!", false));
        messageList.add(new Message("Friend 1", "Bye hahaha", true));
        messageList.add(new Message("Friend 1", ">_>", false));
    }
}
