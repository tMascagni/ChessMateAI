package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;

import java.awt.*;
import java.util.List;

public final class Rook extends Piece {

    public Rook(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        int[][] directionOffsets = {
                {0, 1},  // Up
                {0, -1}, // Down
                {1, 0},  // Left
                {-1, 0}  // Right
        };

        return getMovesInLine(board, directionOffsets);
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {5, 10, 10, 10, 10, 10, 10, 5},
                {-5, 0, 0, 0, 0, 0, 0, -5},
                {-5, 0, 0, 0, 0, 0, 0, -5},
                {-5, 0, 0, 0, 0, 0, 0, -5},
                {-5, 0, 0, 0, 0, 0, 0, -5},
                {-5, 0, 0, 0, 0, 0, 0, -5},
                {0, 0, 0, 5, 5, 0, 0, 0}
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{false, true, false, true, true, false, true, false};
    }

    @Override
    public Piece copy() {
        return new Rook(getPlayerColor(), new Point(getPosition()));
    }

}