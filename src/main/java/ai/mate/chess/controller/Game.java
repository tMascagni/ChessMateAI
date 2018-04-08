package ai.mate.chess.controller;

import ai.mate.chess.ui.gui.board.BoardPresenter;
import ai.mate.chess.ui.gui.board.BoardView;
import ai.mate.chess.ui.gui.promotion.PromotionPresenter;
import ai.mate.chess.ui.gui.promotion.PromotionView;

import javax.swing.*;

public final class Game {

    public static final int MAX_PLY = 4;
    public static final int MIN_PLY = 1;

    GameController gameController;
    BoardPresenter boardPresenter;
    PromotionPresenter promotionPresenter;

    public Game() {
        gameController = GameController.getInstance();
        boardPresenter = new BoardPresenter(new BoardView(), gameController, this);
    }

    public void showPawnPromotionView() {
        promotionPresenter = new PromotionPresenter(new PromotionView(gameController.getCurrentPlayer().getPlayerColor()), gameController, this);
        boardPresenter.pause();
        promotionPresenter.loadView();
    }

    public void unpause() {
        boardPresenter.unpause();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Game::new);
    }

}