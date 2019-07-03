package sample;

import java.util.ArrayList;

public class Pitch_Dealer implements Dealer {
    private Deck mainDeck;

    Pitch_Dealer() {
        mainDeck = new Deck();
        mainDeck.shuffleDeck();
    }

    @Override
    public ArrayList<Card> dealHand() {
        return mainDeck.getCards(6);
    }

    @Override
    public void resetDeck(){
        mainDeck.resetDeck();
        mainDeck.shuffleDeck();
    }
    @Override
    public int getDeckSize(){
        return mainDeck.deckSize();
    }

}
