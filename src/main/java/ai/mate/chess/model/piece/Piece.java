package ai.mate.chess.model.piece;

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
        possibleMoves = new ArrayList<>();
        populateMoves();
    }

    protected abstract void initName();

    public abstract void populateMoves();

    @Override
    public void incMoveCount() {
        moveCount++;
    }

    @Override
    public void incSlayCount() {
        slayCount++;
    }

    @Override
    public int calculateDeltaX(int fromX, int toX) {
        System.out.println("deltaX: " + Math.abs(fromX - toX));
        return Math.abs(fromX - toX);
    }

    @Override
    public int calculateDeltaY(int fromY, int toY) {
        System.out.println("deltaY: " + Math.abs(fromY - toY));
        return Math.abs(fromY - toY);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getMoveCount() {
        return moveCount;
    }

    @Override
    public int getSlayCount() {
        return slayCount;
    }

    @Override
    public String toString() {
        return name;
    }

}