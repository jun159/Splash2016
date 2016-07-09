package com.splash2016.app.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.splash2016.app.R;

/**
 * Created by BAOJUN on 3/7/16.
 */
public class ProfileActivity extends AppCompatActivity {

    private static final String KEY_FRIEND_NAME = "name";
    private String friendName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Receive friend name
        Bundle extras = getIntent().getExtras();
        friendName = extras.getString(KEY_FRIEND_NAME);
        displayToolbar(friendName);
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
}
