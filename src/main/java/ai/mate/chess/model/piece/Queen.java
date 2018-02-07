package ai.mate.chess.model.piece;

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
            name = "White Queen";
        else
            name = "Black Queen";
    }

}