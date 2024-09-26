package ru.nsu.zhao;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainTest {

    @Test
    public void testCardCreation() {
        Card card = new Card("Пики", "Дама", 10);
        assertEquals(10, card.getValue());
        assertEquals("Дама Пики (10)", card.toString());
    }

    @Test
    public void testAceValueChange() {
        Card ace = new Card("Трефы", "Туз", 11);
        assertEquals(11, ace.getValue());
        ace.setAceValue(1);
        assertEquals(1, ace.getValue());
    }


    @Test
    public void testDealCard() {
        Deck deck = new Deck();
        Card card = deck.dealCard();
        assertNotNull(card);
    }
}
