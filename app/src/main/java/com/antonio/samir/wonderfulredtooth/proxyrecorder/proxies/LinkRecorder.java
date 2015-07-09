package com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class LinkRecorder
        implements Runnable {

    protected BluetoothSocket inputSocket;
    protected InputStream is;
    protected OutputStream os;


    protected void getStreams(final BluetoothSocket inputSocket, final BluetoothSocket outputSocket) throws IOException {
        is = inputSocket.getInputStream();
        os = outputSocket.getOutputStream();
    }


    public void run() {
        try {
            byte[] buffer = new byte[10240];
            int lenRead = 0;
            while ((lenRead = this.is.read(buffer, 0, buffer.length)) > 0) {
                this.os.write(buffer, 0, lenRead);
                this.os.flush();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                this.inputSocket.close();
            } catch (Exception exSocket) {
            }
        }
    }
}
