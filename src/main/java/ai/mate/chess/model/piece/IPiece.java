package ai.mate.chess.model.piece;

import ai.mate.chess.model.BoardPosition;

/*
 * Interface for the general chess piece.
 */
public interface IPiece {
    void incMoveCount();
    void incSlayCount();
    boolean isValidMove(BoardPosition from, BoardPosition to, IPiece[][] board);
    int calculateDeltaX(int fromX, int toX);
    int calculateDeltaY(int fromY, int toY);
    String getName();
    Color getColor();
    int getMoveCount();
    int getSlayCount();

    enum Color {
        WHITE, BLACK
    }

}