package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

/*
 * Haest
 */
public final class Knight extends Piece {

    public Knight(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KNIGHT;
        else
            name = TextHandler.BLACK_KNIGHT;
    }

}