package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

/*
 * Konge
 */
public final class King extends Piece {

    public King(Color color) {
        super(color);
        this.score = Utils.KING_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KING;
        else
            name = TextHandler.BLACK_KING;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        return false;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();


    }

}