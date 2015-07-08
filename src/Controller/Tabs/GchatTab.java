package Controller.Tabs;

import application.Main;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Presence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Seamy on 6/25/2015.
 */




public class GChatTab implements MessageListener {

    public static String id = "";
    static ConnectionConfiguration config;
    XMPPConnection conn;
    public static Chat chat;
    public static XMPPConnection connection = new XMPPConnection("gmail.com");
    public static boolean free = false;


    ChatManager chatManager;
    MessageListener messageListener;
    public List<String> userList=new ArrayList<>();
    public List<String> nameList = new ArrayList<>();










    public  void connectTOGTalk() {

        try {



            //establish connection between client and server.
            connection.connect();
            System.out.println("Connected to " + connection.getHost());
            //call base class function to get login
            connection.login(Main.username, Main.password);

            System.out.println(connection.isAuthenticated());

            Presence presence = new Presence(Presence.Type.available);
            connection.sendPacket(presence);
            System.out.println("presence is ............" + presence.toXML());



            chatManager = connection.getChatManager();

            connection.getChatManager().addChatListener(new ChatManagerListener() {
                @Override
                public void chatCreated(Chat chat, boolean b) {


                    chat.addMessageListener(new MessageListener() {
                        @Override
                        public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                            System.out.println("Received message: "
                                    + (message != null ? message.getBody() : "NULL"));
                        }
                    });

                }


                {

                }
            });





        }catch (Exception x)  {

            System.out.println(x);

        }



    }

                    public void sendMessage(String message, int to)  {
                        System.out.println("Message is ......." + message + "to" + to);
                        String userId = userList.get(to);

                        chat = connection.getChatManager().createChat(userId, new MySmackDemo());
                        try {
                            chatManager.createChat(userId, new MySmackDemo());

                            chat.sendMessage(message);
                        } catch (XMPPException e) {
                            System.out.println("Error sending message:" + message);                       }


                       System.out.println("Chat obj is ........" + chat);
                    }


                    public void displayBuddyList() {
                        Roster roster = connection.getRoster();
                        roster.setSubscriptionMode(Roster.SubscriptionMode.manual);

                        System.out.println("\n\n" + roster.getEntries().size() + " buddy(ies):");

                            for (RosterEntry entry : roster.getEntries()) {
                                //Main.recipients[x] = entry.getUser().toString();
                                userList.add(entry.getUser());
                                nameList.add(entry.getName());

                                System.out.println("RosterEntry " + entry);

                                System.out.println("User: [" + entry.getUser() + "]");
                                System.out.println("Name: " + entry.getName());

                                Presence presence = roster.getPresence(entry.getUser());
                                if (presence == null) {
                                    System.out.println("User is off-line");

                                } else {
                                    System.out.println("User is on-line");
                                }

                                System.out.println("Present Type: " + presence.getType());
                                System.out.println("Present Status: " + presence.getStatus());

                            }

                        for (int t = 0; t < nameList.size(); t++){

                            Main.buddyList.getItems().add(nameList.get(t));



                        }



                        }

                    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
                        if (message.getType() == org.jivesoftware.smack.packet.Message.Type.chat)
                            Main.GchatText.appendText(message.getBody());
                    }
                }