package com.antonio.samir.wonderfulredtooth.network;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManager;

import java.io.IOException;

/**
 * Created by samir on 7/8/15.
 */
public class ListernerBluetoothThread extends Thread {

    private static final String TAG = "ListernerBluetooth";


    // Name for the SDP record when creating server socket
    private static final String NAME_SECURE = "BluetoothChatSecure";
    private static final String NAME_INSECURE = "BluetoothChatInsecure";


    private final BluetoothServerSocket mmServerSocket;

    private ProxyManager proxyManager;

    public ListernerBluetoothThread(final ProxyManager proxyManager) {

        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        BluetoothServerSocket tmp = null;
        try {
            // MY_UUID is the app's UUID string, also used by the client code
            tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME_SECURE, proxyManager
            .getUuidSecure());

        } catch (Exception ex) {
            Log.e(TAG, "Fail", ex);
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