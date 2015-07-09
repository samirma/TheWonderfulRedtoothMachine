package com.antonio.samir.wonderfulredtooth.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManager;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by samir on 7/8/15.
 */
public class ListernerBluetoothThread extends Thread {

    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "RedtoothService";

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fb87c0d0-afac-11de-8a39-0840200c9a61");


    private final BluetoothServerSocket mmServerSocket;

    private ProxyManager proxyManager;

    public ListernerBluetoothThread(final ProxyManager proxyManager) {

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, MY_UUID_SECURE);

        } catch (IOException e) {
        }
        mmServerSocket = tmp;

        this.proxyManager = proxyManager;

    }

    public void run() {
        BluetoothSocket socket = null;
        // Keep listening until exception occurs or a socket is returned
        while (true) {
            try {
                socket = mmServerSocket.accept();
            } catch (IOException e) {
                break;
            }
            // If a connection was accepted
            if (socket != null) {
                // Do work to manage the connection (in a separate thread)
                manageConnectedSocket(socket);

            }
        }
    }

    private void manageConnectedSocket(final BluetoothSocket socket) {
        proxyManager.setClientSocket(socket);
    }

}