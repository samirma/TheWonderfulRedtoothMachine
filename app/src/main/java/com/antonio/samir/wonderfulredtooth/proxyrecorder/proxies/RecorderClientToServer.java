package com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.ProxyManager;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;

import java.io.IOException;

/**
 * Created by samir on 7/8/15.
 */
public class RecorderClientToServer extends LinkRecorder {

    public RecorderClientToServer(final BeanLink beanLink, final ProxyManager proxyManager) throws IOException {
        super(proxyManager);
        getStreams(beanLink.client, beanLink.server);
    }

    @Override
    protected void write(final byte[] buffer) {
        proxyManager.request(buffer);
    }

}
