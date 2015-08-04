package application;


import Controller.GeneralControls.AlertBox;
import Controller.Tabs.GmailTab.GChatTab;
import Controller.Tabs.GmailTab.GmailTab;
import Controller.WebViewControls.WebViewControls;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Main extends Application {

    public static int numOfEmails = 10;
    public static int emailId = 1;
    public static String replyName = " ";
    public static String username = " ";
    public static String password = " ";
    GmailTab gmail = new GmailTab();
    application.Email email = new application.Email();



    public ListView GmailListView;
    public static ComboBox buddyList;
    Button GMailButton;
    Label GMailLabel;
    Label GMailLabelPass;
    PasswordField GMailPass;
    TextField GMailTxtField;
    TextField TxtEmailNum;
    Label LblEmailNum;
    public static TextArea GchatText;
    TextArea chatArea;
    TextArea inputArea;
    TextField webAddress;
    public static TabPane webViewTab;
    public static  int currentChat;
    public static int gChatrecipient;
    GChatTab gchat = new GChatTab();
    WebViewControls webview = new WebViewControls();
    Boolean connected = false;
    public  static Parent root;
    public static String homePage = "http://www.google.com";




    @Override
    public void start(Stage primaryStage) throws Exception{

        //Load FXML and lookup the Email List

        root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        GmailListView = (ListView) root.lookup("#GmailListView");
        GMailButton = (Button)  root.lookup("#btnGMailLogin");
        GMailLabel = (Label) root.lookup("#lblUser");
        GMailLabelPass = (Label) root.lookup("#lblPass");
        GMailPass = (PasswordField) root.lookup("#passPassword");
        GMailTxtField = (TextField) root.lookup("#userTxtField");
        TxtEmailNum = (TextField)  root.lookup("#txtEmailNum");
        LblEmailNum = (Label) root.lookup("#lblEmailNum");
        GchatText = (TextArea) root.lookup("#txtAreaGchat");
        webViewTab = (TabPane) root.lookup("#webTabPane");


        buddyList = (ComboBox) root.lookup("#cmbGchat");
        inputArea = (TextArea) root.lookup("#textAreaGchatInput");
        chatArea = (TextArea) root.lookup("#txtAreaGchat");
        webAddress = (TextField) root.lookup("#txtWebAdress");




        webview.loadPage(homePage);





        TxtEmailNum.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    GmailTab.numOfEmails = Integer.parseInt(TxtEmailNum.getText());
                } catch (Exception ex) {

                    AlertBox.display("Error", "Value must be an Integer");
                    TxtEmailNum.setText("10");

                }

            }
        });

        webAddress.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getCode() == KeyCode.ENTER && connected == true) {



                }

            }
        });



        GMailButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                try {
                    username = GMailTxtField.getText();
                    password = GMailPass.getText();


                    //Connect to gmail and get email addresses
                    gmail.doConnect();
                    DefaultListModel dm = gmail.getAddresses();

                    GmailListView.setDisable(false);

                    gchat.connectTOGTalk();

                    //gchat.displayBuddyList();

                    //Add each email address to a new line in the list
                    for (int x = 0; x < dm.getSize(); x++) {

                        GmailListView.getItems().add(dm.get(x));

                    }

                    connected = true;

                    GMailButton.setVisible(false);
                    GMailLabel.setVisible(false);
                    GMailLabelPass.setVisible(false);
                    GMailPass.setVisible(false);
                    GMailTxtField.setVisible(false);
                    LblEmailNum.setVisible(false);
                    TxtEmailNum.setVisible(false);
                    inputArea.setEditable(true);

                    ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
                    checkForMail check = new checkForMail();


                    exec.scheduleAtFixedRate(check.check, 10, 10, TimeUnit.SECONDS);




                } catch (Exception ex) {


                    AlertBox.display("Could not connect to GMail", "Possible reasons: 1. Incorrect Email 2. Incorrect Password 3.No internet Connection 4. IMap or SMTP settings not enabled on GMail etc");


                }

            }
        });


        GmailListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                emailId = GmailTab.messages.length - (GmailListView.getSelectionModel().getSelectedIndex() + 1);
                replyName = GmailListView.getSelectionModel().getSelectedItems().toString();
                replyName = replyName.substring(1, replyName.length()-1);
                System.out.println(replyName);

               email.EmailOpen("/view/emailView.fxml", "View email");


            }
        });



        buddyList.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                getUserId();
            }
        });



        inputArea.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(final KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.ENTER && connected == true) {


                    if (inputArea.getText() != null) {
                        try {
                            chatArea.appendText(username + ":\n" + inputArea.getText());
                            gchat.sendMessage(inputArea.getText(), gChatrecipient);
                            inputArea.setText("");

                        } catch (Exception e) {
                            AlertBox alert = new AlertBox();
                            alert.display("Error sending message", "Please make sure there is a message to send");
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();



        primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());




        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }


    public static void  getUserId(){


        currentChat =  buddyList.getSelectionModel().getSelectedIndex();
        gChatrecipient = currentChat;




    }





    public  class checkForMail  {




        final Runnable check = new Runnable() {
            public void run()  {


                DefaultListModel dm = gmail.getAddresses();
                GmailListView.getItems().clear();



                for (int x = 0; x < dm.getSize(); x++) {

                    GmailListView.getItems().add(dm.get(x));

                   }



                }


    };
    }



    public static void main(String[] args) {
        launch(args);



    }
}


