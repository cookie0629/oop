package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    public static final String SPADES = "Пики";
    public static final String HEARTS = "Червы";
    public static final String CLUBS = "Трефы";
    public static final String DIAMONDS = "Бубны";

    private final List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {SPADES, HEARTS, CLUBS, DIAMONDS};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10, 11};

        for (String suit : suits) {
            for (int i = 0; i < ranks.length; i++) {
                cards.add(new Card(suit, ranks[i], values[i]));
            }
        }
        shuffle();
    }
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card dealCard() {
        return cards.remove(0);
    }
}
