package com.antonio.samir.wonderfulredtooth.proxyrecorder;

/**
 * Created by samir on 7/9/15.
 */
public interface ProxyManagerHandle {
    void readyToStart();

    void startPointReady();

    void endPointReady();

    void proxyBroken();

    void proxyStarted();

}
