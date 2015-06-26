package Controller.Tabs;

/**
 * Created by Seamy on 6/25/2015.
 */



import java.util.*;
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
            //Connect to gmail, You can se your protocall here i.e Pop, Imap etc
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
           Message  messages [] = folder.getMessages();

        }catch (Exception ex){


            System.out.println("Error in connecting to Gmail");
        }

    }




    public  DefaultListModel getAddresses() {


        DefaultListModel dm = new DefaultListModel();



        try {
            //Connect and extract emails
            this.doConnect();
            messages = folder.getMessages();

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


    public Message getMessage () {

        int messageLocation = messages.length - emailId;
        return messages[messageLocation];




    }






}
