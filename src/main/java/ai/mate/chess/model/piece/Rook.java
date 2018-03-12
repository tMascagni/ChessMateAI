package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.score = Utils.ROOK_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
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