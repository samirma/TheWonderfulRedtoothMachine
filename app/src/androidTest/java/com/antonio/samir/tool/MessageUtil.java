package com.antonio.samir.tool;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samir on 7/13/15.
 */
public class MessageUtil {
    public static List<Message> getSampleMessages() {

        List<Message> messages = new ArrayList<>();

        messages.add(new Message(MessageType.REQUEST, "first_request".getBytes()));
        messages.add(new Message(MessageType.RESPONSE, "first_response".getBytes()));

        messages.add(new Message(MessageType.REQUEST, "second_request".getBytes()));
        messages.add(new Message(MessageType.RESPONSE, "second_response".getBytes()));

        messages.add(new Message(MessageType.RESPONSE, "goodbye_response".getBytes()));

        return messages;
    }

    public static List<Message> filterMessage(final List<Message> messageList, final MessageType type) {

        List<Message> messages = new ArrayList<>();

        for (Message message : messageList) {
            if (type == message.type) {
                messages.add(message);
            }
        }

        return messages;

    }
}
