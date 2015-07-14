package com.antonio.samir.wonderfulredtooth.model;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;

import java.util.List;

/**
 * Created by samir on 7/14/15.
 */
public class Conversation {
    public Integer id;
    public List<Message> messages;
    public String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conversation)) return false;

        Conversation that = (Conversation) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
