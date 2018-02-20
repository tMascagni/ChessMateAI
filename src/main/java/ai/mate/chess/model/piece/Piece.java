package ai.mate.chess.model.piece;

import java.awt.*;

/*
 * Abstract class that implements the piece interface.
 * This is used for the actual Pieces to inherit from.
 */
public abstract class Piece implements IPiece {

    protected final Color color;
    protected String name;
    protected int moveCount;
    protected int slayCount;

    public Piece(Color color) {
        this.color = color;
        initName();
    }

    protected abstract boolean isValidMove(Point from, Point to);

    protected abstract void initName();

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getSlayCount() {
        return slayCount;
    }

    @Override
    public String toString() {
        return name;
    }

}