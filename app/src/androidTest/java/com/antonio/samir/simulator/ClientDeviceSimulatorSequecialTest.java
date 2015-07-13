package com.antonio.samir.simulator;

import com.antonio.samir.tool.MessageCreator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulatorSequecial;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
        List<Message> msgs = MessageCreator.getSampleMessages();

        final String response = "first_responsesecond_responsegoodbye_response";

        byte[] inputByte = new byte[1];

        final ByteArrayInputStream input = new ByteArrayInputStream(inputByte);

        final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        simulator.start(input, outputStream, msgs);

        final String stringResponse = new String(outputStream.toByteArray());

        Assert.assertTrue("Response messages shoud be the same", StringUtils.equals(stringResponse, response));


    }
} 