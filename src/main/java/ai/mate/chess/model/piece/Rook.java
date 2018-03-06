package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.score = 5;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
    }

    @Override
    public void populateMoves() {

    }

}