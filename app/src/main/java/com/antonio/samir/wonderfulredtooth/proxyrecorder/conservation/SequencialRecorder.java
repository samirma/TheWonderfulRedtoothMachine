package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import java.util.ArrayList;

/**
 * Created by samir on 7/10/15.
 */
public class SequencialRecorder implements ConversationRecorder {

    private ArrayList<Byte> incomming;

    private boolean requesting;
    private boolean responsing;

    private ArrayList<Message> mensages;

    @Override
    public void start() {
        requesting = false;
        responsing = false;
        incomming = new ArrayList<>();
        mensages = new ArrayList<>();
    }

    @Override
    public void stop() {

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
            final Message message = new Message(type, incomming);
            mensages.add(message);
            incomming = new ArrayList<>();
        }

        for (Byte byt : buffer) {
            incomming.add(byt);
        }
    }


}
