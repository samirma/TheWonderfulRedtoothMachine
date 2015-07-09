package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderClientToServer;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderServerToClient;

import java.io.IOException;
import java.util.UUID;

public class ProxyManagerBluetooth implements ProxyManager {

    private static final String TAG = "ProxyManagerBluetooth";

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private BeanLink beanLink;
    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    private BluetoothSocket clientSocket;
    private BluetoothSocket serverSocket;

    private ProxyManagerHandle handle;

    private boolean isEndPointReady;
    private boolean isStartPointReady;

    public ProxyManagerBluetooth(final ProxyManagerHandle handle) {
        this.beanLink = new BeanLink();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.handle = handle;
        isEndPointReady = false;
        isStartPointReady = false;
    }

    @Override
    public void doProxy() {
        try {

            final RecorderServerToClient serverToClient = new RecorderServerToClient(beanLink);

            final RecorderClientToServer clientToServer = new RecorderClientToServer(beanLink);

            new Thread(serverToClient).start();
            new Thread(clientToServer).start();

        } catch (Exception ex) {
            Log.e(TAG, "Fail", ex);
        }
    }

    @Override
    public void setClientSocket(final BluetoothSocket socket) {
        clientSocket = socket;
        beanLink.client = clientSocket;
        handle.startPointReady();
        isStartPointReady = true;
        checkViablity();
    }


    @Override
    public void setServerSocket(final BluetoothSocket socket) {
        serverSocket = socket;
        beanLink.server = serverSocket;
    }

    @Override
    public void connectToAdress(final String address, boolean secure) throws IOException {
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device

        final BluetoothSocket mmSocket;

        if (secure) {
            mmSocket = device.createRfcommSocketToServiceRecord(
                    getUuidSecure());
        } else {
            mmSocket = device.createInsecureRfcommSocketToServiceRecord(
                    getUuid());
        }

        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
        } catch (IOException e) {
            Log.e(TAG, "fail to connect", e);
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }


        setServerSocket(mmSocket);

        isEndPointReady = true;

        handle.endPointReady();
        checkViablity();

    }

    @Override
    public UUID getUuid() {
        return MY_UUID_INSECURE;
    }

    @Override
    public UUID getUuidSecure() {
        return MY_UUID_SECURE;
    }


    private void checkViablity() {
        if (isEndPointReady && isStartPointReady) {
            handle.readyToStart();
        }
    }

}
