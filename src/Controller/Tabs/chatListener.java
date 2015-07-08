package Controller.Tabs;

import application.Main;
import javafx.scene.control.*;
import javafx.scene.paint.*;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import java.awt.*;
import java.awt.Color;

import static java.awt.Color.RED;
import static java.awt.Color.cyan;

/**
 * Created by Seamy on 7/8/2015.
 */
public class chatListener implements MessageListener {

    public void processMessage(Chat chat, Message message) {
        if (message.getBody()!= null){
        if(message.getFrom() != GChatTab.userList.get(Main.gChatrecipient)){


            Main.GchatText.appendText("\n" + GChatTab.nameList.get(Main.gChatrecipient));
            System.out.println("Printed option 1");




        }else {


            for(int x =0; x < GChatTab.userList.size(); x++){

                if (GChatTab.userList.get(x) == message.getFrom()){


                    Main.GchatText.appendText("\n" + GChatTab.nameList.get(x));
                    System.out.println("Printed option 2");
                    break;

                }



            }



        }




        }




        }}


