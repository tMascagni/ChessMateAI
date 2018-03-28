package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.score = Utils.QUEEN_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_QUEEN;
        else
            name = TextHandler.BLACK_QUEEN;
    }

    @Override
    public void populateMoves(Board board) {
        /* Remove old possible moves and slay moves */
        resetMoves();

        /* Firstly, we need to get this piece's board position */
        BoardPosition pieceBoardPos = board.getPieceBoardPos(this.ID);
        Point piecePos = new Point(pieceBoardPos.rowX, pieceBoardPos.colY);

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

        for (int rowX = xPiece - 1; rowX >= 0; rowX--) {
            //-x, 0
            int colY = piecePos.y + yPiece;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateSouthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        for (int rowX = xPiece + 1; rowX <= 7; rowX++) {
            //+x, 0
            int colY = piecePos.y + yPiece;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateEastMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int colY = yPiece + 1; colY <= 7; colY++) {
            // 0, +y
            int rowX = piecePos.x + xPiece;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

    private void populateWestMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        for (int colY = yPiece - 1; colY >= 0; colY--) {
            // 0, -y
            int rowX = piecePos.x + xPiece;

            if (!isInBoardBounds(rowX, colY))
                return;

            Point move = calculateDeltaMove(piecePos, new Point(rowX, colY));
            Point posAfterMove = new Point(piecePos.x + move.x, piecePos.y + move.y);

            if (board.getPiece(posAfterMove.x, posAfterMove.y) instanceof Empty) {
                /* legal move! */
                possibleMoves.add(move);
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
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
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
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
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
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
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
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
            } else if (board.getPiece(posAfterMove.x, posAfterMove.y).getColor().equals(Utils.getOpponentColor(getColor()))) {
                /* legal slay move! */
                possibleMoves.add(move);
                break;
            } else {
                /* Can't walk onto own pieces! */
                break;
            }
        }
    }

}