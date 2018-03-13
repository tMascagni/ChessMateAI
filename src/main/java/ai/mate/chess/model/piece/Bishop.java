package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
        this.score = Utils.BISHOP_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_BISHOP;
        else
            name = TextHandler.BLACK_BISHOP;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        return true;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();


    }

}