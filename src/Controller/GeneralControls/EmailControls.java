package Controller.GeneralControls;

import Controller.Tabs.GmailTab.GmailTab;
import application.Email;
import application.Main;
import javafx.stage.FileChooser;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.search.FlagTerm;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;



/**
 * Created by Seamy on 6/30/2015.
 */
public class EmailControls {



        private boolean textIsHtml = false;
        public static int numOfEmails = 10;
        public boolean isAttachment = false;
        int emailId = Main.emailId;
        public boolean attachmentExists = false;
        public String attachName = "No attachments";
        MimeBodyPart attachment;
        Message message;
        public  Multipart multipart;

        String result;










    public  String parse (Message message) {

            String parsedEmail = " ";

            try {

                String contentType = message.getContentType();
                Object content = message.getContent();

                if (contentType.contains("multipart")) {
                    // this message may contain attachment

                    Multipart mp = (Multipart) content;

                    for (int i = 0; i < mp.getCount(); i++) {
                        BodyPart bp = mp.getBodyPart(i);
                        MimeBodyPart part = (MimeBodyPart) mp.getBodyPart(i);



                        if (Pattern
                                .compile(Pattern.quote("text/html"),
                                        Pattern.CASE_INSENSITIVE)
                                .matcher(bp.getContentType()).find()) {
                            // found html part

                            String html = (String) bp.getContent();
                            Document doc = Jsoup.parseBodyFragment(html);
                            Element body = doc.body();


                            String s = (String) body.text();
                            StringBuilder sb = new StringBuilder(s);

                            int x = 0;
                            while ((x = sb.indexOf(" ", x + 75)) != -1)
                                sb.replace(x, x + 1, "\n");

                            parsedEmail = sb.toString();


                        }


                       else if (bp.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
                            // this part is attachment
                            // code to save attachment...
                            attachmentExists = true;
                            attachment = part;

                            Multipart multipart2 = (Multipart) message.getContent();
                            BodyPart bp2 = multipart2.getBodyPart(1);

                            BodyPart bp1 = multipart2.getBodyPart(0);
                            Document doc = Jsoup.parseBodyFragment(bp1.getContent().toString());
                            Element bod = doc.body();
                            parsedEmail = bod.text();

                           DataHandler handler = bp2.getDataHandler();
                            attachName ="Attachment : " + handler.getName();





                        }

                    }
                }  else if (contentType.contains("text/plain")) {
                    // some other bodypart...
                    String s = (String) message.getContent();
                    StringBuilder sb = new StringBuilder(s);

                    int i = 0;
                    while ((i = sb.indexOf(" ", i + 30)) != -1)
                        sb.replace(i, i + 1, "\n");

                    parsedEmail = sb.toString();

                }


            }


            catch (Exception ex) {
                System.out.println("Error parsing Email. Error Id:"  + ex);

            }

            System.out.println("here is the message: " + parsedEmail);
            System.out.println("Message received");

            return parsedEmail;
        }


    public void saveAttachment()  {

        //Create new File Chooser
        FileChooser fileChooser = new FileChooser();

        try {
            //Set the default save name to the name of the attachment
            fileChooser.setInitialFileName(attachment.getFileName());

            //Add extensions to File Chooser
            fileChooser.getExtensionFilters().addAll(

                    new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("All Files", "*.*")

            );


        } catch (MessagingException e) {
            e.printStackTrace();
        }


        //Declare a file name as whatever is chosen in the FileChooser
        File file = fileChooser.showSaveDialog(WindowControls.stage);

        //Get the address of the chosen file for saving.
        String address = file.getPath();
        System.out.println("address = " + address);


        //if a file is chosen
        if (file != null){
            try {
                //save the file to the address
                attachment.saveFile(address + fileChooser.toString());
                System.out.println("Saved");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (MessagingException e) {
                System.out.println("Could not download attachment");
                e.printStackTrace();
            }

        }




    }

    public void addAttachment() {
    }

    public static boolean  sendEmail() {

        Boolean answer = false;

        String emailBody = " ";
            String file_name = " ";

            final String username = Main.username;
            final String password = Main.password;

        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        // Get a Properties object
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");



            try {

                Session session = Session.getInstance(props,
                        new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });

                // -- Create a new message --
                Message msg = new MimeMessage(session);

                // -- Set the FROM and TO fields --
                msg.setFrom(new InternetAddress("seamo123@gmail.com"));
                msg.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(Email.recipents, false));
                msg.setSubject(Email.subject);
                msg.setText(Email.emailBody);
                msg.setSentDate(new Date());
                Transport.send(msg);
                System.out.println("Message sent.");



                answer = true;

                    AlertBox.display("Message", "Your message has been sent!");


            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }


        return answer;
    }

    private static void addAttachment(Multipart multipart, String filename)
    {
        try {

            DataSource source = new FileDataSource(filename);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(filename);
            multipart.addBodyPart(messageBodyPart);




        } catch (Exception ex){



        }


    }




}






