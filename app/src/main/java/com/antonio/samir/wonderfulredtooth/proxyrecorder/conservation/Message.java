package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class Message {

    public MessageType type;
    public List<Byte> content;

    public Message(MessageType type, List<Byte> content) {
        this.type = type;
        this.content = content;
    }

}

