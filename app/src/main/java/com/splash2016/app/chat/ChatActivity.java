package com.splash2016.app.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.splash2016.app.R;
import com.splash2016.app.chatbot.Chatbot;
import com.splash2016.app.database.ChatDatabase;
import com.splash2016.app.objects.Message;
import com.splash2016.app.objects.MessageModel;

import org.alicebot.ab.Chat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by BAOJUN on 31/5/16.
 */
public class ChatActivity extends AppCompatActivity {

    private static final SimpleDateFormat DATETIMEFORMATTER = new SimpleDateFormat("dd/MM/yyyy h:mm:ss", Locale.getDefault());
    private static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    private static final SimpleDateFormat TIMEFORMATTER = new SimpleDateFormat("h:mma", Locale.getDefault());

    private Chatbot chatbot;
    private Chat robot;

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

        // Receive friend name
        Bundle extras = getIntent().getExtras();
        friendName = extras.getString(KEY_FRIEND_NAME);
        displayToolbar(friendName);

        calendar = Calendar.getInstance();
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

        chatbot = new Chatbot(this.getApplicationContext(), "mini");
        robot = chatbot.getChatbot();

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
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void displayToolbar(String friendName) {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(friendName);
        }
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
        replyMessage(message, date, time, dateTime);
    }

    private void updateListView() {
        editTextMessage.setText("");
        messageList = getMessageList();
        adapter = new RecyclerViewSectionAdapter(messageList, ChatActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void replyMessage(String message, final String date, final String time, final String dateTime) {
        // Delay before sending out new message
        final String reply = robot.multisentenceRespond(message);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                chatDatabase.addMessage(chatDatabase, friendName, reply, ISNOTSELF, date, time, dateTime);
                updateListView();
            }
        }, 500);
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

            ArrayList<Message> messages = new ArrayList<>();
            for (int j = i; j < size; j++) {
                message = messageList.get(j);
                if(date.equals(message.getDate())) {
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
}