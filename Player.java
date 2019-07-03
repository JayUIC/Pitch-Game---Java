package sample;

import java.util.ArrayList;
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
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import java.util.ArrayList;

public class Player {
    private int playerID;
    private int numPoints;
    private int bidValue;
    private String bidName;
    private ArrayList<Card> currentHand;
    private ArrayList<Card> cardsWon;
    private Card cardPlayedOnRound;
    private ArrayList<Card> cardsPlayed;

    //GUI related variables
    Label scoreLabel;
    Label bidLabel;

    Player(int id){
        playerID = id;
        numPoints = 0;
        bidValue = -1;
        cardsWon = new ArrayList<>();
        currentHand = new ArrayList<>();
        cardsPlayed = new ArrayList<>();
    }
    int getPlayerID(){
        return playerID;
    }
    void setPlayerID(int id){
        playerID = id;
    }
    int getNumPoints(){
        return numPoints;
    }
    void setNumPoints(int points){
        numPoints = points;
    }

    ArrayList<Card> getCurrentHand(){
        return currentHand;
    }

    void setCurrentHand(ArrayList<Card> hand){
        currentHand = hand;
    }

    ArrayList<Card> getCardsWon(){
        return cardsWon;
    }

    void setCardsWon(ArrayList<Card> cards){
        cardsWon = cards;
    }

    int getBidValue(){
        return bidValue;
    }

    void setBidValue(int bid){
        bidValue = bid;
    }

    String getBidName(){
        return bidName;
    }
    void setBidName(String name){
        bidName = name;
    }

    Card getCardPlayedOnRound(){
        return cardPlayedOnRound;
    }
    void setCardPlayedOnRound(Card c){
        cardPlayedOnRound = c;
    }

    ArrayList<Card> getCardsPlayed(){
        return cardsPlayed;
    }
    void setCardsPlayed(ArrayList<Card> cards){
        cardsPlayed = cards;
    }

    void bid(Pitch Game){
        BidBox.bidAlert(this,Game);
    }

    int calculateGamePoints(){
        return 0;
    }

    public Card makeMove(Pitch Game){
        return null;
    }

    public void resetForNextDeal(){
        bidValue = -1;
        bidName = null;
        currentHand.clear();
        cardsWon.clear();

        cardPlayedOnRound = null;

    }
}
