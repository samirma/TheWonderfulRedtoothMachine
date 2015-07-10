package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class SequencialRecorder implements ConversationRecorder {

    private ArrayList<Byte> incomming = null;

    private Boolean requesting;
    private Boolean responsing;

    private ArrayList<Message> messages = null;

    @Override
    public void start() {
        requesting = false;
        responsing = false;
        incomming = new ArrayList<>();
        messages = new ArrayList<>();
    }

    @Override
    public void stop() {

        messageSent();

        messages = null;

    }

    public void messageSent() {
        MessageType type = MessageType.REQUEST;
        if (responsing) {
            type = MessageType.RESPONSE;
        }

        saveMessage(type);

        requesting = false;
        responsing = false;

    }

    @Override
    public void request(byte[] buffer) {
        requesting = true;

        MessageType type = MessageType.RESPONSE;

        processBuffer(buffer, type, responsing);

        if (responsing) {
            responsing = Boolean.FALSE;
        }

    }

    @Override
    public void response(byte[] buffer) {
        responsing = true;

        MessageType type = MessageType.REQUEST;

        processBuffer(buffer, type, requesting);

        if (requesting) {
            requesting = Boolean.FALSE;
        }
    }

    private void processBuffer(byte[] buffer, MessageType type, boolean changeDirection) {
        if (changeDirection) {
            saveMessage(type);
        }

        copyToIncommingBuffer(buffer);
    }

    private void copyToIncommingBuffer(byte[] buffer) {
        for (Byte byt : buffer) {
            incomming.add(byt);
        }
    }

    private void saveMessage(MessageType type) {
        final Message message = new Message(type, incomming);
        final List<Message> messages = getMessages();
        messages.add(message);
        incomming = new ArrayList<>();
    }


    public List<Message> getMessages() {
        return messages;
    }
}
