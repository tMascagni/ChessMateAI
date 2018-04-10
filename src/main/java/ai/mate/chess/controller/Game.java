package ai.mate.chess.controller;

import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.ui.gui.board.BoardPresenter;
import ai.mate.chess.ui.gui.board.BoardView;
import ai.mate.chess.ui.gui.promotion.PromotionPresenter;
import ai.mate.chess.ui.gui.promotion.PromotionView;
import ai.mate.chess.ui.tui.Tui;
import ai.mate.chess.utils.ChessUtils;

import javax.swing.*;

public final class Game {

    GameController gameController;
    BoardPresenter boardPresenter;
    PromotionPresenter promotionPresenter;

    private final Tui tui = Tui.getInstance();

    public Game() {
        gameController = GameController.getInstance();

        tui.printChoosePlayer();
        Piece.PlayerColor humanPlayer = tui.getPlayerColorInput();
        tui.printHumanPlayer(humanPlayer);
        tui.printAIPlayer(ChessUtils.changePlayer(humanPlayer));
        // WHITE always starts!
        gameController.setCurrentPlayer(Piece.PlayerColor.WHITE);

        boardPresenter = new BoardPresenter(new BoardView(), gameController, this, humanPlayer);
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