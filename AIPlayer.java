package sample;

import java.util.ArrayList;
public class AIPlayer extends Player {

    private String bidSuite;

    AIPlayer(int id){
        super(id);
    }

    String getBidSuite(){
        return bidSuite;
    }
    void setBidSuite(String suite){
        bidSuite = suite;
    }

    @Override
    void bid(Pitch Game){
        int maxBid = 0;

        //System.out.println("Computer Player " + getPlayerID() + " is bidding . . .");

        if (Game.getPlayerList().get(0).getBidValue() == 5){
            setBidName("Pass");
            setBidValue(5);
            return;
        }

        for (Player p : Game.getPlayerList()){
            if (p.getBidValue() > maxBid){
                maxBid = p.getBidValue();
            }
        }

        calculateBidBasedOnHand(maxBid);

        if (getBidValue() == -1){
            setBidName("Pass");
            setBidValue(0);
            setBidSuite(null);
        }

    }

    void calculateBidBasedOnHand(int maxBid){
        ArrayList<Card> hearts = new ArrayList<>();
        ArrayList<Card> diamonds = new ArrayList<>();
        ArrayList<Card> clubs = new ArrayList<>();
        ArrayList<Card> spades = new ArrayList<>();

        int heartsPotential = 0;
        int diamondsPotential = 0;
        int clubsPotential = 0;
        int spadesPotential = 0;


        for (Card c : getCurrentHand()){
            if (c.getSuite() == "H"){
                hearts.add(c);
            }
            else if (c.getSuite() == "D"){
                diamonds.add(c);
            }
            else if (c.getSuite() == "C"){
                clubs.add(c);
            }
            else{
                spades.add(c);
            }
        }

        if (hearts.size() >= 2){
            for (int i = 0; i < hearts.size(); i++){
                int value = hearts.get(i).getValue();
                if (value == 1 || value == 11 || value == 12 || value == 13 || value == 2 || value == 3){
                    if (heartsPotential < 5){
                        heartsPotential++;
                    }
                }
            }
        }
        if (diamonds.size() >=2){
            for (int i = 0; i < diamonds.size(); i++){
                int value = diamonds.get(i).getValue();
                if (value == 1 || value == 11 || value == 12 || value == 13 || value == 2 || value == 3){
                    if (diamondsPotential < 5){
                        diamondsPotential++;
                    }
                }
            }
        }
        if (clubs.size() >= 2){
            for (int i = 0; i < clubs.size(); i++){
                int value = clubs.get(i).getValue();
                if (value == 1 || value == 11 || value == 12 || value == 13 || value == 2 || value == 3){
                    if (clubsPotential < 5){
                        clubsPotential++;
                    }
                }
            }
        }
        if (spades.size() >= 2){
            for (int i = 0; i < spades.size(); i++){
                int value = spades.get(i).getValue();
                if (value == 1 || value == 11 || value == 12 || value == 13 || value == 2 || value == 3){
                    if (spadesPotential < 5){
                        spadesPotential++;
                    }
                }
            }
        }

        findFinalBid(heartsPotential, diamondsPotential, clubsPotential, spadesPotential,maxBid);


    }
    void findFinalBid(int hp, int dp, int cp, int sp, int maxBid){
        ArrayList<Integer> potentialList = new ArrayList<>();
        int max = 0;

        potentialList.add(hp);
        potentialList.add(dp);
        potentialList.add(cp);
        potentialList.add(sp);

        int j = 0;

        for (int i = 0; i < potentialList.size(); i++){
            if (potentialList.get(i) > max){
                max = potentialList.get(i);
                j = i;
            }
        }

        if (max > maxBid && max >=2){
            setBidName(String.valueOf(max));
            setBidValue(max);

            if (j == 0){
                setBidSuite("H");
            }
            else if (j == 1){
                setBidSuite("D");
            }
            else if (j == 2){
                setBidSuite("C");
            }
            else{
                setBidSuite("S");
            }
        }
    }
    @Override
    public Card makeMove(Pitch Game){
        Card moveCard = null;

        if (Game.getTrumpFound() == false){
            moveCard = deployFirstPlayerStrategy(Game);
            Game.setTrumpFound(true);
            Game.setGameTrump(moveCard);
            Game.displayTrumpCard();
        }
        else{
            if (Game.getRoundCards().size() == 0){
                moveCard = evaluateHandOnly(Game);
            }
            else{
                moveCard = evaluateHandAndTable(Game);
            }
        }

        getCurrentHand().remove(moveCard);
        Game.getCardsAlreadyPlayed().add(moveCard);
        setCardPlayedOnRound(moveCard);
        getCardsPlayed().add(moveCard);

        Game.addCardToStage(moveCard);
        if (Game.getRoundLead() == null){
            Game.setRoundLead(moveCard);
        }

        return moveCard;
    }
    private Card deployFirstPlayerStrategy(Pitch G){
        ArrayList<Card> bidCards = new ArrayList<>();
        int min = 13;

        Card lowCard = null;

        for (Card c : getCurrentHand()){
            if (c.getSuite() == bidSuite){
                bidCards.add(c);
            }
        }
        for (Card c : bidCards){
            if (c.getValue() < min){
                lowCard = c;
                min = c.getValue();
            }
        }

        return lowCard;
    }
    private Card evaluateHandOnly(Pitch Game){
        Card makeMove = null;
        ArrayList<Card> trumpCards = new ArrayList<>();
        int val = 0;
        int numTrumpPlays = 0;
        double previousPlayedAverage = 0.0;

        for (Card c : Game.getCardsAlreadyPlayed()){
            if (c.getSuite() == Game.getGameTrump().getSuite()){
                val += c.getValue();
                numTrumpPlays++;
            }
        }
        previousPlayedAverage = val / (numTrumpPlays * 1.0);

        for (Card c : getCurrentHand()){
            if (c.getSuite() == Game.getGameTrump().getSuite()){
                trumpCards.add(c);
            }
        }
        if (trumpCards.size() == 0){
            for (Card c : getCurrentHand()){
                if (c.getValue() < 10){
                    trumpCards.add(c);
                }
            }
            if (trumpCards.size() == 0){
                makeMove = getCurrentHand().get(0);
            }
        }
        else{
            for (Card c : trumpCards){
                if (c.getValue() < previousPlayedAverage){
                    makeMove = c;
                }
            }
        }
        if (makeMove == null){
            makeMove = getCurrentHand().get(0);
        }
        return makeMove;
    }

    private Card evaluateHandAndTable(Pitch Game){
        int numFaces = 0;
        Card makeMove = null;
        ArrayList<Card> trumpCards = new ArrayList<>();

        for (Card c : Game.getRoundCards()){
            if (c.getValue() == 1){
                numFaces = 4;
            }
            if (c.getValue() == 10){
                numFaces = 10;
            }
            if (c.getValue() == 11){
                numFaces = 1;
            }
            if (c.getValue() == 12){
                numFaces = 2;
            }
            if (c.getValue() == 13){
                numFaces = 3;
            }
        }
        for (Card c : getCurrentHand()){
            if (c.getSuite() == Game.getGameTrump().getSuite()){
                trumpCards.add(c);
            }
        }
        if (trumpCards.size() == 0){
            return evaluateHandOnly(Game);
        }
        else{
            int max = 0;
            if (numFaces > 5){
                int i = 0;
                int j = 0;
                for (Card c : trumpCards){
                    if (c.getValue() > max){
                        max = c.getValue();
                        j = i;
                    }
                    i++;
                }
                return trumpCards.get(j);
            }
            else{
                return trumpCards.get(0);
            }
        }
    }
}
