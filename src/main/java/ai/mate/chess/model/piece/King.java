package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

/*
 * Konge
 */
public final class King extends Piece {

    public King(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KING;
        else
            name = TextHandler.BLACK_KING;
    }

}