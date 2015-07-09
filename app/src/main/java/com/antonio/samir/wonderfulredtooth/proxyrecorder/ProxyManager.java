package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
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

}
