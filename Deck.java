package sample;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Deck {
    private ArrayList<Card> deck;
    public ArrayList<Image> cardImages;

    Deck() {
        deck = new ArrayList<Card>();

        for (int i = 1; i <= 13; i++){
            deck.add(new Card("H", i));
            deck.add(new Card("D", i));
            deck.add(new Card("C", i));
            deck.add(new Card("S", i));
        }
    }
    public void shuffleDeck(){
        if (deck.size() == 0){
            return;
        }

        Collections.shuffle(deck);
    }
    public ArrayList<Card> getCards(int numCards){

        ArrayList<Card> temp;

        if (numCards > 52){
            return deck;
        }
        if (numCards < 1){
            return null;
        }

        temp = new ArrayList<Card>(deck.subList(0,numCards));

        int j = 0;
        for (int i = 0; i < numCards; i++){
            deck.remove(j);
        }

        return temp;
    }

    public void resetDeck(){
        deck.clear();
        for (int i = 1; i <= 13; i++){
            deck.add(new Card("H", i));
            deck.add(new Card("D", i));
            deck.add(new Card("C", i));
            deck.add(new Card("S", i));
        }
    }
    public int deckSize(){
        return deck.size();
    }

}
