package ai.mate.chess.ui.gui.promotion;

import ai.mate.chess.controller.Game;
import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

public class PromotionPresenter implements PromotionGUIContract.Presenter {

    private final PromotionGUIContract.View view;
    private final GameController gameController;
    private final Game game;

    public PromotionPresenter(PromotionGUIContract.View view, GameController gameController, Game game) {
        this.view = view;
        this.view.setPresenter(this);
        this.gameController = gameController;
        this.game = game;
    }

    public void loadView() {
        view.showView();
    }

    @Override
    public void handlePromotion(Piece selectedPiece) {
        Piece pawnToPromote = gameController.getSelectedPiece();
        selectedPiece.setPosition(pawnToPromote.getPosition());
        gameController.setTile(pawnToPromote.getPosition(), new Tile(selectedPiece));
        view.hideView();
        game.unpause();
    }

    @Override
    public Piece.PlayerColor getTeam() {
        return gameController.getCurrentPlayer().getPlayerColor();
    }

}