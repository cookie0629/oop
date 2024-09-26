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

    @Test
    public void testAddCardAndScore() {
        Player player = new Player();
        Card card1 = new Card("Пики", "Дама", 10);
        Card card2 = new Card("Червы", "Тройка", 3);

        player.addCard(card1);
        player.addCard(card2);

        assertEquals(13, player.getScore());
    }

    @Test
    public void testAdjustForAce() {
        Player player = new Player();
        Card ace = new Card("Трефы", "Туз", 11);
        Card highCard = new Card("Бубны", "Десятка", 10);

        player.addCard(ace);
        player.addCard(highCard);

        assertEquals(21, player.getScore());

        player.addCard(new Card("Пики", "Двойка", 2));
        assertEquals(13, player.getScore()); // 11 + 10 + 2 -> 1 + 10 + 2 = 13
    }

    @Test
    public void testBustCondition() {
        Player player = new Player();
        player.addCard(new Card("Пики", "Дама", 10));
        player.addCard(new Card("Червы", "Десятка", 10));
        player.addCard(new Card("Трефы", "Двойка", 2));

        assertTrue(player.getScore() > 21);
    }
}
