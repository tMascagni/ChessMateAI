package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.List;

public final class Queen extends Piece {

    @Override
    public int getRank() {
        return ChessUtils.QUEEN_SCORE;
    }

    public Queen(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        threatenedPieces.clear();

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

        return cleanAvailableMoves(availableMoves, board);
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{true, true, true, true, true, true, true, true};
    }

    @Override
    public double getScore(Board board) {
        return ChessUtils.QUEEN_SCORE + 1.0 * getAvailableMoves(board).size();
    }

    @Override
    public Piece copy() {
        return new Queen(getPlayerColor(), new Point(getPosition()));
    }

}