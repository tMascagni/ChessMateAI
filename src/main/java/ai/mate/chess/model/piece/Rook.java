package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.board.BoardOld;
import ai.mate.chess.model.board.BoardPosition;

import java.awt.*;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.score = ROOK_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
    }

    @Override
    public void populateMoves(BoardOld boardOld) {
        /* Remove old possible moves and slay moves */
        resetMoves();

        /* Firstly, we need to get this piece's boardOld position */
        BoardPosition pieceBoardPos = boardOld.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.rowX, pieceBoardPos.colY);

        populateNorthMoves(piecePos, boardOld);
        populateSouthMoves(piecePos, boardOld);
        populateEastMoves(piecePos, boardOld);
        populateWestMoves(piecePos, boardOld);
    }

    private void populateNorthMoves(Point piecePos, BoardOld boardOld) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        for (int rowX = xPiece - 1; rowX >= 0; rowX--) {
            //-x, 0
            int colY = piecePos.y + yPiece;

            if (isOutOfBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (boardOld.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (boardOld.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* add to slay list */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateEastMoves(Point piecePos, BoardOld boardOld) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int colY = yPiece + 1; colY <= 7; colY++) {
            // 0, +y
            int rowX = piecePos.x + xPiece;

            if (isOutOfBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (boardOld.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (boardOld.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* add to slay list */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateSouthMoves(Point piecePos, BoardOld boardOld) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        for (int rowX = xPiece + 1; rowX <= 7; rowX++) {
            //+x, 0
            int colY = piecePos.y + yPiece;

            if (isOutOfBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (boardOld.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (boardOld.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals((Piece.getOpponentColor(getColor())))) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* add to slay list */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateWestMoves(Point piecePos, BoardOld boardOld) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int colY = yPiece - 1; colY >= 0; colY--) {
            // 0, -y
            int rowX = piecePos.x + xPiece;

            if (isOutOfBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (boardOld.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (boardOld.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                /* add to slay list */
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

}