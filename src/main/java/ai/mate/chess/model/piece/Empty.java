package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

public final class Empty extends Piece {

    public Empty() {
        super(Color.EMPTY);
        this.score = Utils.EMPTY_SCORE;
    }

    @Override
    protected void initName() {
        name = TextHandler.EMPTY;
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