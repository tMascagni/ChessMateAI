package ai.mate.chess.model.piece;

import ai.mate.chess.model.BoardPosition;

/*
 * Interface for the general chess piece.
 */
public interface IPiece {

    String getName();
    Color getColor();
    int getMoveCount();
    int getSlayCount();
    boolean isValidMove(BoardPosition from, BoardPosition to);

    enum Color {
        WHITE, BLACK
    }

}