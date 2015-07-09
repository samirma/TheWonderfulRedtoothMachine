/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.antonio.samir.wonderfulredtooth.proxyrecorder.bean;

import android.bluetooth.BluetoothSocket;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManagerHandle;

/**
 * @author sam
 */
public class BeanLink {

    public BluetoothSocket client;
    public BluetoothSocket server;

    public ProxyManagerHandle handle;
}
