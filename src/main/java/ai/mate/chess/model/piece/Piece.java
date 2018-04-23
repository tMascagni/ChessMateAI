package ai.mate.chess.model.piece;

import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.AttackMove;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.move.MoveHistory;
import ai.mate.chess.model.move.NormalMove;
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
    private int moveCount;
    private int attackCount;

    protected List<Piece> threatenedPieces = new ArrayList<>();

    public boolean threatensHigherRank(Board board) {
        List<Move> moves = getAvailableMoves(board);
        for (Move move : moves) {
            if(move instanceof AttackMove && board.getTile(move.getTo()).getPiece().getRank() > this.getRank()) {
                return true;
            }
        }
        return false;
    }

    public abstract int getRank();

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
        threatenedPieces.clear();

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
                        if (!isSameTeam(possibleTile.getPiece())) {
                            // ATTACK MOVE HERE
                            threatenedPieces.add(board.getTile(possiblePos).getPiece());
                            Move attMove = createAttackMove(possiblePos);
                            movesInLine.add(attMove);
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

    public abstract List<Move> getAvailableMoves(Board board);

    public abstract boolean[] getPositionThreats();

    public boolean isSameTeam(Piece piece) {
        return this.getPlayerColor() == piece.getPlayerColor();
    }

    public Move createAttackMove(Point end) {
        return new AttackMove(position, end);
    }

    public Move createNormalMove(Point end) {
        return new NormalMove(position, end);
    }

    public Move createNormalMove(Point end, Move.MoveType moveType) {
        return new NormalMove(position, end, moveType);
    }

    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Point getPosition() {
        return position;
    }

    /**
     * Returns the piece evaluation score, by the Kaare Danielsen model.
     *
     * @param board
     * @return
     */
    public abstract double getScore(Board board);

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

    public abstract Piece copy();


    protected List<Move> cleanAvailableMoves(List<Move> availableMoves, Board board) {
        /*
         * Iterate through all available moves,
         * check every single on of them,
         * if after they've been handled,
         * if our (same playerColor)'s King
         * is in check.
         *
         * If the king is in check after the move, then
         * we know that it is not a legal move.
         */
        List<Move> cleanMoves = new ArrayList<>();

        for (Move move : availableMoves) {
            Board copyBoard = new Board(board);
            // move has now been done on the board
            copyBoard.handleMove(move);
            // check if our King is in check
            boolean isInCheck = GameController.getInstance().isInCheck(getPlayerColor());
            if (!isInCheck) cleanMoves.add(move);
            // we undo the move on the board.
        }

        return cleanMoves;
    }

    public List<Piece> getThreatenedPieces() {
        return threatenedPieces;
    }

}