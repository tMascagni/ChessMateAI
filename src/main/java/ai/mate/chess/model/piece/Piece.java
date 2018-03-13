package ai.mate.chess.model.piece;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/*
 * Abstract class that implements the piece interface.
 * This is used for the actual Pieces to inherit from.
 */
public abstract class Piece implements IPiece {

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    protected final int ID;

    protected final Color color;
    protected String name;

    protected int moveCount;
    protected int slayCount;

    protected int score;

    protected List<Point> possibleMoves;

    public Piece(Color color) {
        this.color = color;
        ID = idCounter.getAndIncrement();
        initName();
        possibleMoves = new ArrayList<>();
    }

    protected abstract void initName();

    public abstract boolean isValidMove(BoardPosition from, BoardPosition to, Board board);

    public abstract void populateMoves(Board board);

    @Override
    public int calculateDeltaX(int fromX, int toX) {
        int deltaX = toX - fromX;
        System.out.println("deltaX: " + deltaX);
        return deltaX;
    }

    @Override
    public int calculateDeltaY(int fromY, int toY) {
        int deltaY = toY - fromY;
        System.out.println("deltaY: " + deltaY);
        return deltaY;
    }

    @Override
    public void incMoveCount() {
        moveCount++;
    }

    @Override
    public void incSlayCount() {
        slayCount++;
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
    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public Color getOpponentColor() {
        if (getColor().equals(Color.EMPTY))
            return Color.EMPTY;

        if (getColor().equals(Color.WHITE))
            return Color.BLACK;
        return Color.WHITE;
    }

    @Override
    public int getId() {
        return ID;
    }

    @Override
    public String toString() {
        return name;
    }

}