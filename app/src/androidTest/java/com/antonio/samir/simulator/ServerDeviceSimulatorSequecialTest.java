package com.antonio.samir.simulator;

import com.antonio.samir.tool.MessageUtil;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ServerDeviceSimulator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ServerDeviceSimulatorSequecial;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.List;

/**
 * ClientDeviceSimulatorSequecial Tester.
 */
public class ServerDeviceSimulatorSequecialTest extends TestCase {

    ServerDeviceSimulator simulator;

    public ServerDeviceSimulatorSequecialTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ServerDeviceSimulatorSequecialTest.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        simulator = new ServerDeviceSimulatorSequecial();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: start(InputStream input, OutputStream outputStream, List<Message> messageList)
     */
    public void testStart() throws Exception {
        final List<Message> msgs = MessageUtil.getSampleMessages();


        final PipedOutputStream pipedOutputStream = new PipedOutputStream();


        final InputStream input = new PipedInputStream(pipedOutputStream);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        new Thread() {
            @Override
            public void run() {
                simulator.start(input, outputStream, msgs);
            }
        }.start();

        List<Message> msgsRequest = MessageUtil.filterMessage(msgs, MessageType.REQUEST);
        List<Message> msgsResponse = MessageUtil.filterMessage(msgs, MessageType.RESPONSE);

        int index = 0;

        for (Message msg : msgsRequest) {

            pipedOutputStream.write("req".getBytes());

            Thread.sleep(200);

            final String outputResponse = new String(outputStream.toByteArray());

            final String stringRightResponse = new String(msgsResponse.get(index).content);

            Assert.assertTrue("Response messages shoud be the same", StringUtils.contains(outputResponse, stringRightResponse));

            index++;

        }

        Thread.sleep(1000);

        final String outputResponse = new String(outputStream.toByteArray());

        final String stringRightResponse = new String(msgsResponse.get(msgsResponse.size() - 1).content);

        final boolean contains = outputResponse.contains(stringRightResponse);

        Assert.assertTrue("Response messages shoud be the same", contains);


    }
} 
