package Controller.WebViewControls;

import application.Main;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.lang.model.AnnotatedConstruct;
import java.io.IOException;

/**
 * Created by Seamy on 7/19/2015.
 */
public class WebViewControls {


   private static TabPane tabPane;
   private static AnchorPane webViewPane;
   private static Tab homePageTab;
   private static Tab newPageTab;









    public void loadPage(String webPage) {



        tabPane = Main.webViewTab;

        homePageTab = new Tab("Homepage");
        newPageTab =  new Tab("New Page");
       tabPane.getTabs().addAll(homePageTab, newPageTab);

        WebView browser = new WebView();
        homePageTab.setContent(browser);

        WebEngine webEngine = browser.getEngine();

        webEngine.load(webPage);

        newPageTab.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                if (newPageTab.isSelected()) {

                    newTab();


                }
            }
        });





    }

    public void loadNewPage() {



    }


    public void newTab () {

        Tab newHomePageTab = new Tab("Homepage");
        newHomePageTab.setClosable(true);
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

        tabPane.getTabs().addAll(newHomePageTab);




        selectionModel.select(newHomePageTab);

        try {
            WebView browser = new WebView();
            newHomePageTab.setContent(browser);

            WebEngine webEngine = browser.getEngine();

            webEngine.load(Main.homePage);
        } catch (Exception ex) {

            Controller.GeneralControls.AlertBox.display("Could not display webpage", "Please make sure it is a valid webpage and your internet connection is working");

        }



    }




}
