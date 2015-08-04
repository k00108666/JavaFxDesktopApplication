package Controller.Tabs.GmailTab;

/**
 * Created by Seamy on 6/25/2015.
 */



import application.Main;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.FlagTerm;
import javax.swing.DefaultListModel;


public class GmailTab {

    public static Folder folder;
    public static Store store;
    public static   Message messages[];
    public static int numOfEmails = 10;
    public static int emailId = 1;
    public static int  folderSize = 0;





    public final void doConnect() {

        try{
            //Connect to gmail, You can se your protocall here i.e Pop, Imap etc
            Properties props = System.getProperties();
            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            session.setDebug(true);
            store = session.getStore("imaps");
            store.connect("imap.gmail.com", Main.username, Main.password);
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
             messages = folder.getMessages();
            folderSize = messages.length;

        }catch (Exception ex){


            System.out.println("Error in connecting to Gmail");
        }

    }




    public  DefaultListModel getAddresses() {


        DefaultListModel dm = new DefaultListModel();



        try {
            //Connect and extract emails
            this.doConnect();


            //add each email to the DefaultListModel
            for (int x = 0 ; x < numOfEmails; x++){


                int y = x + 1;

                Message message = messages[messages.length - y];
                Address[] froms = message.getFrom();
                String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
                Object emailOb = email;

                dm.addElement(emailOb);

            }


        } catch (MessagingException ex) {
            System.out.println("Exception at getAddresses method:" + ex);
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


    public Message[] emailCheck() {

        Message newMessages[] = null;
        DefaultListModel newDm = new DefaultListModel();
        int oldFolderSize = folderSize;

        this.doConnect();

        if (oldFolderSize != folderSize){

            System.out.println("got new emails");

            int diff = folderSize - oldFolderSize;

            for (int x = 0; x <diff; x ++) {

                newMessages[x] = messages[x];

            }



        }



        try {
            System.out.println("Checking for emails");

                newMessages = folder.search(new FlagTerm(new Flags(Flags.Flag.RECENT), true));
                System.out.println("Your new messages are: " + newMessages);


        } catch (MessagingException e) {
            System.out.println("Error in checking for email");
            e.printStackTrace();
        }


        return newMessages;
    }







}
