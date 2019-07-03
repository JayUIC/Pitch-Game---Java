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

public class BidBox {
    public static void bidAlert(Player p, Pitch Game){
        Stage bidStage = new Stage();
        VBox vbox = new VBox();
        Scene scene = new Scene(vbox,250,250);
        bidStage.setScene(scene);


        bidStage.initModality(Modality.APPLICATION_MODAL);
        bidStage.setTitle("Bid");
        bidStage.setOnCloseRequest(event ->{
            System.exit(0);
        });

        MenuButton bidSelect = new MenuButton("Please select a bid");
        RadioMenuItem pass = new RadioMenuItem("Pass");
        RadioMenuItem two = new RadioMenuItem("2");
        RadioMenuItem three = new RadioMenuItem("3");
        RadioMenuItem four = new RadioMenuItem("4");
        RadioMenuItem smudge = new RadioMenuItem("Smudge");

        bidSelect.getItems().addAll(pass,two,three,four,smudge);

        pass.setOnAction(e-> {
            p.setBidName("Pass");
            p.setBidValue(0);
            bidStage.close();
        });

        two.setOnAction(e-> {
            p.setBidName("2");
            p.setBidValue(2);
            bidStage.close();
        });
        three.setOnAction(e-> {
            p.setBidName("3");
            p.setBidValue(3);
            bidStage.close();
        });
        four.setOnAction(e-> {
            p.setBidName("4");
            p.setBidValue(4);
            bidStage.close();
        });
        smudge.setOnAction(e-> {
            p.setBidName("Smudge");
            p.setBidValue(5);
            bidStage.close();
        });

        vbox.getChildren().add(bidSelect);
        vbox.setAlignment(Pos.CENTER);


        bidStage.showAndWait();
    }
}
