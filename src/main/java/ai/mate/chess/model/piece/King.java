package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.CastleMove;
import ai.mate.chess.model.move.Move;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class King extends Piece {

    private Point startPosition;

    public King(PlayerColor playerColor, Point position) {
        super(playerColor, position);
        this.startPosition = position;
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        List<Move> availableMoves = new ArrayList<>();

        // Regular & attacking moves
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Point possiblePos = new Point(getPosition().x + i, getPosition().y + j);
                if (!getPosition().equals(possiblePos)) {
                    if (board.isValidPosition(possiblePos)) {
                        Tile possibleTile = board.getTile(possiblePos);
                        if (possibleTile.isEmpty()) {
                            availableMoves.add(createNormalMove(possiblePos));
                        } else {
                            if (!isSameTeam(possibleTile.getPiece()))
                                availableMoves.add(createAttackMove(possiblePos));
                        }
                    }
                }
            }
        }

        // Castling
        if (this.getPosition() == this.startPosition && getMoveCount() == 0) {
            int x = this.getPosition().x;
            int y = this.getPosition().y;

            for (int direction = -1; direction <= 1; direction += 2) {
                int left = x + direction;
                int right = x + (2 * direction);

                if (left < 0 || right < 0 || right > 7 || left > 7)
                    break;

                if (board.getTile(left, y).isEmpty() && board.getTile(right, y).isEmpty()) {
                    int potentialX = direction < 0 ? 0 : 7;
                    Tile potentialRook = board.getTile(new Point(potentialX, y));
                    if (!potentialRook.isEmpty() && potentialRook.getPiece().getPieceType() == PieceType.ROOK) {
                        Piece rook = potentialRook.getPiece();
                        if (rook.getMoveCount() == 0)
                            availableMoves.add(createCastleMove(new Point(x + (2 * direction), y)));
                    }
                }
            }
        }

        return availableMoves;
    }

    @Override
    public int[][] getPositionTable() {
        return new int[][]{
                {-30, -40, -40, -50, -50, -40, -40, -30},
                {-30, -40, -40, -50, -50, -40, -40, -30},
                {-30, -40, -40, -50, -50, -40, -40, -30},
                {-30, -40, -40, -50, -50, -40, -40, -30},
                {-20, -30, -30, -40, -40, -30, -30, -20},
                {-10, -20, -20, -20, -20, -20, -20, -10},
                {20, 20, 0, 0, 0, 0, 20, 20},
                {20, 30, 10, 0, 0, 10, 30, 20}
        };
    }

    @Override
    public boolean[] getPositionThreats() {
        return new boolean[]{true, true, true, true, true, true, true, true};
    }

    @Override
    public Piece copy() {
        return new King(this.getPlayerColor(), new Point(this.getPosition()));
    }

    public Point getStartPosition() {
        return startPosition;
    }

    private Move createCastleMove(Point end) {
        return new CastleMove(startPosition, end);
    }

}