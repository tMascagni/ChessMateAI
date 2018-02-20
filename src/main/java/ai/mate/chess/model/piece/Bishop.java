package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

import java.awt.*;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    protected boolean isValidMove(Point from, Point to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_BISHOP;
        else
            name = TextHandler.BLACK_BISHOP;
    }

}