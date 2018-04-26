package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Knight extends Piece {

    @Override
    public int getRank() {
        return ChessUtils.KNIGHT_SCORE;
    }

    public Knight(PlayerColor playerColor, Point position) {
        super(playerColor, position);
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        threatenedPieces.clear();

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
                if (possibleTile.isEmpty()) {
                    availableMoves.add(createNormalMove(possibleMove));
                } else if (!isSameTeam(possibleTile.getPiece())) {
                    availableMoves.add(createAttackMove(possibleMove));
                    threatenedPieces.add(possibleTile.getPiece());
                }
            }
        }

        return availableMoves;
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
        Point position = this.getPosition();
        double distanceToCenter;
        if(position.getX() > 3 && position.getY() > 3) {
            distanceToCenter = position.distance(4,4);
        } else if(position.getX() > 3) {
            distanceToCenter = position.distance(4,3);
        } else if(position.getY() > 3) {
            distanceToCenter = position.distance(3,4);
        } else {
            distanceToCenter = position.distance(3,3);
        }
        return ChessUtils.KNIGHT_SCORE + 3.0 * (4 - distanceToCenter);
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