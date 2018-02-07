package ai.mate.chess.model.piece;

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
            name = "White King";
        else
            name = "Black King";
    }

}