package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {

    private Point startPosition;
    private int direction;

    public Pawn(PlayerColor playerColor, Point position) {
        super(playerColor, position);
        this.startPosition = new Point(position);
        this.direction = getPlayerColor() == PlayerColor.WHITE ? -1 : 1;
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        Point currentPos = getPosition();
        Point singleMove = new Point(currentPos.x, currentPos.y + getNormalized(1));

        // Regular moves
        if (board.isValidPosition(singleMove) && board.getTile(singleMove).isEmpty()) {
            moves.add(createNormalMove(singleMove));

            Point doubleMove = new Point(currentPos.x, currentPos.y + getNormalized(2));
            if (board.isValidPosition(doubleMove) && board.getTile(doubleMove).isEmpty() && getMoveCount() == 0)
                moves.add(createNormalMove(doubleMove, Move.MoveType.NORMAL_DOUBLE));
        }

        Point[] diagPositions = {
                new Point(currentPos.x - 1, currentPos.y + getNormalized(1)),
                new Point(currentPos.x + 1, currentPos.y + getNormalized(1))
        };

        for (Point diagPos : diagPositions) {
            if (board.isValidPosition(diagPos)) {
                // Attack moves
                Tile diagonalTile = board.getTile(diagPos);
                if (!diagonalTile.isEmpty()) {
                    if (!isSameTeam(diagonalTile.getPiece()))
                        moves.add(createAttackMove(diagonalTile.getPosition()));
                } else {
                    // En passant moves
                    if (currentPos.y == startPosition.y + getNormalized(3)) {
                        Tile sideTile = board.getTile(diagPos.x, diagPos.y + getNormalized(-1));
                        if (!sideTile.isEmpty()) {
                            Piece sidePiece = sideTile.getPiece();
                            if (isSameType(sidePiece) && !isSameTeam(sidePiece)) {
                                Move lastMove = MoveHistory.getInstance().popLastMove();
                                Point lastPos = lastMove.getEnd();
                                Point sidePos = sideTile.getPosition();
                                if (lastMove.getType() == Move.MoveType.NORMAL_DOUBLE && sidePos.equals(lastPos))
                                    moves.add(createEnPassantMove(diagPos));
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            if (move.getEnd().y == 7 || move.getEnd().y == 0)
                moves.set(i, new PawnPromotionMove(currentPos, move.getEnd()));
        }

        return moves;
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0},
                {50, 50, 50, 50, 50, 50, 50, 50},
                {10, 10, 20, 30, 30, 20, 10, 10},
                {5, 5, 10, 25, 25, 10, 5, 5},
                {0, 0, 0, 20, 20, 0, 0, 0},
                {5, -5, -10, 0, 0, -10, -5, 5},
                {5, 10, 10, -20, -20, 10, 10, 5},
                {0, 0, 0, 0, 0, 0, 0, 0}
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        boolean diagonal = getPlayerColor() == PlayerColor.WHITE;
        return new boolean[]{!diagonal, false, !diagonal, false, false, diagonal, false, diagonal};
    }

    @Override
    public Piece copy() {
        return new Pawn(getPlayerColor(), new Point(getPosition()));
    }

    public boolean promotePawn() {
        return getPlayerColor() == PlayerColor.WHITE && getPosition().y == 0 ||
                getPlayerColor() == PlayerColor.BLACK && getPosition().y == 7;
    }

    private Move createEnPassantMove(Point target) {
        return new EnPassantMove(getPosition(), target.getLocation());
    }

    private boolean isSameType(Piece piece) {
        return this.getPieceType() == piece.getPieceType();
    }

    private int getNormalized(int value) {
        return value * direction;
    }

}