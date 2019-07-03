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
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Card {
    private String suite;
    private int value;

    Image cardImage;
    ImageView iv;

    Card(String suiteType, int cardValue){
        suite = suiteType;
        value = cardValue;;
        cardImage = new Image("/sample/JPEG/" + cardValue + suiteType + ".jpg");
        iv = new ImageView(cardImage);
        iv.setFitWidth(75);
        iv.setFitHeight(150);
        iv.setPreserveRatio(true);
    }
    Card(Card c){
        suite = c.getSuite();
        value = c.getValue();

        this.cardImage = c.cardImage;
        this.iv = new ImageView(cardImage);
        iv.setFitWidth(75);
        iv.setFitHeight(150);
        iv.setPreserveRatio(true);
    }

    String getSuite(){
        return suite;
    }

    int getValue(){
        return value;
    }

    void setSuite(String type){
        suite = type;
    }
    void setValue(int val){
        value = val;
    }
}
