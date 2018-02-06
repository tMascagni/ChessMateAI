package ai.mate.chess.model.piece;

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
            name = "White Pawn";
        else
            name = "Black Pawn";
    }

}