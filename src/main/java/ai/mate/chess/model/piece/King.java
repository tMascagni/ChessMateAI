package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

/*
 * Konge
 */
public final class King extends Piece {

    public King(Color color) {
        super(color);
        this.score = 100;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KING;
        else
            name = TextHandler.BLACK_KING;
    }

    @Override
    public void populateMoves() {

    }

}