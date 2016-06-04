package com.splash2016.app.chatbot;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import org.alicebot.ab.*;

/**
 * Created by MunKeat on 5/6/2016.
 */
public class Chatbot {
    private Chatbot _chatbot = null;
    private String _chatbotName = null;
    private Chat _chatSession = null;

    private final static String DEFAULT_CHATBOT_NAME = "sparky";

    private Chatbot() {
        // Chatbot is intended as a Singleton
    }

    public Chatbot getInstance() {
        return getInstance(null);
    }

    public Chatbot getInstance(String chatbotName) {
        if (chatbotName == null) {
            // Initialise chatbot
            _chatbotName = DEFAULT_CHATBOT_NAME;
        } else {
            _chatbotName = chatbotName;
        }

        if (_chatbot == null) {
            _chatbot = new Chatbot();

            String libPathName = getAliceBotPathName();
            Bot bot = new Bot(_chatbotName, libPathName);
            _chatSession = new Chat(bot);
        }

        return _chatbot;
    }

    private String getAliceBotPathName() {
        // TODO Get path to be set as .../Splash2016/app/libs/alicebot/
        String path = null;

        return path;
    }
}
