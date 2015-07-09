package com.antonio.samir.wonderfulredtooth.proxyrecorder;

import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;

import java.io.IOException;

/**
 * Created by samir on 7/9/15.
 */
public interface ProxyManager {
    void doProxy(BeanLink beanLink);

    void setClientSocket(BluetoothSocket socket);

    void setServerSocket(BluetoothSocket socket);

    void connectToAdress(String address, boolean secure) throws IOException;
}
