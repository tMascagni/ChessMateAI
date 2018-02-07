package ai.mate.chess.model.piece;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = "White Bishop";
        else
            name = "Black Bishop";
    }

}