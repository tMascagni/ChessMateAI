package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

/*
 * Bonde
 */
public final class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_PAWN;
        else
            name = TextHandler.BLACK_PAWN;
    }

}