package com.antonio.samir.simulator;

import com.antonio.samir.tool.MessageCreator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulator;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.simulator.ClientDeviceSimulatorSequecial;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;

/**
 * ClientDeviceSimulatorSequecial Tester.
 */
public class ClientDeviceSimulatorSequecialTest extends TestCase {

    ClientDeviceSimulator simulator;

    public ClientDeviceSimulatorSequecialTest(String name) {
        super(name);
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

    }



    public static Test suite() {
        return new TestSuite(ClientDeviceSimulatorSequecialTest.class);
    }
} 
