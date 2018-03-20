package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.score = Utils.ROOK_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
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
    }

    private void populateNorthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        for (int i = (xPiece - 1); i >= 0; i--) {
            //-x, 0
            int x = i;
            int y = piecePos.y + yPiece;

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
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("NORTH: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateEastMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int y = (yPiece + 1); y <= 7; y++) {
            // 0, +y
            int x = piecePos.x + xPiece;

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
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("EAST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateSouthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        for (int i = (xPiece + 1); i <= 7; i++) {
            //+x, 0
            int x = i;
            int y = piecePos.y + yPiece;

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
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("SOUTH: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

    private void populateWestMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int y = (yPiece - 1); y >= 0; y--) {
            // 0, -y
            int x = piecePos.x + xPiece;

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
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }

            //System.out.println("WEST: Move (" + move.x + ", " + move.y + ") Position after move: (" + posAfterMove.x + ", " + posAfterMove.y + ")");
        }
    }

}