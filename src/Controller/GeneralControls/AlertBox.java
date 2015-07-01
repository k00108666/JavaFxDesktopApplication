package Controller.GeneralControls;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


/**
 * Created by Seamy on 6/9/2015.
 */
public class AlertBox {


    public static void display(String title, String message){

            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setMinWidth(200);

            Label label = new Label(message);

            Button button = new Button("Close the window");
            button.setOnAction(e -> window.close());
            VBox layout = new VBox();
            layout.getChildren().addAll(label, button);

            layout.setAlignment(Pos.CENTER);

            Scene scene = new Scene(layout);

            window.setScene(scene);
            window.showAndWait();









    }

}
