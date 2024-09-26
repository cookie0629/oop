package ru.nsu.zhao;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> hand;  // 玩家/庄家的手牌
    private final boolean isDealer; // 用于区分玩家和庄家
    private Card faceDownCard;      // 庄家的盖牌（仅当是庄家时有用）

    public Player(boolean isDealer) {
        this.hand = new ArrayList<>();
        this.isDealer = isDealer;
    }

    // 添加手牌，庄家可以选择是否有盖牌
    public void addCard(Card card, boolean isFaceDown) {
        if (isDealer && isFaceDown) {
            this.faceDownCard = card;
        } else {
            this.hand.add(card);
        }
    }

    // 添加明牌
    public void addCard(Card card) {
        addCard(card, false);  // 默认是明牌
    }

    // 亮出盖牌
    public void revealFaceDownCard() {
        if (this.faceDownCard != null) {
            this.hand.add(faceDownCard);  // 将盖牌加入到手牌中
            this.faceDownCard = null;     // 重置盖牌
        }
    }

    // 计算手牌的总分，自动调整 Ace 的值
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

    // 庄家在得分小于 17 时必须继续抽牌
    public boolean mustDrawCard() {
        return isDealer && getScore() < 17;
    }

    @Override
    public String toString() {
        if (isDealer && this.faceDownCard != null) {
            return "Ваши карты: " + hand + ", <закрытая карта>";  // 显示有一张盖牌
        } else {
            return "Ваши карты: " + hand + " => " + getScore();  // 显示手牌和得分
        }
    }
}
