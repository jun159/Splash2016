package com.splash2016.app.chatbot;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.alicebot.ab.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Chatbot {
    private static Chat _chatSession = null;
    private static Context _context = null;
    private static String _chatbotName = null;
    private final static String DEFAULT_CHATBOT_NAME = "sparky";

    public Chatbot (Context context, String chatbotName) {
        _chatbotName = (chatbotName == null) ? DEFAULT_CHATBOT_NAME : chatbotName;
        _context = context;
        // Instantiate the bot
        String libPathName = getAliceBotPathName();
        Bot bot = new Bot(_chatbotName, libPathName);
        _chatSession = new Chat(bot);
    }

    public Chatbot(Context context) {
        this(context, null);
    }

    public Chat getChatbot() {
        return _chatSession;
    }

    private String getAliceBotPathName() {

        File filesDir;
        String path = "";

        String state = Environment.getExternalStorageState();

        try {
            // Make sure it's available
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                filesDir = _context.getExternalFilesDir(null);
                unZipIt(_context.getAssets().open("bots.zip"), _context.getExternalFilesDir(null).getAbsolutePath() + "/");
                path = _context.getExternalFilesDir(null).getAbsolutePath() + "/";
            } else {
                filesDir = _context.getFilesDir();
                unZipIt(_context.getAssets().open("bots.zip"), _context.getFilesDir().getAbsolutePath() + "/");
                path = _context.getFilesDir().getAbsolutePath() + "/";
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return path;
    }

    private static void unZipIt(InputStream zipFile, String outputFolder) {
        try {
            // Get the zip file content
            ZipInputStream zipFolderEntries = new ZipInputStream(zipFile);
            // Placeholder for zip file entries
            ZipEntry entry;

            int bytesRead;
            byte[] buffer = new byte[4096];

            while ((entry = zipFolderEntries.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    // Create directory listed in zip file if it does not exist
                    File dir = new File(outputFolder, entry.getName());
                    System.out.println(dir.toString());
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                } else {
                    // Output files
                    FileOutputStream fos = new FileOutputStream(outputFolder + entry.getName());
                    while ((bytesRead = zipFolderEntries.read(buffer)) >= 0) {
                        fos.write(buffer, 0, bytesRead);
                    }
                    fos.close();
                }
            }
            zipFolderEntries.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
