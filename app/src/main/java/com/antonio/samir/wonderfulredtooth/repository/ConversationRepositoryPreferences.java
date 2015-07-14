package com.antonio.samir.wonderfulredtooth.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.antonio.samir.wonderfulredtooth.model.Conversation;
import com.google.gson.Gson;


public class ConversationRepositoryPreferences extends ConversationRepository {

    private static final Gson GSON = new Gson();

    private static final String CONVERSATION = "CONVERSATION";

    private Context context;

    public ConversationRepositoryPreferences(final Context context) {
        this.context = context;
    }

    @Override
    public void saveConversation(final Conversation conversation) {

        SharedPreferences sharedPref = ((Activity) context).getPreferences(Context.MODE_PRIVATE);

        final String json = GSON.toJson(conversation);

        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(CONVERSATION, json);
        editor.commit();

    }

    @Override
    public Conversation getConversationById(Integer id) {

        SharedPreferences sharedPref = ((Activity) context).getPreferences(Context.MODE_PRIVATE);

        final String sharedPrefString = sharedPref.getString(CONVERSATION, null);

        final Conversation conversation = GSON.fromJson(sharedPrefString, Conversation.class);

        return conversation;
    }
}
