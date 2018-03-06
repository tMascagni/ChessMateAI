package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.score = Utils.QUEEN_SCORE;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, IPiece[][] board) {
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