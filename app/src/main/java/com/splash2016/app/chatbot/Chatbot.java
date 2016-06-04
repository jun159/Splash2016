package com.splash2016.app.chatbot;

import org.alicebot.ab.*;

public class Chatbot {
    private static Chatbot _chatbot = null;
    private static String _chatbotName = null;
    private static Chat _chatSession = null;

    private final static String DEFAULT_CHATBOT_NAME = "sparky";

    private Chatbot() {
        // Chatbot is intended as a Singleton
    }

    public static Chatbot getInstance() {
        return getInstance(null);
    }

    public static Chatbot getInstance(String chatbotName) {
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

    public Chat getChatbot() {
        return _chatSession;
    }

    private static String getAliceBotPathName() {
        // TODO Get path to be set as .../Splash2016/app/libs/alicebot/
        String path = null;

        return path;
    }
}
