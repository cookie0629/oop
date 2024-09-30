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
        int score = 0;
        int aceCount = 0;

        // 计算当前得分并统计 Ace 的数量
        for (Card card : hand) {
            score += card.getValue();
            if (card.getRank().equals("Туз")) { // 判断是否为 Ace
                aceCount++;
            }
        }

        // 如果分数大于 21，并且手牌中有 Ace，则将 Ace 的值从 11 调整为 1
        while (score > 21 && aceCount > 0) {
            score -= 10;  // 每次调整一个 Ace 的值从 11 变成 1
            aceCount--;
        }

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

