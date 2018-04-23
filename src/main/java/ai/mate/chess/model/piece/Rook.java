package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.List;

public final class Rook extends Piece {

    @Override
    public int getRank() {
        return ChessUtils.ROOK_SCORE;
    }

    public Rook(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        threatenedPieces.clear();

        int[][] directionOffsets = {
                {0, 1},  // Up
                {0, -1}, // Down
                {1, 0},  // Left
                {-1, 0}  // Right
        };

        /* Get all the available moves */
        List<Move> availableMoves = getMovesInLine(board, directionOffsets);

        return cleanAvailableMoves(availableMoves, board);
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{false, true, false, true, true, false, true, false};
    }

    @Override
    public double getScore(Board board) {
        return ChessUtils.ROOK_SCORE + 1.5 * getAvailableMoves(board).size();
    }

    @Override
    public Piece copy() {
        return new Rook(getPlayerColor(), new Point(getPosition()));
    }

}