package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by samir on 7/9/15.
 */
public interface ProxyManager {
    void doProxy();

    void setClientSocket(BluetoothSocket socket);

    void setServerSocket(BluetoothSocket socket);

    void connectToAdress(String address, boolean secure) throws IOException;

    UUID getUuid();

    UUID getUuidSecure();

    void response(final byte[] buffer);

    void request(final byte[] buffer);

    void simulateServer();

    void simulateClient();

    List<Message> stopRecorder();

    void closeConnections();
}
