package com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;

import java.io.IOException;

/**
 * Created by samir on 7/8/15.
 */
public class RecorderClientToServer extends LinkRecorder {

    public RecorderClientToServer(final BeanLink beanLink) throws IOException {
        getStreams(beanLink.client, beanLink.server);
    }

}
