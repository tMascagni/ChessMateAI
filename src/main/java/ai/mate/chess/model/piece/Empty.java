package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.BoardPosition;

public final class Empty extends Piece {

    public Empty() {
        super(Color.EMPTY);
        this.score = EMPTY_SCORE;
    }

    @Override
    protected void initName() {
        name = TextHandler.EMPTY;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        /* Empty pieces should NEVER be able to move. There always return false. */
        return false;
    }

    @Override
    public void populateMoves(Board board) {
        /* Do nothing here. */
    }

}