package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
        this.score = Utils.BISHOP_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_BISHOP;
        else
            name = TextHandler.BLACK_BISHOP;
    }

    @Override
    public void populateMoves(Board board) {
        /* Remove old possible moves and slay moves */
        resetMoves();

        /* Firstly, we need to get this piece's board position */
        BoardPosition pieceBoardPos = board.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.rowX, pieceBoardPos.colY);

        populateNorthEastMoves(piecePos, board);
        populateSouthEastMoves(piecePos, board);
        populateSouthWestMoves(piecePos, board);
        populateNorthWestMoves(piecePos, board);
    }

    private void populateNorthEastMoves(Point piecePos, Board board) {
        // x-, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece;

        for (int colY = yPiece + 1; colY <= 7; colY++) {
            rowX--;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                addToKillMoves(board, posAfterMove.x, posAfterMove.y);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateSouthEastMoves(Point piecePos, Board board) {
        // x+, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece;

        for (int colY = yPiece + 1; colY <= 7; colY++) {
            rowX++;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                addToKillMoves(board, posAfterMove.x, posAfterMove.y);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateSouthWestMoves(Point piecePos, Board board) {
        // x+, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece;

        for (int colY = yPiece - 1; colY >= 0; colY--) {
            rowX++;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                addToKillMoves(board, posAfterMove.x, posAfterMove.y);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateNorthWestMoves(Point piecePos, Board board) {
        // x-, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece;

        for (int colY = yPiece - 1; colY >= 0; colY--) {
            rowX--;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                addToKillMoves(board, posAfterMove.x, posAfterMove.y);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void addToKillMoves(Board board, int xAfterMove, int yAfterMove) {
        slayMoves.add(board.getPiece(xAfterMove, yAfterMove));
    }

}