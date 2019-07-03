package sample;
import java.util.ArrayList;

interface Dealer {
    public ArrayList<Card> dealHand();
    public void resetDeck();
    public int getDeckSize();
}
