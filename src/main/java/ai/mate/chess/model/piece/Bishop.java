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
        possibleMoves.clear();

        /* Firstly, we need to get this piece's board position */
        BoardPosition pieceBoardPos = board.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.arrayX, pieceBoardPos.arrayY);

        populateNorthEastMoves(piecePos, board);
        populateSouthEastMoves(piecePos, board);
        populateSouthWestMoves(piecePos, board);
        populateNorthWestMoves(piecePos, board);
    }

    private void populateNorthEastMoves(Point piecePos, Board board) {
        // x-, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece;

        for (int y = (yPiece + 1); y <= 7; y++) {
            x--;
            if (x < 0) break;

            Point move = calculateDeltaMove(piecePos, new Point(x, y));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* but nothing more */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("NORTH EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateSouthEastMoves(Point piecePos, Board board) {
        // x+, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece;

        for (int y = (yPiece + 1); y <= 7; y++) {
            x++;
            if (x > 7) break;

            Point move = calculateDeltaMove(piecePos, new Point(x, y));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* but nothing more */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("SOUTH EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateSouthWestMoves(Point piecePos, Board board) {
        // x+, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece;

        for (int y = (yPiece - 1); y >= 0; y--) {
            x++;
            if (x > 7) break;

            Point move = calculateDeltaMove(piecePos, new Point(x, y));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* but nothing more */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("SOUTH WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateNorthWestMoves(Point piecePos, Board board) {
        // x-, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece;

        for (int y = (yPiece - 1); y >= 0; y--) {
            x--;
            if (x < 0) break;

            Point move = calculateDeltaMove(piecePos, new Point(x, y));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* but nothing more */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("NORTH WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

}