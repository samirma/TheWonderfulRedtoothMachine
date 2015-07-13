package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class Message {

    public MessageType type;
    public byte[] content;

    public Message(MessageType type, byte[] content) {
        this.type = type;
        this.content = content;
    }

    public Message(MessageType type, ArrayList<Byte> incomming) {
        this.type = type;
        this.content = getContentAsArray(incomming);
    }

    private byte[] getContentAsArray(final List<Byte> incomming) {
        byte[] contentsAsArray = new byte[incomming.size()];
        int index = 0;
        for (byte byt : incomming) {
            contentsAsArray[index] = byt;
            index++;
        }
        return contentsAsArray;
    }
}

