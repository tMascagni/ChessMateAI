package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

import java.awt.*;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    protected boolean isValidMove(Point from, Point to) {
        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
    }

}