package com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies;

import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public abstract class LinkRecorder
        implements Runnable {

    protected BluetoothSocket inputSocket;
    protected InputStream is;
    protected OutputStream os;
    protected ProxyManager proxyManager;

    public LinkRecorder(ProxyManager proxyManager) {
        this.proxyManager = proxyManager;
    }


    protected void getStreams(final BluetoothSocket inputSocket, final BluetoothSocket outputSocket) throws IOException {
        is = inputSocket.getInputStream();
        os = outputSocket.getOutputStream();
    }


    public void run() {
        try {
            byte[] buffer = new byte[10240];
            int lenRead = 0;
            final int offset = 0;
            while ((lenRead = this.is.read(buffer, offset, buffer.length)) > 0) {
                this.os.write(buffer, offset, lenRead);
                this.os.flush();
                writeToProxyManager(buffer, offset, lenRead);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            try {
                this.inputSocket.close();
            } catch (Exception exSocket) {
            }
        }
    }

    private void writeToProxyManager(byte[] buffer, int i, int lenRead) {
        final byte[] bytes = Arrays.copyOfRange(buffer, i, lenRead);
        write(bytes);
    }

    protected abstract void write(byte[] buffer);
}
