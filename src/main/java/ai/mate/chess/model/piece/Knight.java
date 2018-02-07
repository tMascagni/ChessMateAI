package ai.mate.chess.model.piece;

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
            name = "White Knight";
        else
            name = "Black Knight";
    }

}