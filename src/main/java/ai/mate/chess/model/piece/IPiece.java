package ai.mate.chess.model.piece;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;

import java.awt.Point;
import java.util.List;


/*
 * Interface for the general chess piece.
 */
public interface IPiece {
    enum Color {
        WHITE, BLACK, EMPTY
    }

    boolean isValidMove(BoardPosition from, BoardPosition to, Board board);
    void populateMoves(Board board);

    int calculateDeltaX(int fromX, int toX);
    int calculateDeltaY(int fromY, int toY);
    Point calculateDeltaMove(Point piecePos, Point move);

    void incMoveCount();
    void incSlayCount();

    int getMoveCount();
    int getSlayCount();

    String getName();
    int getScore();
    Color getColor();
    Color getOpponentColor();
    int getId();
    List<Point> getPossibleMoves();

    String toString();
}