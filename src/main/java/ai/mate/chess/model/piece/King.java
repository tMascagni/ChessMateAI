package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Konge
 */
public final class King extends Piece {

    public King(Color color) {
        super(color);
        this.score = Utils.KING_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KING;
        else
            name = TextHandler.BLACK_KING;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();

        /* Firstly, we need to get this piece's board position */
        BoardPosition pieceBoardPos = board.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.arrayX, pieceBoardPos.arrayY);

        populateNorthMoves(piecePos, board);
        populateSouthMoves(piecePos, board);
        populateEastMoves(piecePos, board);
        populateWestMoves(piecePos, board);
        populateNorthEastMoves(piecePos, board);
        populateSouthEastMoves(piecePos, board);
        populateSouthWestMoves(piecePos, board);
        populateNorthWestMoves(piecePos, board);
    }

    private void populateNorthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        //-x, 0
        int x = xPiece - 1;
        int y = piecePos.y + yPiece;

        if (x < 0)
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */

        }

        //System.out.println("NORTH: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");

    }

    private void populateSouthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        //+x, 0
        int x = xPiece + 1;
        int y = piecePos.y + yPiece;

        if (x > 7)
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("SOUTH: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");

    }

    private void populateEastMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        // 0, +y
        int x = piecePos.x + xPiece;
        int y = yPiece + 1;

        if ((x < 0 || x > 7) || (y < 0 || y > 7))
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

    private void populateWestMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        // 0, -y
        int x = piecePos.x + xPiece;
        int y = yPiece - 1;

        if ((x < 0 || x > 7) || (y < 0 || y > 7))
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

    private void populateNorthEastMoves(Point piecePos, Board board) {
        // x-, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece - 1;
        int y = yPiece + 1;

        if (x < 0) return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("NORTH EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

    private void populateSouthEastMoves(Point piecePos, Board board) {
        // x+, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece + 1;
        int y = (yPiece + 1);

        if (x > 7)
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("SOUTH EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

    private void populateSouthWestMoves(Point piecePos, Board board) {
        // x+, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece + 1;
        int y = (yPiece - 1);

        if (x > 7) return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("SOUTH WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

    private void populateNorthWestMoves(Point piecePos, Board board) {
        // x-, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int x = xPiece - 1;
        int y = (yPiece - 1);
        if (x < 0)
            return;

        Point move = calculateDeltaMove(piecePos, new Point(x, y));
        Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

        if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
            /* legal move! */
            possibleMoves.add(move);
        } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(getOpponentColor())) {
            /* legal slay move! */
            possibleMoves.add(move);
            /* but nothing more */
        } else {
            /* Can't walk onto own pieces! */
        }

        //System.out.println("NORTH WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
    }

}