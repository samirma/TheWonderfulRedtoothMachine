package com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation;

/**
 * Created by samir on 7/10/15.
 */
public interface ConversationRecorder {
    void start();

    void stop();

    void request(byte[] buffer);

    void response(byte[] buffer);
}
