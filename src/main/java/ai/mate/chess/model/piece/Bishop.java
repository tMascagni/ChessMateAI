package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;

import java.awt.*;
import java.util.List;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(PlayerColor color, Point position) {
        super(color, position);
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][] {
                { -20,-10,-10,-10,-10,-10,-10,-20 },
                { -10,  0,  0,  0,  0,  0,  0,-10 },
                { -10,  0,  5, 10, 10,  5,  0,-10 },
                { -10,  5,  5, 10, 10,  5,  5,-10 },
                { -10,  0, 10, 10, 10, 10,  0,-10 },
                { -10, 10, 10, 10, 10, 10, 10,-10 },
                { -10,  5,  0,  0,  0,  0,  5,-10 },
                { -20,-10,-10,-10,-10,-10,-10,-20 }
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{ true, false, true, false, false, true, false, true };
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        int[][] directionOffsets = {
                {1, 1}, // Upper right
                {-1, -1}, // Bottom left
                {1, -1}, // Bottom right
                {-1, 1} // Upper left
        };

        return getMovesInLine(board, directionOffsets);
    }

    @Override
    public Piece copy() {
        return new Bishop(color, new Point(position));
    }

}