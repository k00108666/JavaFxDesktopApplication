package application;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class Main extends Application {

    public static int numOfEmails = 10;
    public static int emailId = 1;
    Controller.Tabs.GmailTab gmail = new Controller.Tabs.GmailTab();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();




        gmail.doConnect();


        DefaultListModel dm = gmail.getAddresses();   //<<<<<Crashing here


       javafx.scene.control.ListView GmailListView = new javafx.scene.control.ListView();

       //GmailListView.getItems().addAll(dm);





    }


    public static void main(String[] args) {
        launch(args);







    }
}


