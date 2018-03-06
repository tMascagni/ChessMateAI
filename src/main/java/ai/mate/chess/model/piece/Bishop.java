package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

import java.awt.*;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
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
            name = TextHandler.WHITE_BISHOP;
        else
            name = TextHandler.BLACK_BISHOP;
    }

    @Override
    public void populateMoves() {

    }

}