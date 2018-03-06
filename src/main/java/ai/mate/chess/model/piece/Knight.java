package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

/*
 * Haest
 */
public final class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.score = 3;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KNIGHT;
        else
            name = TextHandler.BLACK_KNIGHT;
    }

    @Override
    public void populateMoves() {

    }

}