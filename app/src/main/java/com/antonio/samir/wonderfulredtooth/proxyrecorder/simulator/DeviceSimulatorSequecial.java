package com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator;

import android.util.Log;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by samir on 7/13/15.
 */
public abstract class DeviceSimulatorSequecial implements DeviceSimulator {
    private static String TAG = "DeviceSimulatorSequecial";


    protected void writeInSequence(final InputStream input, final OutputStream output, List<Message> messageList, MessageType messageType) {
        MessageType lastType = null;

        try {
            for (Message message : messageList) {
                final MessageType currentType = message.type;
                if (messageType == currentType) {

                    if (lastType == currentType) {
                        Thread.sleep(1000);
                    }

                    output.write(message.content);

                } else {
                    readInput(input);
                }

                lastType = currentType;

            }
        } catch (IOException | InterruptedException e) {
            Log.e(TAG, null, e);
        }
    }

    private void readInput(final InputStream input) throws IOException {
        final int avaliable = input.read();
        final byte[] buffer = new byte[avaliable];
        input.read(buffer);
    }
}
