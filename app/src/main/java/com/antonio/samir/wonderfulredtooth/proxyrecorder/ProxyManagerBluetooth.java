package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderClientToServer;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderServerToClient;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProxyManagerBluetooth implements ProxyManager {

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("fa87c0d0-affc-11de-8b39-0800200c9a66");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("2ce255c0-200a-11e0-ab64-0800200c9a66");
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
    public void doProxy(final BeanLink beanLink) {
        try {


            final RecorderServerToClient serverToClient = new RecorderServerToClient(beanLink);

            final RecorderClientToServer clientToServer = new RecorderClientToServer(beanLink);

            new Thread(serverToClient).start();
            new Thread(clientToServer).start();

        } catch (Exception ex) {
            Logger.getLogger(ProxyManagerBluetooth.class.getName()).log(Level.SEVERE, null, ex);
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

        final BluetoothSocket btSocket;

        if (secure) {
            btSocket = device.createRfcommSocketToServiceRecord(
                    MY_UUID_SECURE);
        } else {
            btSocket = device.createInsecureRfcommSocketToServiceRecord(
                    MY_UUID_INSECURE);
        }

        setServerSocket(btSocket);

        isEndPointReady = true;

        handle.endPointReady();

    }


    private void checkViablity() {
        if (isEndPointReady && isStartPointReady) {
            handle.readyToStart();
        }
    }

}
