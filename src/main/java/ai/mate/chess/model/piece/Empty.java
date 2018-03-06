package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

public final class Empty extends Piece {

    public Empty() {
        super(Color.WHITE);
        this.score = Utils.EMPTY_SCORE;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, IPiece[][] board) {
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