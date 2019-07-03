package sample;
import javafx.application.Application;
import javafx.application.Platform;
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

import java.util.ArrayList;

import static javafx.application.Application.launch;

public class Pitch implements DealerType {
    public Stage mainStage = new Stage();
    public Pane mainPane= new Pane();

    public Scene scene = new Scene(mainPane,750,750);
    VBox scoreboard = new VBox(15);
    HBox hand = new HBox(50);
    VBox bidBoard = new VBox(15);
    HBox mainGameArea = new HBox();
    VBox trumpDisplay = new VBox(10);
    VBox gameConsole = new VBox();
    Label consoleMessage = new Label();

    private int numPlayers;
    private Card gameTrump;
    private Card roundLead;
    private Player currentTurn;
    private ArrayList<Player> playerList;
    private Dealer mainDealer;
    private ArrayList<Card> roundCards;
    private ArrayList<Player> currentRound;
    private boolean trumpFound;
    private ArrayList<Card> cardsAlreadyPlayed;
    private ArrayList<Player> gameTrickWinners;

    private Player dealHighWinner;
    private Player dealLowWinner;
    private Player dealGameWinner;
    private Player dealJackWinner;
    private Player finalGameWinner;

    Pitch() {
        numPlayers = 0;
        playerList = new ArrayList<>();
        mainDealer = createDealer();
        currentTurn = null;
        gameTrump = null;
        roundCards = new ArrayList<>();
        trumpFound = false;
        cardsAlreadyPlayed = new ArrayList<>();
        gameTrickWinners = new ArrayList<>();

        dealJackWinner = null;
        dealHighWinner = null;
        dealLowWinner = null;
        dealGameWinner = null;
        finalGameWinner = null;
    }
    int getNumPlayers(){
        return numPlayers;
    }

    void setNumPlayers(int num){
        numPlayers = num;
    }


    Card getGameTrump(){
        return gameTrump;
    }

    void setGameTrump(Card trump){
        gameTrump = new Card(trump);
    }

    Player getCurrentTurn(){
        return currentTurn;
    }

    void setCurrentTurn(Player p){
        currentTurn = p;
    }

    ArrayList<Player> getPlayerList(){
        return playerList;
    }

    ArrayList<Card> getRoundCards(){
        return roundCards;
    }
    void setRoundCards(ArrayList<Card> cards){
        roundCards = cards;
    }
    Boolean getTrumpFound(){
        return trumpFound;
    }
    void setTrumpFound(boolean found){
        trumpFound = found;
    }
    ArrayList<Card> getCardsAlreadyPlayed(){
        return cardsAlreadyPlayed;
    }
    void setCardsAlreadyPlayed(ArrayList<Card> cards){
        cardsAlreadyPlayed = cards;
    }

    Card getRoundLead(){
        return roundLead;
    }
    void setRoundLead(Card c){
        roundLead = c;
    }

    @Override
    public Dealer createDealer() {
        return new Pitch_Dealer();
    }

    void startGame(){
        mainPane.setStyle("-fx-background-color: transparent;");
        mainStage.setTitle("Pitch");
        mainStage.setScene(scene);
        mainStage.setResizable(false);
        mainStage.show();

        scene.setFill(Color.GREEN);
        mainGameArea.setStyle("-fx-background-color: #0c681d");
        mainGameArea.relocate(50,250);
        mainGameArea.setMinSize(650,175);
        mainGameArea.setSpacing((650 - (75 * numPlayers))/ (numPlayers * 1.0));
        mainGameArea.setAlignment(Pos.CENTER_LEFT);
        mainPane.getChildren().add(mainGameArea);


        initPlayerList();

        createGameConsoleGUI();

        createScoreBoardGUI();

        createExitButton();

        createStartOverButton();

        requestDealerToDealCards();

        dealCardsGUI();

        requestBidsFromPlayers();

        displayBids();

        newRound();

    }


    void awardPoints(Player p, int numPoints){
        p.setNumPoints(p.getNumPoints() + numPoints);
    }
    void awardCards(Player p, ArrayList<Card> cards){
        for (Card c : cards){
            p.getCardsWon().add(c);
        }
    }


    void requestBidsFromPlayers(){
        int max = 0;
        consoleMessage.setText("Waiting for Bid . . .");
        for (Player p : playerList){
            p.bid(this);
            if (p.getBidValue() > max){
                max = p.getBidValue();
                currentTurn = p;
            }
        }

    }

    void initPlayerList(){
        playerList.add(new Player(0));
        for (int i = 1; i < numPlayers; i++){
            playerList.add(new AIPlayer(i));
        }
    }

    void createGameConsoleGUI(){
        Label console = new Label("Game Console");

        consoleMessage.setFont(Font.font("Arial", FontWeight.BOLD,18));
        consoleMessage.setTextFill(Color.LIGHTGREEN);

        console.relocate(100,475);
        console.setFont(Font.font("Arial", FontWeight.BOLD,16));

        gameConsole.relocate(225,450);
        gameConsole.setMinHeight(75);
        gameConsole.setMinWidth(300);
        gameConsole.setAlignment(Pos.CENTER);

        gameConsole.setStyle("-fx-background-color: #000000");
        gameConsole.getChildren().add(consoleMessage);
        mainPane.getChildren().addAll(gameConsole,console);
    }

    void requestDealerToDealCards(){
        for (int i = 0; i < numPlayers; i++){
            (playerList.get(i)).setCurrentHand(mainDealer.dealHand());
        }
    }

    void createScoreBoardGUI(){
        playerList.get(0).scoreLabel = new Label("Your Score: " + playerList.get(0).getNumPoints());
        scoreboard.getChildren().add(playerList.get(0).scoreLabel);
        playerList.get(0).scoreLabel.setFont(Font.font("Arial",FontWeight.BOLD, 14));

        for (int i = 1; i < numPlayers; i++){
            playerList.get(i).scoreLabel = new Label("Computer Player " + i + "'s Score: " + playerList.get(i).getNumPoints());
            playerList.get(i).scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
            scoreboard.getChildren().add(playerList.get(i).scoreLabel);
        }
        mainPane.getChildren().add(scoreboard);
    }

    void dealCardsGUI(){
        hand.relocate(25,550);
        for (Card c : playerList.get(0).getCurrentHand()){
            hand.getChildren().add(c.iv);
        }
        Label handLabel = new Label("Your Current Hand");
        handLabel.setFont(Font.font("Arial",FontWeight.BOLD,14));
        handLabel.relocate(325,700);
        mainPane.getChildren().addAll(hand,handLabel);
    }

    void displayBids(){
        playerList.get(0).bidLabel = new Label("Your Bid is: " + playerList.get(0).getBidName());
        bidBoard.getChildren().add(playerList.get(0).bidLabel);
        playerList.get(0).bidLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));

        for (int i = 1; i < numPlayers; i++){
            playerList.get(i).bidLabel = new Label("Computer Player " + i + "'s bid is: " + playerList.get(i).getBidName());
            playerList.get(i).bidLabel.setFont(Font.font("Arial", FontWeight.BOLD,14));
            bidBoard.getChildren().add(playerList.get(i).bidLabel);
        }
        bidBoard.relocate(500,0);
        mainPane.getChildren().add(bidBoard);

        boolean bidMade = false;
        for (Player p : playerList){
            if (p.getBidValue() != 0){
                bidMade = true;
            }
        }
        if (bidMade == false){
            WarningBox.alert("No bid found", "All players made no bid. Restarting Round . . .");
            newDeal(false);
        }

    }

    void newDeal(boolean newGame){

        resetDataMembersForNewDeal(newGame);

        clearGameBoard();

        requestDealerToDealCards();

        dealCardsGUI();

        requestBidsFromPlayers();

        displayBids();

        newRound();

    }
    void resetDataMembersForNewDeal(boolean newGame){
        if (newGame == true){
            cardsAlreadyPlayed.clear();
            resetScores();
        }
        dealHighWinner = null;
        dealLowWinner = null;
        dealJackWinner = null;
        dealGameWinner = null;

        if (mainDealer.getDeckSize() < (numPlayers * 6)){
            mainDealer.resetDeck();
            cardsAlreadyPlayed.clear();
        }

        for (Player p : playerList){
            p.resetForNextDeal();
        }
        if (roundCards.size() != 0){
            roundCards.clear();
        }
        trumpFound = false;

    }

    void clearGameBoard(){
        hand.getChildren().clear();
        bidBoard.getChildren().clear();
        mainGameArea.getChildren().clear();
        trumpDisplay.getChildren().clear();

        mainPane.getChildren().removeAll(hand,bidBoard,trumpDisplay);
    }
    void createExitButton(){
        Button exit = new Button("Exit");
        exit.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        exit.setOnAction(event -> {
            mainStage.close();
        });

        exit.maxWidth(75);
        exit.maxHeight(100);
        exit.relocate(690,700);
        mainPane.getChildren().add(exit);

    }
    void createStartOverButton(){
        Button startOver = new Button("Restart Game");
        startOver.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY, Insets.EMPTY)));
        startOver.setOnAction(event -> {
            startNewGame();
        });
        startOver.maxWidth(75);
        startOver.maxHeight(100);
        startOver.relocate(40, 700);
        mainPane.getChildren().add(startOver);

    }

    void startNewGame(){
        scoreboard.getChildren().clear();
        mainPane.getChildren().remove(scoreboard);
        currentTurn = null;


        for (Player p : playerList){
            p.setNumPoints(0);
        }
        WarningBox.alert("New Game","Starting New Game . . .");
        createScoreBoardGUI();
        newDeal(true);
    }

    void newRound(){
        currentRound = new ArrayList<>(playerList);

        resetDataMembersForNewRound();
        resetGUIForNewRound();

        if (currentTurn == playerList.get(0)){
            haveUserSelectCard();
            return;
        }

        roundCards.add(currentTurn.makeMove(this));
        currentRound.remove(currentTurn);

        ArrayList<Player> temp = new ArrayList<>(currentRound);

        for (Player p : temp){
            if (p == playerList.get(0)){
                haveUserSelectCard();
                return;
            }
            roundCards.add(p.makeMove(this));
            currentRound.remove(p);
        }
    }
    void resetDataMembersForNewRound(){
        roundLead = null;
        roundCards.clear();

        for (Player p : playerList){
            p.setCardPlayedOnRound(null);
        }
    }
    void resetGUIForNewRound(){
        mainGameArea.getChildren().clear();
    }
    void haveUserSelectCard(){
        makeUserHandSelectable();
        currentRound.remove(playerList.get(0));
        consoleMessage.setText("Your Turn. Select a Card to play.");
        return;
    }

    void makeUserHandSelectable(){
        for (Card c : playerList.get(0).getCurrentHand()){
            c.iv.setOnMouseClicked(e-> {
                if (c != gameTrump){
                    hand.getChildren().remove(c.iv);
                    mainGameArea.getChildren().add(c.iv);
                    playerList.get(0).getCurrentHand().remove(c);
                    cardsAlreadyPlayed.add(c);
                    playerList.get(0).setCardPlayedOnRound(c);
                    playerList.get(0).getCardsPlayed().add(c);
                    roundCards.add(c);
                    if (trumpFound == false){
                        trumpFound = true;
                        gameTrump = new Card(c);
                        roundLead = c;
                        displayTrumpCard();
                    }
                    removeUserHandSelect();
                    handleRemainingAIMoves();
                }

            });

        }
    }

    void removeUserHandSelect(){
        consoleMessage.setText("");
        for (Card c : playerList.get(0).getCurrentHand()){
            c.iv.setOnMouseClicked(e-> {

            });
        }
    }
    void handleRemainingAIMoves(){
        for (Player p : currentRound){
            if (p.getCurrentHand().size() != 0){
                roundCards.add(p.makeMove(this));
            }
        }
        endRound();
    }
    void addCardToStage(Card c){
        mainGameArea.getChildren().add(c.iv);
    }
    void endRound(){
        findTrickWinner();
        displayTrickResults();
        if (dealFinished() == true){
            calculatePointsForPlayers();
            displayDealResults();
            newDeal(false);
        }
        else{
            newRound();
        }
    }
    void displayTrickResults(){
        TrickResults.trickResults(gameTrickWinners.get(gameTrickWinners.size() - 1),this);
    }
    void displayDealResults(){
        DealResults.dealresulst(this,dealHighWinner,dealLowWinner,dealJackWinner,dealGameWinner);
    }

    void findTrickWinner(){

        Card cardLead = roundCards.get(0);

        Player trickWinner = null;
        boolean trumpFound = false;
        int trumpMax = 0;
        int roundMax = 0;

        for (Player p: playerList){
            if (cardLead == gameTrump){
                if (p.getCardPlayedOnRound().getSuite() == cardLead.getSuite()){
                    if (p.getCardPlayedOnRound().getValue() == 1|| p.getCardPlayedOnRound().getValue() >= cardLead.getValue()){
                        trickWinner = p;
                    }
                }
            }
            else{
                if (p.getCardPlayedOnRound().getSuite() == gameTrump.getSuite()){
                    if (trumpFound == false){
                        trickWinner = p;
                        trumpMax = p.getCardPlayedOnRound().getValue();
                        trumpFound = true;
                    }
                    else if (p.getCardPlayedOnRound().getValue() == 1 || (p.getCardPlayedOnRound().getValue() > trumpMax && trumpMax != 1)){
                        trickWinner = p;
                        trumpMax = p.getCardPlayedOnRound().getValue();
                    }
                }
                else if (trumpFound == false){
                    if (p.getCardPlayedOnRound().getSuite() == cardLead.getSuite()){
                        if (p.getCardPlayedOnRound().getValue() == 1 || p.getCardPlayedOnRound().getValue() > roundMax){
                            trickWinner = p;
                            roundMax = p.getCardPlayedOnRound().getValue();
                        }
                    }
                }
            }
        }

        gameTrickWinners.add(trickWinner);
        currentTurn = trickWinner;
        awardCards(trickWinner,roundCards);
    }
    boolean dealFinished(){
        for (Player p : playerList){
            if (p.getCurrentHand().size() != 0){
                return false;
            }
        }
        return true;
    }
    void displayTrumpCard(){
        Label trumpLabel = new Label("Trump Card: " + returnVal(gameTrump.getValue()) + " of " + returnSuite(gameTrump));
        trumpLabel.setFont(Font.font("Arial",FontWeight.BOLD, 14));

        trumpDisplay.getChildren().addAll(gameTrump.iv,trumpLabel);
        trumpDisplay.setAlignment(Pos.TOP_CENTER);
        trumpDisplay.relocate(300,50);

        mainPane.getChildren().addAll(trumpDisplay);
    }
    String returnSuite(Card c){
        if (c.getSuite() == "H"){
            return "Hearts";
        }
        if (c.getSuite() == "D"){
            return "Diamonds";
        }
        if (c.getSuite() == "C"){
            return "Clubs";
        }
        return "Spades";
    }
    String returnVal(int val){
        if (val == 1){
            return "Ace";
        }
        else if (val == 11){
            return "Jack";
        }
        else if (val == 12){
            return "Queen";
        }
        else if (val == 13){
            return "King";
        }
        else{
            return String.valueOf(val);
        }
    }

    void calculatePointsForPlayers(){

        findGamePointWinner();

        findHighAndLowPointWinner();

        findJackPointWinner();

        evaluatePointsAndBids();

        if(gameFinished()){
            endGame();
        }
    }
    void findGamePointWinner(){
        int maxGamePoints = 0;

        int gamePoints = 0;

        for (Player p : playerList){
            gamePoints = calculateGamePointsFromCardsWon(p);
            if (gamePoints > maxGamePoints){
                maxGamePoints = gamePoints;
                dealGameWinner = p;
            }
        }


    }
    void findHighAndLowPointWinner(){
        int maxTrump = 0;
        int minTrump = 14;

        for (Player p : playerList){
            for (Card c: p.getCardsPlayed()){
                if (c.getSuite() == gameTrump.getSuite()){
                    if (c.getValue() > maxTrump){
                        maxTrump = c.getValue();
                        dealHighWinner = p;
                    }
                    if (c.getValue() < minTrump){
                        minTrump = c.getValue();
                        dealLowWinner = p;
                    }
                }
            }
        }
    }
    void findJackPointWinner(){

        for (Player p : playerList){
            for (Card c : p.getCardsPlayed()){
                if (c.getSuite() == gameTrump.getSuite() && c.getValue() == 11){
                    dealJackWinner = p;
                    return;
                }
            }
        }
    }
    int calculateGamePointsFromCardsWon(Player p){
        int points = 0;

        for (Card c : p.getCardsWon()){
            if (c.getValue() == 10){
                points += 10;
            }
            if (c.getValue() == 1){
                points+=4;
            }
            if (c.getValue() == 13){
                points+=3;
            }
            if (c.getValue() == 12){
                points+=2;
            }
            if (c.getValue() == 11){
                points++;
            }
        }
        return points;
    }
    void resetScores(){
        for (Player p : playerList){
            p.setNumPoints(0);
            p.getCardsWon().clear();
        }
    }

    boolean gameFinished(){
        clearScoreBoard();
        createScoreBoardGUI();

        for (Player p : playerList){
            if (p.getNumPoints() >= 7){
                if (p == dealHighWinner){
                    finalGameWinner = p;
                    return true;
                }
                else if (p == dealLowWinner){
                    finalGameWinner = p;
                    return true;
                }
                else if (p == dealJackWinner){
                    finalGameWinner = p;
                    return true;
                }
                else if (p == dealGameWinner){
                    finalGameWinner = p;
                    return true;
                }
            }
        }

        return false;
    }
    void clearScoreBoard(){
        scoreboard.getChildren().clear();
        mainPane.getChildren().remove(scoreboard);
    }

    void evaluatePointsAndBids(){
        int numWon;
        for (Player p : playerList){
            numWon = 0;
            if (dealHighWinner == p){
                numWon++;
            }
            if (dealLowWinner == p){
                numWon++;
            }
            if (dealJackWinner == p){
                numWon++;
            }
            if (dealGameWinner == p){
                numWon++;
            }

            if (p.getBidValue() == 5){
                if (checkForAllSixWin(p) && numWon ==4){
                    awardPoints(p,5);
                }
                else{
                    awardPoints(p,-5);
                }
            }
            else if (p.getBidValue() > 0){
                if (numWon >= p.getBidValue()){
                    awardPoints(p,numWon);
                }
                else{
                    awardPoints(p,(p.getBidValue() * -1));
                }
            }
            else{
                awardPoints(p,numWon);
            }

        }
    }
    boolean checkForAllSixWin(Player player){
        for (Player p : gameTrickWinners){
            if (p != player){
                return false;
            }
        }
        return true;
    }
    void endGame(){
        int max = -1;

        for (Player p : playerList){
            if (p.getNumPoints() > max){
                max = p.getNumPoints();
                finalGameWinner = p;
            }
            else if (p.getNumPoints() == max){
                if (p == dealHighWinner || finalGameWinner == dealHighWinner){
                    if (p == dealHighWinner){
                        finalGameWinner = p;
                    }
                }
                else if (p == dealLowWinner || finalGameWinner == dealLowWinner){
                    if (p == dealLowWinner){
                        finalGameWinner = p;
                    }
                }
                else if (p == dealJackWinner || finalGameWinner == dealJackWinner){
                    if (p == dealJackWinner){
                        finalGameWinner = p;
                    }
                }
                else if (p == dealGameWinner || finalGameWinner == dealGameWinner){
                    if (p == dealGameWinner){
                        finalGameWinner = p;
                    }
                }
            }
        }
        GameResults.alertBox(finalGameWinner,this);
        mainStage.close();
    }
}
