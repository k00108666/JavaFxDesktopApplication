package application;

import Controller.GeneralControls.EmailControls;
import Controller.Tabs.GmailTab.GmailTab;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * Created by Seamy on 6/26/2015.
 */
public class Email {

    public static int emailId = 0;
    private   Parent root = null;
    public AnchorPane emailViewPane;

    EmailControls email = new EmailControls();
    Controller.GeneralControls.WindowControls windows = new Controller.GeneralControls.WindowControls();
    TextArea emailTxtArea;
    ToggleButton forwardTgl;
    ToggleButton replyTgl;
    Button sendBtn;
    String parsedEmail;
    TextField toTxtField;
    TextField fromTxtField;
    Label attachmentLabel;
    Label addAttach;
    public static String recipents;
    public static String  emailBody;
    public static String subject;




    public void EmailOpen(String fxmlId, String title) {

        try {
             root = FXMLLoader.load(getClass().getResource("/view/emailView.fxml"));


             forwardTgl = (ToggleButton) root.lookup("#forwardTgl");
             replyTgl = (ToggleButton) root.lookup("#replyTgl");






            sendBtn = (Button) root.lookup("#sendBtn");
             emailTxtArea = (TextArea) root.lookup("#emailText");
            fromTxtField = (TextField) root.lookup("#fromTxtField");
            toTxtField = (TextField) root.lookup("#toTextField");
            TextField subjectTxtField = (TextField) root.lookup("#subjectTxtField");
            attachmentLabel = (Label) root.lookup("#lblAttachment");
            addAttach = (Label) root.lookup("#lblAddAttach");


            subjectTxtField.setText(GmailTab.messages[Main.emailId].getSubject());
            fromTxtField.setText(Main.replyName);



            parsedEmail = email.parse(GmailTab.messages[Main.emailId]);
            emailTxtArea.appendText(parsedEmail);
            attachmentLabel.setText(email.attachName);


           replyTgl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event) {

                  setReplyTgl();


               }
           });


            forwardTgl.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    setForwardTgl();


                }
            });


            if (email.attachmentExists = true){
                attachmentLabel.setCursor(Cursor.HAND);
                attachmentLabel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent event) {
                      email.saveAttachment();
                    }
                });;
                }


            addAttach.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    email.addAttachment();
                }
            });


            sendBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {

                    String from = "Seamus O Connor";
                     recipents = toTxtField.getText();
                     subject = subjectTxtField.getText();
                     emailBody = emailTxtArea.getText();
                    String file_name = addAttach.getText();



                boolean answer = email.sendEmail();


                    if (answer = true){
                        clearWindow();
                   windows.closeWindow();}
                }
            });





            windows.openWindow(root, title);




        } catch (Exception ex) {

            System.out.println("Error opening Email. Error Id: " + ex);

        }


        doUpdate();

    }

    private void doUpdate() {
        String parsedEmail = email.parse(GmailTab.messages[Main.emailId]);
        emailTxtArea.appendText(parsedEmail);
    }





    public void setForwardTgl () {
            toTxtField.setDisable(false);
            toTxtField.setText("");
            sendBtn.setDisable(false);
            emailTxtArea.setText("FWD\nFrom: " + Main.replyName + "\n\n" + parsedEmail);
            fromTxtField.setDisable(true);
            addAttach.visibleProperty().setValue(true);

    }

    public void setReplyTgl () {
        toTxtField.setDisable(false);
        toTxtField.setText(fromTxtField.getText());
        fromTxtField.setDisable(true);
        sendBtn.setDisable(false);
        emailTxtArea.setText("\n\n\n\nRegarding\n: " + parsedEmail);
        attachmentLabel.visibleProperty().setValue(false);
        addAttach.visibleProperty().setValue(true);
    }


    public void clearWindow() {

        toTxtField.clear();
        fromTxtField.clear();
        sendBtn.setDisable(true);
        emailTxtArea.clear();
        fromTxtField.clear();



    }



}

