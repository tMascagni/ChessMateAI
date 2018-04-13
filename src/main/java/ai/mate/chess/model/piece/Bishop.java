package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.List;

public final class Bishop extends Piece {

    public Bishop(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        int[][] directionOffsets = {
                {1, 1},   // Upper right
                {-1, -1}, // Bottom left
                {1, -1},  // Bottom right
                {-1, 1}   // Upper left
        };

        /* Get all the available moves */
        List<Move> availableMoves = getMovesInLine(board, directionOffsets);

        return availableMoves;
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {-20, -10, -10, -10, -10, -10, -10, -20},
                {-10, 0, 0, 0, 0, 0, 0, -10},
                {-10, 0, 5, 10, 10, 5, 0, -10},
                {-10, 5, 5, 10, 10, 5, 5, -10},
                {-10, 0, 10, 10, 10, 10, 0, -10},
                {-10, 10, 10, 10, 10, 10, 10, -10},
                {-10, 5, 0, 0, 0, 0, 5, -10},
                {-20, -10, -10, -10, -10, -10, -10, -20}
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{true, false, true, false, false, true, false, true};
    }

    @Override
    public double getScore(Board board) {
        return ChessUtils.BISHOP_SCORE + 2.0 * getAvailableMoves(board).size();
    }

    @Override
    public Piece copy() {
        return new Bishop(getPlayerColor(), new Point(getPosition()));
    }

}