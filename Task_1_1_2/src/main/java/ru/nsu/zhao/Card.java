package ru.nsu.zhao;

import java.util.Objects;

public class Card {
    private final String suit;
    private final String rank;
    private int value;
    public static final String ACE = "Туз";

    public Card(String suit, String rank, int value) {
        this.suit = suit;
        this.rank = rank;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }

    // A 牌可以根据需要切换为 1 或 11
    public void setAceValue(int newValue) {
        if (Objects.equals(rank, ACE)) {
            this.value = newValue;
        }
    }

    @Override
    public String toString() {
        return rank + " " + suit + " (" + value + ")";
    }
}
