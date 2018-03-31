package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.BoardPosition;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract class that implements the piece interface.
 * This is used for the actual Pieces to inherit from.
 */
public abstract class Piece {

    public static final int BISHOP_SCORE = 3;
    public static final int ROOK_SCORE = 5;
    public static final int QUEEN_SCORE = 9;
    public static final int PAWN_SCORE = 1;
    public static final int KNIGHT_SCORE = 3;
    public static final int KING_SCORE = 100;
    public static final int EMPTY_SCORE = 0;

    private static final AtomicInteger idCounter = new AtomicInteger(0);

    protected final int ID;

    protected final Color color;
    protected String name;

    protected int moveCount;
    protected int slayCount;

    protected int score;

    protected List<Point> possibleMoves;

    public enum Color {
        WHITE, BLACK, EMPTY
    }

    public Piece(Color color) {
        this.color = color;
        ID = idCounter.getAndIncrement();
        initName();
        possibleMoves = new ArrayList<>();
    }

    protected abstract void initName();

    public abstract void populateMoves(Board board);

    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        int deltaRowX = calculateDeltaRowX(from.rowX, to.rowX);
        int deltaColY = calculateDeltaColY(from.colY, to.colY);

        for (Point p : possibleMoves)
            if (p.x == deltaRowX && p.y == deltaColY)
                return true;

        return false;
    }

    public void incMoveCount() {
        moveCount++;
    }

    public void incSlayCount() {
        slayCount++;
    }

    public List<Point> getPossibleMoves() {
        return possibleMoves;
    }

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

    public int getMoveCount() {
        return moveCount;
    }

    public int getSlayCount() {
        return slayCount;
    }

    public int getId() {
        return ID;
    }

    public int getScore() {
        return score;
    }

    public Color getColor() {
        return color;
    }

    public String toString() {
        return name;
    }

    protected Point calculateDeltaMove(Point piecePos, Point move) {
        int deltaRowX = calculateDeltaRowX(piecePos.x, move.x);
        int deltaColY = calculateDeltaColY(piecePos.y, move.y);
        return new Point(deltaRowX, deltaColY);
    }

    protected void resetMoves() {
        possibleMoves.clear();
    }

    protected boolean isOutOfBounds(int rowX, int colY) {
        return (rowX < 0 || rowX > 7) || (colY < 0 || colY > 7);
    }

    private int calculateDeltaRowX(int fromRowX, int toRowX) {
        return toRowX - fromRowX;
    }

    private int calculateDeltaColY(int fromColY, int toColY) {
        return toColY - fromColY;
    }

    public static Piece.Color getOpponentColor(Piece.Color playerColor) {
        if (playerColor.equals(Piece.Color.EMPTY))
            return Piece.Color.EMPTY;

        if (playerColor.equals(Piece.Color.WHITE))
            return Piece.Color.BLACK;

        return Piece.Color.WHITE;
    }

}