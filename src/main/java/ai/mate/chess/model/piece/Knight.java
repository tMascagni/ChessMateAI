package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {

    public Knight(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        List<Move> availableMoves = new ArrayList<>();

        Point[] possibleMoves = {
                generatePos(-1, -2),
                generatePos(1, -2),
                generatePos(-1, 2),
                generatePos(1, 2),
                generatePos(-2, -1),
                generatePos(2, -1),
                generatePos(-2, 1),
                generatePos(2, 1),
        };

        for (Point possibleMove : possibleMoves) {
            if (board.isValidPosition(possibleMove)) {
                Tile possibleTile = board.getTile(possibleMove);
                if (possibleTile.isEmpty())
                    availableMoves.add(createNormalMove(possibleMove));
                else if (!isSameTeam(possibleTile.getPiece()))
                    availableMoves.add(createAttackMove(possibleMove));
            }
        }

        return availableMoves;
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {-50, -40, -30, -30, -30, -30, -40, -50},
                {-40, -20, 0, 0, 0, 0, -20, -40},
                {-30, 0, 10, 15, 15, 10, 0, -30},
                {-30, 5, 15, 20, 20, 15, 5, -30},
                {-30, 0, 15, 20, 20, 15, 0, -30},
                {-30, 5, 10, 15, 15, 10, 5, -30},
                {-40, -20, 0, 5, 5, 0, -20, -40},
                {-50, -40, -30, -30, -30, -30, -40, -50}
        };
    }

    /*
     * Unused for knight
     */
    @Override
    public boolean[] getPositionThreats() {
        return new boolean[0];
    }

    @Override
    public double getScore(Board board) {
        return ChessUtils.KNIGHT_SCORE;
        //TODO 300 + 3.0 * (4 - afstand til centrum?)
    }

    @Override
    public Piece copy() {
        return new Knight(getPlayerColor(), new Point(getPosition()));
    }

    private Point generatePos(int x, int y) {
        Point currPos = getPosition();
        return new Point(currPos.x + x, currPos.y + y);
    }

}