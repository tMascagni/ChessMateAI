package ai.mate.chess.model.piece;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;

import java.awt.*;
import java.util.List;

/**
 * Interface for the general chess piece.
 */
public interface IPiece {

    enum Color {
        WHITE, BLACK, EMPTY
    }

    void populateMoves(Board board);
    boolean isValidMove(BoardPosition from, BoardPosition to);

    Point calculateDeltaMove(Point piecePos, Point move);
    int calculateDeltaRowX(int fromRowX, int toRowX);
    int calculateDeltaColY(int fromColY, int toColY);

    List<Point> getPossibleMoves();
    List<IPiece> getSlayMoves();

    void incMoveCount();
    void incSlayCount();
    void decMoveCount();
    void decSlayCount();

    int getMoveCount();
    int getSlayCount();

    int getId();
    int getScore();
    Color getColor();
    Color getOpponentColor();
}