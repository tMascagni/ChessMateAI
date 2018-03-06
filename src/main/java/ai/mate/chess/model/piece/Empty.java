package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

public final class Empty extends Piece {

    public Empty() {
        super(Color.WHITE);
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        return false;
    }

    @Override
    protected void initName() {
        name = TextHandler.EMPTY;
    }

    @Override
    public void populateMoves() {

    }

}