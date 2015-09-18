package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by samir on 7/10/15.
 */
public class SequencialRecorder implements ConversationRecorder {

    private ArrayList<Byte> incomming = null;

    private MessageType currentStremDirection = null;

    private ArrayList<Message> messages = null;

    @Override
    public void start() {
        incomming = new ArrayList<>();
        messages = new ArrayList<>();
    }

    @Override
    public void stop() {

        if (currentStremDirection != null){
            messageSent();
            currentStremDirection = null;
        }

    }

    public void messageSent() {

        saveMessage();

        currentStremDirection = null;

    }

    @Override
    public void request(byte[] buffer) {

        MessageType type = MessageType.REQUEST;

        processBuffer(buffer, type);

    }

    @Override
    public void response(byte[] buffer) {

        MessageType type = MessageType.RESPONSE;

        processBuffer(buffer, type);

    }

    private void processBuffer(byte[] buffer, MessageType type) {
        final boolean isDifferentStream = !Objects.equals(type, currentStremDirection);
        if (isDifferentStream) {
            if (currentStremDirection!=null) {
                saveMessage();
            }
            currentStremDirection = type;
        }

        copyToIncommingBuffer(buffer);
    }

    private void copyToIncommingBuffer(byte[] buffer) {
        for (Byte byt : buffer) {
            incomming.add(byt);
        }
    }

    private void saveMessage() {
        final Message message = new Message(currentStremDirection, incomming);
        final List<Message> messages = getMessages();
        messages.add(message);
        incomming = new ArrayList<>();
    }


    public List<Message> getMessages() {
        return messages;
    }
}
