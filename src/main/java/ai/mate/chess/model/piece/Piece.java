package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.move.MoveHistory;
import ai.mate.chess.model.move.NormalMove;
import ai.mate.chess.model.move.SlayMove;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Piece {

    public static final int BISHOP_SCORE = 30;
    public static final int ROOK_SCORE = 50;
    public static final int QUEEN_SCORE = 90;
    public static final int PAWN_SCORE = 1;
    public static final int KNIGHT_SCORE = 30;
    public static final int KING_SCORE = 900;

    protected final String name;
    protected final PlayerColor color;
    protected final PieceType type;
    protected Point position;

    protected final int score;
    protected int moveCount;
    protected int slayCount;

    public enum PlayerColor {
        WHITE, BLACK
    }

    public enum PieceType {
        BISHOP, KING, KNIGHT, PAWN, QUEEN, ROOK
    }

    public Piece(PlayerColor color, Point position) {
        this.name = initName();
        this.color = color;
        this.type = initPieceType();
        this.position = position;
        this.score = initScore();
        this.moveCount = 0;
        this.slayCount = 0;
    }

    public void updatePiece(Move move) {
        if (type == PieceType.KING)
            GameManager.getInstance().updateKingPosition(this);

        if (move.getType() == Move.MoveType.ATTACK)
            slayCount++;

        moveCount++;

        MoveHistory.getInstance().addMove(move);
        position = move.getEnd();
    }

    public List<Move> getMovesInLine(Board board, int[][] directionOffsets) {
        List<Move> movesInLine = new ArrayList<>();

        for (int[] offset : directionOffsets) {
            int offsetX = offset[0];
            int offsetY = offset[1];

            for (int i = 1; i < Board.BOARD_SIZE; i++) {
                Point possiblePosition = new Point(position.x + (i * offsetX), position.y + (i * offsetY));

                if (board.isValidPosition(possiblePosition)) {
                    Tile possibleTile = board.getTile(possiblePosition);

                    if (possibleTile.isEmpty()) {
                        movesInLine.add(createNormalMove(possiblePosition));
                    } else {
                        if (!isSameColor(possibleTile.getPiece())) {
                            movesInLine.add(createAttackMove(possiblePosition));
                        }
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return movesInLine;
    }

    public abstract int[][] getPositionTable();

    public abstract boolean[] getPositionThreats();

    public abstract List<Move> getAvailableMoves(Board board);

    public boolean isSameColor(Piece piece) {
        return color == piece.color;
    }

    public Move createAttackMove(Point end) {
        return new SlayMove(position, end);
    }

    public Move createNormalMove(Point end) {
        return new NormalMove(position, end);
    }

    public Move createNormalMove(Point end, Move.MoveType type) {
        return new NormalMove(position, end, type);
    }

    public PlayerColor getColor() {
        return color;
    }

    public PieceType getType() {
        return type;
    }

    public Point getPosition() {
        return position;
    }

    public int getScore() {
        return score;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public int getSlayCount() {
        return slayCount;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    private String initName() {
        switch (type) {
            case BISHOP:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_BISHOP : TextHandler.BLACK_BISHOP;
            case KING:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_KING : TextHandler.BLACK_KING;
            case KNIGHT:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_KNIGHT : TextHandler.BLACK_KNIGHT;
            case PAWN:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_PAWN : TextHandler.BLACK_PAWN;
            case QUEEN:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_QUEEN : TextHandler.BLACK_QUEEN;
            case ROOK:
                return (color == PlayerColor.WHITE) ? TextHandler.WHITE_ROOK : TextHandler.BLACK_ROOK;
            default:
                return "ERROR: initName";
        }
    }

    private int initScore() {
        switch (type) {
            case BISHOP:
                return BISHOP_SCORE;
            case KING:
                return KING_SCORE;
            case KNIGHT:
                return KNIGHT_SCORE;
            case PAWN:
                return PAWN_SCORE;
            case QUEEN:
                return QUEEN_SCORE;
            case ROOK:
                return ROOK_SCORE;
            default:
                return 0;
        }
    }

    private PieceType initPieceType() {
        if (this instanceof Bishop) {
            return PieceType.BISHOP;
        } else if (this instanceof King) {
            return PieceType.KING;
        } else if (this instanceof Pawn) {
            return PieceType.PAWN;
        } else if (this instanceof Queen) {
            return PieceType.QUEEN;
        } else if (this instanceof Rook) {
            return PieceType.ROOK;
        } else {
            return PieceType.PAWN;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Piece && color == ((Piece) obj).color
                && type == ((Piece) obj).type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this);
    }

    public abstract Piece copy();

}