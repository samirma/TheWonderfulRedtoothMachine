package com.antonio.samir.conservation;

import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.Message;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.MessageType;
import com.antonio.samir.wonderfulredtooth.proxyrecorder.conservation.SequencialRecorder;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.List;


public class SequencialRecorderTest extends TestCase {

    private SequencialRecorder sequencialRecorder = null;

    public SequencialRecorderTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(SequencialRecorderTest.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        sequencialRecorder = new SequencialRecorder();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Method: start()
     */
    public void testStart() throws Exception {
        Assert.assertNull(sequencialRecorder.getMessages());
        sequencialRecorder.start();
        final List<Message> messages = sequencialRecorder.getMessages();
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() == 0);
    }

    /**
     * Method: stop()
     */
    public void testStop() throws Exception {
        Assert.assertNull(sequencialRecorder.getMessages());
        sequencialRecorder.start();
        final List<Message> messages = sequencialRecorder.getMessages();
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.size() == 0);

        sequencialRecorder.stop();

        Assert.assertNull(sequencialRecorder.getMessages());

    }

    /**
     * Method: request(byte[] buffer)
     */
    public void testRequest() throws Exception {
        sequencialRecorder.start();

        final String string1 = "request";
        final String string2 = "new_request";

        sequencialRecorder.request(string1.getBytes());

        sequencialRecorder.request(string2.getBytes());

        sequencialRecorder.messageSent();

        List<Message> messages = sequencialRecorder.getMessages();

        Assert.assertEquals(1, messages.size());

        final Message message = messages.get(0);

        Assert.assertEquals(message.type, MessageType.REQUEST);

        final byte[] content = message.getContentAsArray();

        String expected = String.format("%s%s", string1, string2);

        final String actual = new String(content);

        Assert.assertEquals(expected, actual);

    }

    /**
     * Method: response(byte[] buffer)
     */
    public void testResponse() throws Exception {
        sequencialRecorder.start();

        final String string1 = "request";
        final String string2 = "new_request";

        sequencialRecorder.response(string1.getBytes());

        sequencialRecorder.response(string2.getBytes());

        sequencialRecorder.messageSent();

        List<Message> messages = sequencialRecorder.getMessages();

        Assert.assertEquals(1, messages.size());

        final Message message = messages.get(0);

        Assert.assertEquals(message.type, MessageType.RESPONSE);

        final byte[] content = message.getContentAsArray();

        String expected = String.format("%s%s", string1, string2);

        final String actual = new String(content);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Method: getMessages()
     */
    public void testGetMessagesConversation() throws Exception {
        sequencialRecorder.start();

        final String string1 = "request";
        final String string2 = "new_request";

        final String response1 = "response";
        final String response2 = "new_response";

        sequencialRecorder.request(string1.getBytes());

        sequencialRecorder.request(string2.getBytes());

        sequencialRecorder.response(response1.getBytes());

        sequencialRecorder.response(response2.getBytes());

        List<Message> messages = sequencialRecorder.getMessages();

        Assert.assertEquals(1, messages.size());

        sequencialRecorder.messageSent();

        messages = sequencialRecorder.getMessages();

        Assert.assertEquals(2, messages.size());

        final Message message = messages.get(0);

        Assert.assertEquals(message.type, MessageType.REQUEST);

        final byte[] content = message.getContentAsArray();

        String expected = String.format("%s%s", string1, string2);

        final String actual = new String(content);

        Assert.assertEquals(expected, actual);
    }

    /**
     * Method: processBuffer(byte[] buffer, MessageType type, boolean changeDirection)
     */
    public void testProcessBuffer() throws Exception {


    }

    /**
     * Method: saveMessage(MessageType type)
     */
    public void testSaveMessage() throws Exception {

    }
} 
