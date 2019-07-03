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


public class DealResults {
    public static void dealresulst(Pitch Game, Player highWinner, Player lowWinner, Player jackWinner, Player gameWinner){
        Stage alertBox = new Stage();

        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("End of Round Results");
        alertBox.setMinWidth(250);
        alertBox.setMinHeight(250);

        VBox vbox = new VBox(20);

        Label highLabel = new Label();
        Label lowLabel = new Label();
        Label jackLabel = new Label();
        Label gameLabel = new Label();

        if (highWinner == Game.getPlayerList().get(0)){
            highLabel.setText("High Point Winner: Human Player(You)");
        }
        else{
            highLabel.setText("High Point Winner: Computer Player " + highWinner.getPlayerID());
        }

        if (lowWinner == Game.getPlayerList().get(0)){
            lowLabel.setText("Low Point Winner: Human Player(You)");
        }
        else{
            lowLabel.setText("Low Point Winner: Computer Player " + lowWinner.getPlayerID());
        }

        if (jackWinner == Game.getPlayerList().get(0)){
            jackLabel.setText("Jack Point Winner: Human Player(You)");
        }
        else{
            if (jackWinner == null){
                jackLabel.setText("Jack Point Winner: No Winner");
            }
            else{
                jackLabel.setText("Jack Point Winner: Computer Player " + jackWinner.getPlayerID());
            }
        }

        if (gameWinner == Game.getPlayerList().get(0)){
            gameLabel.setText("Game Point Winner: Human Player(You)");
        }
        else{
            if (gameWinner == null){
                gameLabel.setText("Game Point Winner: No Winner");
            }
            else{
                gameLabel.setText("Game Point Winner: Computer Player " + gameWinner.getPlayerID());
            }
        }
        Button okButton = new Button("Next Hand");
        okButton.setOnAction(event -> alertBox.close());
        vbox.getChildren().addAll(highLabel,lowLabel,jackLabel,gameLabel,okButton);


        Scene scene = new Scene(vbox);
        alertBox.setScene(scene);
        alertBox.showAndWait();

    }
}
