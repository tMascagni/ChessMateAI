package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;

import java.awt.*;
import java.util.List;

public final class Queen extends Piece {

    public Queen(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        int[][] directionOffsets = {
                {0, 1},   // Up
                {0, -1},  // Down
                {1, 0},   // Left
                {-1, 0},  // Right
                {1, 1},   // diagUpRight
                {-1, -1}, // diagDownLeft
                {1, -1},  // diagDownRight
                {-1, 1}   // diagUpLeft
        };

        /* Get all the available moves */
        List<Move> availableMoves = getMovesInLine(board, directionOffsets);

        return availableMoves;
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {-20, -10, -10, -5, -5, -10, -10, -20},
                {-10, 0, 0, 0, 0, 0, 0, -10},
                {-10, 0, 5, 5, 5, 5, 0, -10},
                {-5, 0, 5, 5, 5, 5, 0, -5},
                {0, 0, 5, 5, 5, 5, 0, -5},
                {-10, 5, 5, 5, 5, 5, 0, -10},
                {-10, 0, 5, 0, 0, 0, 0, -10},
                {-20, -10, -10, -5, -5, -10, -10, -20}
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{true, true, true, true, true, true, true, true};
    }

    @Override
    public Piece copy() {
        return new Queen(getPlayerColor(), new Point(getPosition()));
    }

}