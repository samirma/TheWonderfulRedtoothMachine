package com.antonio.samir.wonderfulredtooth.repository;

import android.app.Activity;

import com.antonio.samir.wonderfulredtooth.model.Conversation;


public abstract class ConversationRepository {

    private static Activity activity;

    public static ConversationRepository getRepository() {
        return new ConversationRepositoryPreferences(activity);
    }

    public static void init(final Activity _activity) {
        activity = _activity;
    }

    public abstract void saveConversation(final Conversation conversation);

    public abstract Conversation getConversationById(final Integer id);

}
