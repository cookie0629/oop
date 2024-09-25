package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

public class Player {
    protected List<Card> hand;
    protected int score;

    public Player() {
        hand = new ArrayList<>();
        score = 0;
    }

    // 添加一张牌到手牌
    public void addCard(Card card) {
        hand.add(card);
        updateScore(card);
    }

    // 更新得分
    protected void updateScore(Card card) {
        score += card.getValue();
        adjustForAce();
    }

    // 返回当前得分
    public int getScore() {
        return score;
    }

    // 处理A牌的特殊情况
    protected void adjustForAce() {
        for (Card card : hand) {
            if (score > 21 && card.getValue() == 11) {
                score -= 10;  // 将A从11变为1
            }
        }
    }

    // 返回手牌内容
    @Override
    public String toString() {
        return "Ваши карты: " + hand.toString() + " => " + score;
    }
}
