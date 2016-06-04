package com.splash2016.app.chat;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.splash2016.app.R;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.objects.Message;
import com.splash2016.app.objects.MessageModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ChatActivity extends AppCompatActivity {

    private static SimpleDateFormat DATETIMEFORMATTER = new SimpleDateFormat("dd MMM yyyy h:mm:ss");
    private static SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd MMM yyyy");
    private static SimpleDateFormat TIMEFORMATTER = new SimpleDateFormat("h:mma");

    private static String[] friendMessage = { "Go away! I don't wan to talk to you", "I'm the best", "Lame", "Ahahaha", ">_>", "Bye ahaha!" };

    private static final String TAG = ChatActivity.class.getSimpleName();
    private static final String KEY_FRIEND_NAME = "name";
    private static final String ISSELF = "true";
    private static final String ISNOTSELF = "false";

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private RecyclerViewSectionAdapter adapter;
    private LinearLayoutManager layoutManager;

    private EditText editTextMessage;
    private ImageButton buttonSend;
    private Calendar calendar;
    private ChatDatabase chatDatabase;
    private List<MessageModel> messageList;
    private String friendName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Display back arrow in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Set name
        Bundle extras = getIntent().getExtras();
        friendName = extras.getString(KEY_FRIEND_NAME);
        getSupportActionBar().setTitle(friendName);

        calendar = calendar.getInstance();
        //getTestMessage();
        chatDatabase = new ChatDatabase(this);
        editTextMessage = (EditText) findViewById(R.id.edittext_message);
        buttonSend = (ImageButton) findViewById(R.id.button_send);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        messageList = getMessageList();
        adapter = new RecyclerViewSectionAdapter(messageList, ChatActivity.this);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


//        listView = (ListView) findViewById(R.id.list_messages);
//        messageList = chatDatabase.getMessages(chatDatabase, friendName);
//        adapter = new ChatListAdapter(getApplicationContext(), messageList);
//        listView.setAdapter(adapter);

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
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        Log.d(TAG, "onActivityResult setResult");
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setMaxWidth(Integer.MAX_VALUE);

        return super.onCreateOptionsMenu(menu);
    }

    private void setButtonSend() {
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = editTextMessage.getText().toString();
                if(!message.isEmpty()) {
                    saveMessage(message);
                }
            }
        });
    }

    private void saveMessage(String message) {
        final String dateTime = DATETIMEFORMATTER.format(calendar.getTime());
        final String date = DATEFORMATTER.format(calendar.getTime());
        final String time = TIMEFORMATTER.format(calendar.getTime());
        chatDatabase.addMessage(chatDatabase, friendName, message, ISSELF, date, time, dateTime);
        updateListView();
        // Delay before sending out new message
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatDatabase.addMessage(chatDatabase, friendName, randomMessage(), ISNOTSELF, date, time, dateTime);
                updateListView();
            }
        }, 500);
    }

    private void updateListView() {
        editTextMessage.setText("");
        messageList = getMessageList();
        adapter = new RecyclerViewSectionAdapter(messageList, ChatActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private String randomMessage() {
        Random rand = new Random();
        return friendMessage[rand.nextInt(6)];
    }

    private List<MessageModel> getMessageList() {
        List<MessageModel> messageModelList = new ArrayList<>();
        List<Message> messageList = chatDatabase.getMessages(chatDatabase, friendName);
        int size = messageList.size();
        int index = 0;

        for (int i = 0; i < size; i++) {
            MessageModel messageModel = new MessageModel();
            Message message = messageList.get(i);
            String date = message.getDate();
            messageModel.setDateHeader(date);
            Log.d(TAG, "Message: " + date);

            ArrayList<Message> messages = new ArrayList<>();
            for (int j = i; j < size; j++) {
                message = messageList.get(j);
                if(date.equals(message.getDate())) {
                    Log.d(TAG, "Message date: " + date + " / " + message.getMessage());
                    messages.add(message);
                    index = j;
                }
            }

            i = index;
            messageModel.setAllMessagesInSection(messages);
            messageModelList.add(messageModel);
        }

        return messageModelList;
    }

//    private void getTestMessage() {
//        messageList = new ArrayList<>();
//        String date = DATEFORMATTER.format(calendar.getTime());
//        String time = TIMEFORMATTER.format(calendar.getTime()).replaceAll("\\.", "").toUpperCase();
//
//        messageList.add(new Message("Friend 1", "Hey dude", false, date, time));
//        messageList.add(new Message("Friend 1", "How are you?", false, date, time));
//        messageList.add(new Message("Friend 1", "Don't talk to me, go away!", true, date, time));
//        messageList.add(new Message("Friend 1", "Hey, come back!", false, date, time));
//        messageList.add(new Message("Friend 1", "Lame", true, date, time));
//        messageList.add(new Message("Friend 1", "Ahaha", true, date, time));
//        messageList.add(new Message("Friend 1", "Bye", true, date, time));
//        messageList.add(new Message("Friend 1", "Zzz", false, date, time));
//        messageList.add(new Message("Friend 1", "Hello hello hello hello hello hello hello hello hello hello hello hello hello ", false, date, time));
//        messageList.add(new Message("Friend 1", "Dudeeeeeeeeee!!!", false, date, time));
//        messageList.add(new Message("Friend 1", "Bye hahaha", true, date, time));
//        messageList.add(new Message("Friend 1", ">_>", false, date, time));
//    }
}
