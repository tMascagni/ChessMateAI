package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_QUEEN;
        else
            name = TextHandler.BLACK_QUEEN;
    }

}