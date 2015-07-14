package com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class ClientDeviceSimulatorSequecial extends DeviceSimulatorSequecial implements ClientDeviceSimulator {

    @Override
    /**
     * It response message goes to output
     */
    public void start(final InputStream input, final OutputStream output, final List<Message> messageList) {
        final MessageType messageType = MessageType.REQUEST;

        writeInSequence(input, output, messageList, messageType);
    }

}
