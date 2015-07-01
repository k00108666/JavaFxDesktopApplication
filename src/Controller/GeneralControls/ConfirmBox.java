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
public class ConfirmBox {

    static boolean answer;
     static Stage window;


    public static boolean  display(String title, String message){

        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);
        window.setMaxHeight(200);
        window.setOnCloseRequest(e -> closeProgram());

        Label label = new Label(message);

        Button buttonYes = new Button("Yes");
        buttonYes.setOnAction(e -> closeProgram());

        Button buttonNo = new Button("No");
        buttonNo.setOnAction(e -> {


                    answer = false;
                    window.close();


        });

        VBox layout = new VBox();
        layout.getChildren().addAll(label, buttonNo, buttonYes);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);

        window.setScene(scene);
        window.showAndWait();


        return answer;
    }

    private static void closeProgram() {


System.out.println("File is saved");
        window.close();

    }


}
