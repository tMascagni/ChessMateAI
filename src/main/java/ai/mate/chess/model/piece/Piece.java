package ai.mate.chess.model.piece;

import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.*;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    private final PlayerColor playerColor;
    private final PieceType pieceType;
    private Point position;

    private final String name;
    private final int score;
    private int moveCount;
    private int attackCount;

    public enum PlayerColor {
        WHITE, BLACK
    }

    public enum PieceType {
        BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK, NONE
    }

    public Piece(PlayerColor playerColor, Point position) {
        this.playerColor = playerColor;
        this.pieceType = initPieceType();
        this.position = position;
        this.name = initName();
        this.score = initScore();
        this.moveCount = 0;
        this.attackCount = 0;
    }

    public void updatePiece(Move move) {
        if (pieceType == PieceType.KING)
            GameController.getInstance().updateKingPosition(this);

        moveCount++;
        position = move.getTo();
        MoveHistory.getInstance().addMove(move);
    }

    /**
     * Finds the moves in line, useful for queens, rooks and bishops
     *
     * @param board            board to search
     * @param directionOffsets array of x, y pairs to search (-1, -1) for bottom diag etc
     * @return
     */
    public List<Move> getMovesInLine(Board board, int[][] directionOffsets) {
        List<Move> movesInLine = new ArrayList<>();

        for (int[] offset : directionOffsets) {
            int offsetX = offset[0];
            int offsetY = offset[1];

            for (int i = 1; i < 8; i++) {
                Point possiblePos = new Point(getPosition().x + (i * offsetX), getPosition().y + (i * offsetY));
                if (board.isValidPosition(possiblePos)) {
                    Tile possibleTile = board.getTile(possiblePos);
                    if (possibleTile.isEmpty()) {
                        movesInLine.add(createNormalMove(possiblePos));
                    } else {
                        if (!isSameTeam(possibleTile.getPiece()))
                            movesInLine.add(createAttackMove(possiblePos));
                        break;
                    }
                } else {
                    break;
                }
            }
        }

        return movesInLine;
    }

    public abstract List<Move> getAvailableMoves(Board board);

    public abstract int[][] getPositionTable();

    public abstract boolean[] getPositionThreats();

    public boolean isSameTeam(Piece piece) {
        return this.getPlayerColor() == piece.getPlayerColor();
    }

    public Move createAttackMove(Point end) {
        return new AttackMove(this.position, end);
    }

    public Move createNormalMove(Point end) {
        return new NormalMove(this.position, end);
    }

    public Move createNormalMove(Point end, Move.MoveType moveType) {
        return new NormalMove(this.position, end, moveType);
    }

    public PlayerColor getPlayerColor() {
        return this.playerColor;
    }

    public PieceType getPieceType() {
        return this.pieceType;
    }

    public Point getPosition() {
        return this.position;
    }

    public int getScore() {
        return this.score;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getAttackCount() {
        return attackCount;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece && this.playerColor ==
                ((Piece) obj).playerColor && this.pieceType == ((Piece) obj).pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }

    private PieceType initPieceType() {
        if (this instanceof Bishop)
            return PieceType.BISHOP;
        else if (this instanceof King)
            return PieceType.KING;
        else if (this instanceof Knight)
            return PieceType.KNIGHT;
        else if (this instanceof Pawn)
            return PieceType.PAWN;
        else if (this instanceof Queen)
            return PieceType.QUEEN;
        else if (this instanceof Rook)
            return PieceType.ROOK;
        else
            return PieceType.NONE;
    }

    private String initName() {
        switch (pieceType) {
            case BISHOP:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_BISHOP : ChessUtils.BLACK_BISHOP;
            case KING:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_KING : ChessUtils.BLACK_KING;
            case KNIGHT:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_KNIGHT : ChessUtils.BLACK_KNIGHT;
            case PAWN:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_PAWN : ChessUtils.BLACK_PAWN;
            case QUEEN:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_QUEEN : ChessUtils.BLACK_QUEEN;
            case ROOK:
                return playerColor == PlayerColor.WHITE ? ChessUtils.WHITE_ROOK : ChessUtils.BLACK_ROOK;
            default:
                return ChessUtils.ERROR_NAME;
        }
    }

    private int initScore() {
        switch (pieceType) {
            case BISHOP:
                return ChessUtils.BISHOP_SCORE;
            case KING:
                return ChessUtils.KING_SCORE;
            case KNIGHT:
                return ChessUtils.KNIGHT_SCORE;
            case PAWN:
                return ChessUtils.PAWN_SCORE;
            case QUEEN:
                return ChessUtils.QUEEN_SCORE;
            case ROOK:
                return ChessUtils.ROOK_SCORE;
            default:
                return ChessUtils.DEFAULT_SCORE;
        }
    }

    public abstract Piece copy();

}