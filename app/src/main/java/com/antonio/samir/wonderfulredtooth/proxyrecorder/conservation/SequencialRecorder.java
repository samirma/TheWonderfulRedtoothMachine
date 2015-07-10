package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class SequencialRecorder implements ConversationRecorder {

    private ArrayList<Byte> incomming;

    private boolean requesting;
    private boolean responsing;

    private ArrayList<Message> messages;

    @Override
    public void start() {
        requesting = false;
        responsing = false;
        incomming = new ArrayList<>();
        messages = new ArrayList<>();
    }

    @Override
    public void stop() {
        MessageType type = MessageType.REQUEST;
        if (responsing) {
            type = MessageType.RESPONSE;
        }

        saveMessage(type);

    }

    @Override
    public void request(byte[] buffer) {
        requesting = true;

        MessageType type = MessageType.RESPONSE;

        final boolean changeDirection = responsing;

        processBuffer(buffer, type, changeDirection);

    }

    @Override
    public void response(byte[] buffer) {
        responsing = true;

        MessageType type = MessageType.REQUEST;

        final boolean changeDirection = requesting;

        processBuffer(buffer, type, changeDirection);
    }

    private void processBuffer(byte[] buffer, MessageType type, boolean changeDirection) {
        if (changeDirection) {
            saveMessage(type);
        }

        for (Byte byt : buffer) {
            incomming.add(byt);
        }
    }

    private void saveMessage(MessageType type) {
        final Message message = new Message(type, incomming);
        getMessages().add(message);
        incomming = new ArrayList<>();
    }


    public List<Message> getMessages() {
        return messages;
    }
}
