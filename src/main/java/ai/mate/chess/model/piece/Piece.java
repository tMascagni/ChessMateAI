package ai.mate.chess.model.piece;

import ai.mate.chess.model.BoardPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/*
 * Abstract class that implements the piece interface.
 * This is used for the actual Pieces to inherit from.
 */
public abstract class Piece implements IPiece {

    protected final Color color;
    protected String name;
    protected int moveCount;
    protected int slayCount;
    protected int score;

    protected List<Point> possibleMoves;

    public Piece(Color color) {
        this.color = color;
        initName();
        possibleMoves = new ArrayList<Point>();
        populateMoves();
    }

    protected abstract void initName();

    public abstract void populateMoves();

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