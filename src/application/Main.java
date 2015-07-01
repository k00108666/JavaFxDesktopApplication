package application;


import Controller.Tabs.GmailTab;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.*;

public class Main extends Application {

    public static int numOfEmails = 10;
    public static int emailId = 1;
    public static String replyName = " ";
    Controller.Tabs.GmailTab gmail = new Controller.Tabs.GmailTab();
    application.Email email = new application.Email();


    public ListView GmailListView;




    @Override
    public void start(Stage primaryStage) throws Exception{

        //Load FXML and lookup the Email List
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        GmailListView = (ListView) root.lookup("#GmailListView");

        //Connect to gmail and get email addresses
        gmail.doConnect();
        DefaultListModel dm = gmail.getAddresses();

        //Add each email address to a new line in the list
        for (int x = 0; x < dm.getSize(); x++){

            GmailListView.getItems().add(dm.get(x));
        }

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


