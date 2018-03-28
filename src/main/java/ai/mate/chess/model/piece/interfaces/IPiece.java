package ai.mate.chess.model.piece.interfaces;

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

    void incMoveCount();
    void incSlayCount();

    List<Point> getPossibleMoves();
    List<Point> getPossibleMovesCoordinates(Board board);

    int getMoveCount();
    int getSlayCount();

    int getId();
    int getScore();
    Color getColor();
}