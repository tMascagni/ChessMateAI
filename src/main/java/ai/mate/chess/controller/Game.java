package ai.mate.chess.controller;

import ai.mate.chess.algorithm.AI;
import ai.mate.chess.ui.gui.board.BoardPresenter;
import ai.mate.chess.ui.gui.board.BoardView;
import ai.mate.chess.ui.gui.promotion.PromotionPresenter;
import ai.mate.chess.ui.gui.promotion.PromotionView;

import javax.swing.*;
import java.util.Scanner;

/**
 * Entry point of the program.
 * VERY IMPORTANT.
 */
public final class Game {

    public static final int MAX_DEPTH = 4;
    public static final int MIN_DEPTH = 1;

    GameController gameController;
    BoardPresenter boardPresenter;
    PromotionPresenter promotionPresenter;

    public Game(int depth) {
        gameController = GameController.getInstance();
        AI ai = new AI(depth);
        boardPresenter = new BoardPresenter(new BoardView(), gameController, this, ai);
    }

    public void showPawnPromotionView() {
        promotionPresenter = new PromotionPresenter(new PromotionView(gameController.getCurrentPlayer().getPlayerColor()), gameController, this);
        boardPresenter.pause();
        promotionPresenter.loadView();
    }

    /**
     * Unpauses the boardPresenter, not the Game. Despite this method being in Game. I am a lying method. I lie.
     */
    public void unpause() {
        boardPresenter.unpause();
    }

    public static void main(String[] args) {
        int depth = 0;
        while (true) {
            depth = askUserForDepth();
            if (depth >= MIN_DEPTH && depth <= MAX_DEPTH) {
                break;
            } else {
                System.out.printf("Invalid, please enter between %d - %d for depth %n", MIN_DEPTH, MAX_DEPTH);
            }
        }

        int finalDepth = depth;
        SwingUtilities.invokeLater(() -> new Game(finalDepth));
    }

    private static int askUserForDepth() {
        Scanner s = new Scanner(System.in);
        System.out.printf("What depth would you like for the AI? (max %d): ", MAX_DEPTH);
        return s.nextInt();
    }


}
