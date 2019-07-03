//Author: Jay Nimkar
//NetID: Jnimka2
//CS 342 Project #2

package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

    private Pitch Game = new Pitch();

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Pitch");
        primaryStage.setResizable(false);
        primaryStage.show();

        Pane background = new Pane();
        background.setStyle("-fx-background-color: transparent;");

        Scene scene = new Scene(background,750,750);
        scene.setFill(Color.WHITE);
        primaryStage.setScene(scene);


        Image logo = new Image("sample/back_cards-07.jpg");
        ImageView iv = new ImageView();
        iv.setImage(logo);
        iv.setPreserveRatio(true);
        iv.setFitHeight(750);
        iv.setFitWidth(750);

        background.getChildren().add(iv);

        //Text label that displays Welcome at the top
        Label welcomeLabel = new Label("Welcome to the game Pitch!");
        welcomeLabel.setFont(Font.font(25));


        //Drop down menu for selecting players
        MenuButton playerSelect = new MenuButton("Select Number of Players");
        MenuItem players2 = new MenuItem("2");
        MenuItem players3 = new MenuItem("3");
        MenuItem players4 = new MenuItem("4");



        players2.setOnAction(event ->{
            playerSelect.setText("2 Player Game");
            Game.setNumPlayers(2);
        });

        players3.setOnAction(event -> {
           playerSelect.setText("3 Player Game");
           Game.setNumPlayers(3);
        });

        players4.setOnAction(event -> {
           playerSelect.setText("4 Player Game");
           Game.setNumPlayers(4);
        });

        playerSelect.getItems().addAll(players2,players3,players4);


        //Button for the player to start the game
        Button startGame = new Button("Start Game!");
        startGame.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        startGame.setOnAction(event ->{
           if (Game.getNumPlayers() == 0){
               WarningBox.alert("Error","Please Select the number of players");
           }
           else{
               primaryStage.close();
               Game.startGame();
           }
        });
        VBox vb = new VBox(100);;
        vb.getChildren().addAll(welcomeLabel,playerSelect,startGame);
        vb.setAlignment(Pos.CENTER);
        vb.relocate(200,350);


        //Button for the player to exit the game
        StackPane sp = new StackPane();

        Button endGame = new Button("Exit Game");
        endGame.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        endGame.setOnAction(event -> primaryStage.close());
        sp.getChildren().add(endGame);
        sp.relocate(650,700);


        background.getChildren().addAll(vb,sp);
    }


    public static void main(String[] args) {
        launch(args);
    }


}
