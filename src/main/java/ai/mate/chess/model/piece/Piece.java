package ai.mate.chess.model.piece;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.interfaces.IPiece;

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

    public Piece(Color color) {
        this.color = color;
        ID = idCounter.getAndIncrement();
        initName();
        possibleMoves = new ArrayList<>();
    }

    protected abstract void initName();

    public abstract void populateMoves(Board board);

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
    public void incMoveCount() {
        moveCount++;
    }

    @Override
    public void incSlayCount() {
        slayCount++;
    }

    @Override
    public List<Point> getPossibleMoves() {
        return possibleMoves;
    }

    @Override
    public List<Point> getPossibleMovesCoordinates(Board board) {
        List<Point> possibleMovesCoordinates = new ArrayList<>();

        BoardPosition pieceBoardPos = board.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.rowX, pieceBoardPos.colY);

        for (Point possibleMove : possibleMoves) {
            Point moveCoordinates = new Point(piecePos.x + possibleMove.x, piecePos.y + possibleMove.y);
            possibleMovesCoordinates.add(moveCoordinates);
        }

        return possibleMovesCoordinates;
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
    public String toString() {
        return name;
    }

    Point calculateDeltaMove(Point piecePos, Point move) {
        int deltaRowX = calculateDeltaRowX(piecePos.x, move.x);
        int deltaColY = calculateDeltaColY(piecePos.y, move.y);
        return new Point(deltaRowX, deltaColY);
    }

    void resetMoves() {
        possibleMoves.clear();
    }

    boolean isOutOfBounds(int rowX, int colY) {
        return (rowX < 0 || rowX > 7) || (colY < 0 || colY > 7);
    }

    private int calculateDeltaRowX(int fromRowX, int toRowX) {
        return toRowX - fromRowX;
    }

    private int calculateDeltaColY(int fromColY, int toColY) {
        return toColY - fromColY;
    }

    public static IPiece.Color getOpponentColor(IPiece.Color playerColor) {
        if (playerColor.equals(IPiece.Color.EMPTY))
            return IPiece.Color.EMPTY;

        if (playerColor.equals(IPiece.Color.WHITE))
            return IPiece.Color.BLACK;

        return IPiece.Color.WHITE;
    }

}