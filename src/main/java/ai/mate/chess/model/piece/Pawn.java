package ai.mate.chess.model.piece;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.*;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Pawn extends Piece {

    private Point startPosition;
    private int direction;

    int[] pawnRow = new int[] {0,0,-1,0,2,14,30,0};
    int[] pawnLine = new int[] {-2,0,3,4,5,1,-2,-2};

    @Override
    public int getRank() {
        return ChessUtils.PAWN_SCORE;
    }

    public Pawn(PlayerColor playerColor, Point position) {
        super(playerColor, position);
        this.startPosition = new Point(position);
        this.direction = getPlayerColor() == PlayerColor.WHITE ? -1 : 1;
    }

    @Override
    public List<Move> getAvailableMoves(Board board) {
        threatenedPieces.clear();

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
                    if (!isSameTeam(diagonalTile.getPiece())) {
                        moves.add(createAttackMove(diagonalTile.getPosition()));
                        threatenedPieces.add(board.getTile(diagonalTile.getPosition()).getPiece());
                    }
                } else {
                    // En passant moves
                    if (currentPos.y == startPosition.y + getNormalized(3)) {
                        Tile sideTile = board.getTile(diagPos.x, diagPos.y + getNormalized(-1));
                        if (!sideTile.isEmpty()) {
                            Piece sidePiece = sideTile.getPiece();
                            if (isSameType(sidePiece) && !isSameTeam(sidePiece)) {
                                Move lastMove = MoveHistory.getInstance().popMove();
                                Point lastPos = lastMove.getTo();
                                Point sidePos = sideTile.getPosition();
                                if (lastMove.getMoveType() == Move.MoveType.NORMAL_DOUBLE && sidePos.equals(lastPos))
                                    moves.add(createEnPassantMove(diagPos));
                            }
                        }
                    }
                }
            }
        }

        for (int i = 0; i < moves.size(); i++) {
            Move move = moves.get(i);
            if (move.getTo().y == 7 || move.getTo().y == 0)
                moves.set(i, new PawnPromotionMove(currentPos, move.getTo()));
        }

        return moves;
    }

    @Override
    public boolean[] getPositionThreats() {
        boolean diagonal = getPlayerColor() == PlayerColor.WHITE;
        return new boolean[]{!diagonal, false, !diagonal, false, false, diagonal, false, diagonal};
    }

    @Override
    public double getScore(Board board) {
        int row;
        int line;
        if(getPlayerColor() == PlayerColor.WHITE){
            row = 7-getPosition().y;
            line = 7-getPosition().x;

        } else {
            row = getPosition().y;
            line = getPosition().x;
        }
        return ChessUtils.PAWN_SCORE + pawnRow[row] + pawnLine[line] * row / 2;
        //TODO mangler "dobbeltbonde" (-8)
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