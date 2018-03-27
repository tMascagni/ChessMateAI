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

        //-x, 0
        int rowX = xPiece - 1;
        int colY = piecePos.y + yPiece;

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
        }
    }

    private void populateSouthMoves(Point piecePos, Board board) {
        int xPiece = piecePos.x;
        int yPiece = 0;

        //+x, 0
        int rowX = xPiece + 1;
        int colY = piecePos.y + yPiece;

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
        }
    }

    private void populateEastMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        // 0, +y
        int rowX = piecePos.x + xPiece;
        int colY = yPiece + 1;

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
        }
    }

    private void populateWestMoves(Point piecePos, Board board) {
        int xPiece = 0;
        int yPiece = piecePos.y;

        // 0, -y
        int rowX = piecePos.x + xPiece;
        int colY = yPiece - 1;

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
        }
    }

    private void populateNorthEastMoves(Point piecePos, Board board) {
        // x-, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece - 1;
        int colY = yPiece + 1;

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
        }
    }

    private void populateSouthEastMoves(Point piecePos, Board board) {
        // x+, y+
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece + 1;
        int colY = yPiece + 1;

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
        }
    }

    private void populateSouthWestMoves(Point piecePos, Board board) {
        // x+, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece + 1;
        int colY = yPiece - 1;

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
        }
    }

    private void populateNorthWestMoves(Point piecePos, Board board) {
        // x-, y-
        int xPiece = piecePos.x;
        int yPiece = piecePos.y;

        int rowX = xPiece - 1;
        int colY = yPiece - 1;

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
        }
    }

    private void addToKillMoves(Board board, int xAfterMove, int yAfterMove) {
        slayMoves.add(board.getPiece(xAfterMove, yAfterMove));
    }

}