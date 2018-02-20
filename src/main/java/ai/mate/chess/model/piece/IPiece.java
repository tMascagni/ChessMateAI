package ai.mate.chess.model.piece;

import java.awt.*;

/*
 * Interface for the general chess piece.
 */
public interface IPiece {

    String getName();
    Color getColor();
    int getMoveCount();
    int getSlayCount();

    enum Color {
        WHITE, BLACK
    }

}