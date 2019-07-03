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


public class GameResults {
    public static void alertBox(Player p, Pitch Game){
        Stage alertBox = new Stage();

        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle("Game Results");
        alertBox.setMinWidth(250);
        alertBox.setMinHeight(250);

        Label displayMessage = new Label();
        displayMessage.setAlignment(Pos.CENTER);
        displayMessage.relocate(100,100);

        if (p.getPlayerID() == 0){
            displayMessage.setText("Congrats! You won the game with: " + p.getNumPoints() + " points.");
        }
        else{
            displayMessage.setText("Computer Player " + p.getPlayerID() + " won with " + p.getNumPoints() + " points.");
        }

        Button startOver = new Button("Start New Game");
        startOver.setOnAction(event->{
            Game.startNewGame();
            alertBox.close();
        });
        startOver.relocate(25,175);
        startOver.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        Button exit = new Button("Exit");
        exit.setOnAction(event ->{
            System.exit(0);
        });
        exit.relocate(200,175);
        exit.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));

        Pane mainPane = new Pane();
        mainPane.getChildren().addAll(displayMessage,startOver,exit);
        Scene scene = new Scene(mainPane);

        alertBox.setOnCloseRequest(event -> {
            System.exit(0);
        });
        alertBox.setScene(scene);
        alertBox.showAndWait();

    }
}
