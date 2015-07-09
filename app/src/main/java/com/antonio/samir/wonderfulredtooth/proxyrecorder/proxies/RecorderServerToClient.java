package com.antonio.samir.wonderfulredtooth.proxyrecorder.proxies;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.bean.BeanLink;

import java.io.IOException;

/**
 * Created by samir on 7/8/15.
 */
public class RecorderServerToClient extends LinkRecorder {

    public RecorderServerToClient(final BeanLink beanLink) throws IOException {
        getStreams(beanLink.server, beanLink.client);
    }
}
