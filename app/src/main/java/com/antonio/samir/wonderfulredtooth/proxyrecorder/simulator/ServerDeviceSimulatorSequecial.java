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
public class ServerDeviceSimulatorSequecial implements ServerDeviceSimulator {
    private static String TAG = "ServerDeviceSimulatorSequecial";

    @Override
    /**
     * It request message goes to output
     */
    public void start(final InputStream input, final OutputStream output, final List<Message> messageList) {
        for (Message message : messageList) {
            if (MessageType.REQUEST == message.type) {
                try {
                    output.write(message.content);
                } catch (IOException e) {
                    Log.e(TAG, null, e);
                }
            }
        }
    }
}
