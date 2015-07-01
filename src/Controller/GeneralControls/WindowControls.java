package Controller.GeneralControls;



import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by Seamy on 6/26/2015.
 */
public class WindowControls {


    public static Stage stage;

    public void openWindow(Parent fxmlId, String title) {


        try {

             stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle(title);
            stage.setScene(new Scene(fxmlId));
            stage.show();
        } catch (Exception ex) {


            System.out.print("Error opening window. Id:" + ex);
        }


    }


    public void closeWindow() {


    }
}