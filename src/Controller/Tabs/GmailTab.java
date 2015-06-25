package Controller.Tabs;

/**
 * Created by Seamy on 6/25/2015.
 */


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.DefaultListModel;

public class GmailTab {

    public  Folder folder;
    public Store store;
    public  Message messages[];
    public static int numOfEmails = 10;
    public static int emailId = 1;





    public final void doConnect() {

        try{
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com","seamo123@gmail.com", "01063504983");
            folder = store.getFolder("Inbox");
      /* Others GMail folders :
       * [Gmail]/All Mail   This folder contains all of your Gmail messages.
       * [Gmail]/Drafts     Your drafts.
       * [Gmail]/Sent Mail  Messages you sent to other people.
       * [Gmail]/Spam       Messages marked as spam.
       * [Gmail]/Starred    Starred messages.
       * [Gmail]/Trash      Messages deleted from Gmail.
       */
            folder.open(Folder.READ_WRITE);
            Message messages[] = folder.getMessages();

            System.out.println("Connected to gmail");


        }catch (Exception ex){


            System.out.println("Error in connecting to Gmail");
        }

    }


    public static void GetEmails(){


        GmailTab gmail = new GmailTab();
        gmail.doConnect();





    }


    public DefaultListModel getAddresses() {


        DefaultListModel dm = new DefaultListModel();


        try {
            GmailTab gmail = new GmailTab();
            folder = gmail.folder;

            messages = folder.getMessages();




            for (int x = 0 ; x < numOfEmails; x++){


                int y = x + 1;

                Message message = messages[messages.length - y];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                Object emailOb = email;



                dm.addElement(emailOb);




            }


        } catch (MessagingException ex) {

        }


        return dm;

    }


    public DefaultListModel getExtraAdresses() {

        DefaultListModel dm = new DefaultListModel();


        try {








            for (int x = 0 ; x < numOfEmails; x++){


                int y = x + 1;

                Message message = messages[messages.length - y];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                Object emailOb = email;



                dm.addElement(emailOb);




            }


        } catch (MessagingException ex) {

        }


        return dm;







    }


    public Message getMessage () {

        int messageLocation = messages.length - emailId;
        return messages[messageLocation];




    }






}
