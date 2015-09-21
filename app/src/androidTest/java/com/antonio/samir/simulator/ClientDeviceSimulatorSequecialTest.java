package com.antonio.samir.simulator;

import com.antonio.samir.tool.MessageUtil;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulatorSequecial;

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
public class ClientDeviceSimulatorSequecialTest extends TestCase {

    ClientDeviceSimulator simulator;

    public ClientDeviceSimulatorSequecialTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(ClientDeviceSimulatorSequecialTest.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        simulator = new ClientDeviceSimulatorSequecial();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     *
     * Method: start(InputStream input, OutputStream outputStream, List<Message> messageList)
     *
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

        Thread.sleep(1000);

        List<Message> msgsRequest = MessageUtil.filterMessage(msgs, MessageType.REQUEST);
        List<Message> msgsResponse = MessageUtil.filterMessage(msgs, MessageType.RESPONSE);

        int index = 0;

        for (Message msg : msgsResponse) {

            final byte[] data = outputStream.toByteArray();
            final String outputRequest = new String(data);


            if (msgsRequest.size() < index) {

                final String stringRightRequest = new String(msgsRequest.get(index).content);

                final boolean contains = StringUtils.contains(outputRequest, stringRightRequest);

                final String messageError = String.format("Response messages should be the same %s %s", outputRequest, stringRightRequest);

                Assert.assertTrue(messageError, contains);

            }

            pipedOutputStream.write(msg.content);

            Thread.sleep(1500);

            index++;

        }


    }
} 
