package ai.mate.chess.model.piece;

/*
 * Abstract class that implements the piece interface.
 * This is used for the actual Pieces to inherit from.
 */
public abstract class Piece implements IPiece {

    protected final Color color;
    protected String name;

    public Piece(Color color) {
        this.color = color;
        initName();
    }

    protected abstract void initName();

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Piece [" + name + "]";
    }

}