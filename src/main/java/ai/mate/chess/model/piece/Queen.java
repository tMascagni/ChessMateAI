package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.score = 9;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_QUEEN;
        else
            name = TextHandler.BLACK_QUEEN;
    }

    @Override
    public void populateMoves() {

    }

}