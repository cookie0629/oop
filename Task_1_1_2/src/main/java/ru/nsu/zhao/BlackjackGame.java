package ru.nsu.zhao;

import java.util.Scanner;

public class BlackjackGame {
    private final Deck deck;
    private final Player player;
    private final Dealer dealer;

    public BlackjackGame() {
        deck = new Deck();
        player = new Player();
        dealer = new Dealer();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        // 发两张牌给玩家，两张牌都明的
        player.addCard(deck.dealCard());
        player.addCard(deck.dealCard());

        // 发两张牌给庄家，一张明的，一张盖着的
        dealer.addCard(deck.dealCard(), false);
        dealer.addCard(deck.dealCard(), true);

        // 打印玩家和庄家的牌
        System.out.println("Дилер раздал карты");
        System.out.println(player);
        System.out.println(dealer);

        // 玩家回合
        boolean playerContinue = true;
        while (playerContinue && player.getScore() <= 21) {
            System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться...");
            int action = scanner.nextInt();
            if (action == 1) {
                player.addCard(deck.dealCard());
                System.out.println(player);
            } else {
                playerContinue = false;
            }
        }

        // 庄家回合
        System.out.println("Ход дилера");
        dealer.revealFaceDownCard();
        System.out.println(dealer);

        while (dealer.mustDrawCard()) {
            dealer.addCard(deck.dealCard(), false);
            System.out.println("Дилер открыл карту: " + dealer);
        }

        // 判断输赢
        if (player.getScore() > 21) {
            System.out.println("Вы проиграли!");
        } else if (dealer.getScore() > 21 || player.getScore() > dealer.getScore()) {
            System.out.println("Вы выиграли!");
        } else if (player.getScore() == dealer.getScore()) {
            System.out.println("Ничья!");
        } else {
            System.out.println("Вы проиграли!");
        }
    }

    public static void main(String[] args) {
        BlackjackGame game = new BlackjackGame();
        game.startGame();
    }
}
