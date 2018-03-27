package ai.mate.chess.model.piece;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
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
    protected List<IPiece> slayMoves;

    public Piece(Color color) {
        this.color = color;
        ID = idCounter.getAndIncrement();
        initName();
        possibleMoves = new ArrayList<>();
        slayMoves = new ArrayList<>();
    }

    protected abstract void initName();

    public abstract void populateMoves(Board board);

    protected void resetMoves() {
        possibleMoves.clear();
        slayMoves.clear();
    }

    protected boolean isInBoardBounds(int rowX, int colY) {
        return (rowX >= 0 && rowX <= 7) && (colY >= 0 && colY <= 7);
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        int deltaRowX = calculateDeltaRowX(from.rowX, to.rowX);
        int deltaColY = calculateDeltaColY(from.colY, to.colY);

        for (Point p : possibleMoves)
            if (p.x == deltaRowX && p.y == deltaColY)
                return true;

        return false;
    }

    @Override
    public Point calculateDeltaMove(Point piecePos, Point move) {
        int deltaRowX = calculateDeltaRowX(piecePos.x, move.x);
        int deltaColY = calculateDeltaColY(piecePos.y, move.y);
        return new Point(deltaRowX, deltaColY);
    }

    @Override
    public int calculateDeltaRowX(int fromRowX, int toRowX) {
        return toRowX - fromRowX;
    }

    @Override
    public int calculateDeltaColY(int fromColY, int toColY) {
        return toColY - fromColY;
    }

    @Override
    public List<Point> getPossibleMoves() {
        return possibleMoves;
    }

    @Override
    public List<IPiece> getSlayMoves() {
        return slayMoves;
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
    public void decMoveCount() {
        moveCount--;
    }

    @Override
    public void decSlayCount() {
        slayCount--;
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
    public int getId() {
        return ID;
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
    public String toString() {
        return name;
    }

}