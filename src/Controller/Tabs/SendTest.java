package Controller.Tabs;

/**
 * Created by Seamy on 7/2/2015.
 */


        import java.io.IOException;
        import org.jivesoftware.smack.Chat;
        import org.jivesoftware.smack.ChatManager;
        import org.jivesoftware.smack.ConnectionConfiguration;
        import org.jivesoftware.smack.MessageListener;
        import org.jivesoftware.smack.XMPPConnection;
        import org.jivesoftware.smack.XMPPException;
        import org.jivesoftware.smack.packet.Message;
        import org.jivesoftware.smack.packet.Presence;

public class SendTest {

    public static class MessageParrot implements MessageListener {

        private Message msg = new Message("nialloc9@gmail.com" , Message.Type.chat);

        // gtalk seems to refuse non-chat messages
        // messages without bodies seem to be caused by things like typing
        public void processMessage(Chat chat, Message message) {
            if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
                System.out.println("Received: " + message.getBody());
                try {
                    msg.setBody("I am a Java bot. You said: " + message.getBody());
                    chat.sendMessage(msg);
                } catch (XMPPException ex) {
                    //ex.printStackTrace();
                    System.out.println("Failed to send message");
                }
            } else {
                System.out.println("I got a message I didn''t understand");
            }
        }
    }


    public static void main( String[] args ) {

        System.out.println("Starting IM client");

        // gtalk requires this or your messages bounce back as errors
        ConnectionConfiguration connConfig = new ConnectionConfiguration("talk.google.com", 5222, "gmail.com");
        XMPPConnection connection = new XMPPConnection(connConfig);

        try {
            connection.connect();
            System.out.println("Connected to " + connection.getHost());
        } catch (XMPPException ex) {
            //ex.printStackTrace();
            System.out.println("Failed to connect to " + connection.getHost());
            System.exit(1);
        }
        try {
            connection.login("", "");
            System.out.println("Logged in as " + connection.getUser());

            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);

        } catch (XMPPException ex) {
            //ex.printStackTrace();
            System.out.println("Failed to log in as " + connection.getUser());
            System.exit(1);
        }

        ChatManager chatmanager = connection.getChatManager();
        Chat chat = chatmanager.createChat("nialloc9@gmail.com", new MessageParrot());

        try {
            // google bounces back the default message types, you must use chat
            Message msg = new Message("nialloc9@gmail.com", Message.Type.chat);
            msg.setBody("Test");
            chat.sendMessage(msg);
        } catch (XMPPException e) {
            System.out.println("Failed to send message");
            // handle this how?
        }

        System.out.println("Press enter to disconnect");

        try {
            System.in.read();
        } catch (IOException ex) {
            //ex.printStackTrace();
        }

        connection.disconnect();
    }
}

