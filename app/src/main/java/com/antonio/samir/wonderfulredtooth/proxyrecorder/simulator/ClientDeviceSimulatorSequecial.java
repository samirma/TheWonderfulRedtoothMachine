package com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator;

import android.util.Log;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by samir on 7/10/15.
 */
public class ClientDeviceSimulatorSequecial implements ClientDeviceSimulator {
    private static String TAG = "ClientDeviceSimulatorSequecial";
    @Override
    /**
     * It response message goes to output
     */
    public void start(InputStream input, OutputStream output, final List<Message> messageList) {
        for (Message message : messageList) {
            if (MessageType.RESPONSE == message.type) {
                try {
                    output.write(message.content);
                } catch (IOException e) {
                    Log.e(TAG, null, e);
                }
            }
        }
    }
}
