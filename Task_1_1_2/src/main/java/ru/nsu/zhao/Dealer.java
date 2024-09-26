package ru.nsu.zhao;

public class Dealer extends Player {
    private Card faceDownCard;

    // 在发两张牌时，设置一张面朝下的牌
    public void addCard(Card card, boolean isFaceDown) {
        if (isFaceDown) {
            this.faceDownCard = card;
        } else {
            super.addCard(card);
        }
    }

    // 亮出盖牌
    public void revealFaceDownCard() {
        if (this.faceDownCard != null) {
            super.addCard(faceDownCard);  // 将面朝下的牌加入到手牌中
            this.faceDownCard = null;  // 重置面朝下的牌
        }
    }

    // 庄家必须在得分少于17时继续拿牌
    public boolean mustDrawCard() {
        return this.getScore() < 17;
    }

    @Override
    public String toString() {
        if (this.faceDownCard != null) {
            return super.toString() + ", <закрытая карта>";  // 显示有一张盖牌
        } else {
            return super.toString();  // 正常显示
        }
    }
}