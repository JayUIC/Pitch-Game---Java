package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

public class WarningBox {

    public static void alert(String title, String message){

        Stage alertBox = new Stage();

        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle(title);
        alertBox.setMinWidth(250);
        alertBox.setMinHeight(250);

        Label displayMessage = new Label(message);
        Button okButton = new Button("Ok");
        okButton.setOnAction(event -> alertBox.close());

        VBox vbox = new VBox(15);
        vbox.getChildren().addAll(displayMessage,okButton);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox);
        alertBox.setScene(scene);
        alertBox.showAndWait();

    }
}
