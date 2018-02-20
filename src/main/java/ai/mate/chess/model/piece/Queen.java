package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

import java.awt.*;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    protected boolean isValidMove(Point from, Point to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_QUEEN;
        else
            name = TextHandler.BLACK_QUEEN;
    }

}