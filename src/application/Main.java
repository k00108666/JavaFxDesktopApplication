package application;


import Controller.GeneralControls.AlertBox;
import Controller.Tabs.GmailTab;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.*;

public class Main extends Application {

    public static int numOfEmails = 10;
    public static int emailId = 1;
    public static String replyName = " ";
    public static String username = " ";
    public static String password = " ";
    Controller.Tabs.GmailTab gmail = new Controller.Tabs.GmailTab();
    application.Email email = new application.Email();


    public ListView GmailListView;
    Button GMailButton;
    Label GMailLabel;
    Label GMailLabelPass;
    PasswordField GMailPass;
    TextField GMailTxtField;




    @Override
    public void start(Stage primaryStage) throws Exception{

        //Load FXML and lookup the Email List
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        GmailListView = (ListView) root.lookup("#GmailListView");
        GMailButton = (Button)  root.lookup("#btnGMailLogin");
        GMailLabel = (Label) root.lookup("#lblUser");
        GMailLabelPass = (Label) root.lookup("#lblPass");
        GMailPass = (PasswordField) root.lookup("#passPassword");
        GMailTxtField = (TextField) root.lookup("#userTxtField");





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

                   //Add each email address to a new line in the list
                   for (int x = 0; x < dm.getSize(); x++) {

                       GmailListView.getItems().add(dm.get(x));
                   }

                    GMailButton.setVisible(false);
                    GMailLabel.setVisible(false);
                    GMailLabelPass.setVisible(false);
                    GMailPass.setVisible(false);
                    GMailTxtField.setVisible(false);





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

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);


    }
}


