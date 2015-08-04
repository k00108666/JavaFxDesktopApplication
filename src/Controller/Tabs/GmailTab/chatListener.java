package Controller.Tabs.GmailTab;

import application.Main;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;

/**
 * Created by Seamy on 7/8/2015.
 */
public class chatListener implements MessageListener {

    public void processMessage(Chat chat, Message message) {
        if (message.getBody()!= null){


           int x = 0;
            boolean success = false;
            String fromName = StringUtils.parseBareAddress(message.getFrom());

            for (int y =0; y < GChatTab.userList.size(); y++){
            System.out.println(fromName + " is not equal to " + chat.getParticipant());}




            for ( x = 0; x < GChatTab.userList.size(); x++) {

                if (GChatTab.userList.get(x) == fromName){

                    Main.GchatText.appendText("\n" + GChatTab.nameList.get(x) + ": \n" + message.getBody());
                    System.out.println("Printed option 1");
                    success = true;
                    break;

                }


            }

            if (success == false) {


                   System.out.println("message sent to: " + message.getTo());



                Main.GchatText.appendText("\n" + fromName + ": \n" + message.getBody());
                    System.out.println("Printed option 2");




            }




        }




        }}


