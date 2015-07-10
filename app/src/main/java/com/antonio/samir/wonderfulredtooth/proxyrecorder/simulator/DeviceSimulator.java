package com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator;

/**
 * Created by samir on 7/10/15.
 */

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

interface DeviceSimulator {
    void start(InputStream input, OutputStream outputStream, List<Message> messageList);
}
