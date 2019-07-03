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

public class TrickResults {
    public static void trickResults(Player p, Pitch Game){
        Stage alertBox = new Stage();
        alertBox.setX(Game.mainStage.getX());
        alertBox.setY(Game.mainStage.getY());
        Label winnerLabel = new Label();

        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("Trick Results");
        alertBox.setMinWidth(250);
        alertBox.setMinHeight(250);

        VBox vbox = new VBox(15);

        int i = 0;
        for (Player gamePlayer : Game.getPlayerList()){
            if (gamePlayer == p){
                if (p == Game.getPlayerList().get(0)){
                    winnerLabel.setText("You won the trick! On to the next one . . .");
                }
                else{
                    winnerLabel.setText("Computer Player " + i +  " won the trick. On to the next one . . .");
                }
                break;
            }
            i++;
        }

        Button okButton = new Button("Ok");
        okButton.setOnAction(event -> alertBox.close());
        vbox.getChildren().addAll(winnerLabel,okButton);


        Scene scene = new Scene(vbox);
        alertBox.setScene(scene);
        alertBox.showAndWait();
    }
}
