package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javax.swing.*;

public class Main extends Application {

    public static int numOfEmails = 10;
    public static int emailId = 1;
    Controller.Tabs.GmailTab gmail = new Controller.Tabs.GmailTab();
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



        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);


    }
}

