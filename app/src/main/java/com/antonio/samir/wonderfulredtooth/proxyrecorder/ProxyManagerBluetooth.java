package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.ConversationRecorder;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.SequencialRecorder;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderClientToServer;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies.RecorderServerToClient;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulatorSequecial;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.DeviceSimulatorSequecial;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ServerDeviceSimulatorSequecial;

import java.io.IOException;
import java.util.UUID;

public class ProxyManagerBluetooth implements ProxyManager {

    private static final String TAG = "ProxyManagerBluetooth";

    // Unique UUID for this application
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private BeanLink beanLink;

    private BluetoothAdapter mBluetoothAdapter = null;

    private BluetoothSocket clientSocket;
    private BluetoothSocket serverSocket;

    private ProxyManagerHandle handle;

    private boolean isEndPointReady;
    private boolean isStartPointReady;

    private ConversationRecorder conversationRecorder;


    public ProxyManagerBluetooth(final ProxyManagerHandle handle) {
        this.beanLink = new BeanLink();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.handle = handle;
        beanLink.handle = handle;
        isEndPointReady = false;
        isStartPointReady = false;

        conversationRecorder = new SequencialRecorder();

    }

    @Override
    public void doProxy() {
        try {

            conversationRecorder.start();

            final RecorderServerToClient serverToClient = new RecorderServerToClient(beanLink, this);

            final RecorderClientToServer clientToServer = new RecorderClientToServer(beanLink, this);

            new Thread(serverToClient).start();
            new Thread(clientToServer).start();

            handle.proxyStarted();


        } catch (Exception ex) {
            handle.proxyBroken();
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

    @Override
    public void response(byte[] buffer) {
        conversationRecorder.response(buffer);
    }

    @Override
    public void request(byte[] buffer) {
        conversationRecorder.request(buffer);
    }

    @Override
    public void simulateServer() {
        DeviceSimulatorSequecial deviceSimulator = new ServerDeviceSimulatorSequecial();
        try {
            deviceSimulator.start(beanLink.client.getInputStream(), beanLink.client.getOutputStream(), conversationRecorder.getMessages());
        } catch (IOException e) {
            Log.e(TAG, null, e);
        }
    }

    @Override
    public void simulateClient() {
        DeviceSimulatorSequecial deviceSimulator = new ClientDeviceSimulatorSequecial();
        try {
            deviceSimulator.start(beanLink.server.getInputStream(), beanLink.server.getOutputStream(), conversationRecorder.getMessages());
        } catch (IOException e) {
            Log.e(TAG, null, e);
        }
    }

    @Override
    public void stopRecorder() {
        conversationRecorder.stop();
    }


    private void checkViablity() {
        if (isEndPointReady && isStartPointReady) {
            handle.readyToStart();
        }
    }

}
