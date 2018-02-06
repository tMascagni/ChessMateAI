package ai.mate.chess.model.piece;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = "White Rook";
        else
            name = "Black Rook";
    }

}