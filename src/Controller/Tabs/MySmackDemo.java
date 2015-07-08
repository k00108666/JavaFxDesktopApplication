package Controller.Tabs;

/**
 * Created by Seamy on 7/2/2015.
 */
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import java.util.Collection;


public class MySmackDemo implements MessageListener {

    public static String username="seamo123@gmail.com";
    public static String password="01063504983";
    public static String id="";
    static ConnectionConfiguration config;
    XMPPConnection conn;
    public static Chat chat;
    public static XMPPConnection connection = new XMPPConnection("gmail.com");
    public static boolean free=false;

    public static void main(String args[]){




        System.out.println("... in main function ......." );
        MySmackDemo mySmackObj = new MySmackDemo();
        mySmackObj.connectTOGTalk();



        mySmackObj.displayBuddyList();
        System.out.println("-----");

        String msg="";

        try {

            mySmackObj.sendMessage("hello from smack", "seamo123test@gmail.com");
            System.out.println("trying to send message");
        }
        catch(Exception e){
            System.out.println("sending failed");
            e.printStackTrace();
        }

    }

    private static void connectTOGTalk(){

        try {
            //establish connection between client and server.
            connection.connect();
            System.out.println("Connected to " + connection.getHost());
            //call base class function to get login
            connection.login(username,password);

            System.out.println(connection.isAuthenticated());

            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            System.out.println("presence is ............" + presence.toXML());

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Function to send message to specified user
     */
    public void sendMessage(String message, String to) throws XMPPException
    {
        System.out.println("Message is ......."+message);
        Chat chat = connection.getChatManager().createChat(to, new MySmackDemo());
        chat.sendMessage(message);

        System.out.println("Chat obj is ........"+ chat);
    }
    /**
     * Function to display user list
     */
    public void displayBuddyList()
    {
        Roster roster = connection.getRoster();
        roster.setSubscriptionMode(Roster.SubscriptionMode.manual);
        Collection<RosterEntry> entries = roster.getEntries();

        System.out.println("\n\n" + entries.size() + " buddy(ies):");
        for(RosterEntry r:entries)
        {

            System.out.println(r.getUser());
            System.out.println(r.getName());
            try {
                sendMessage("Hi this is working", "nialloc9@gmail.com");
            } catch (XMPPException e) {
                e.printStackTrace();
            }
        }
    }

    public void processMessage(Chat chat, Message message)
    {
        if(message.getType() == Message.Type.chat)
            System.out.println(chat.getParticipant() + " says: " + message.getBody());
    }
}