package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Haest
 */
public final class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.score = Utils.KNIGHT_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KNIGHT;
        else
            name = TextHandler.BLACK_KNIGHT;
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
    }

    private void populateNorthMoves(Point piecePos, Board board) {
        Point posAfterLeftMove = new Point();
        Point posAfterRightMove = new Point();

        /* New positions are north move */
        posAfterLeftMove.x = piecePos.x - 2;
        posAfterLeftMove.y = piecePos.y - 1;

        posAfterRightMove.x = piecePos.x - 2;
        posAfterRightMove.y = piecePos.y + 1;

        /* Check left pos bounds */
        if (!isInBoardBounds(posAfterLeftMove.x, posAfterLeftMove.y))
            return;

        /* Check right pos bounds */
        if (!isInBoardBounds(posAfterRightMove.x, posAfterRightMove.y))
            return;

        /* Delta moves */
        Point moveLeft = calculateDeltaMove(piecePos, posAfterLeftMove);
        Point moveRight = calculateDeltaMove(piecePos, posAfterRightMove);

        /* Left */
        if (board.getPiece(posAfterLeftMove.x, posAfterLeftMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveLeft);
        } else if (board.getPiece(posAfterLeftMove.x, posAfterLeftMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveLeft);
        }

        /* Right */
        if (board.getPiece(posAfterRightMove.x, posAfterRightMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveRight);
        } else if (board.getPiece(posAfterRightMove.x, posAfterRightMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveRight);
        }
    }

    private void populateEastMoves(Point piecePos, Board board) {
        Point posAfterNorthMove = new Point();
        Point posAfterSouthMove = new Point();

        /* New positions are north move */
        posAfterNorthMove.x = piecePos.x - 1;
        posAfterNorthMove.y = piecePos.y + 2;

        posAfterSouthMove.x = piecePos.x + 1;
        posAfterSouthMove.y = piecePos.y + 2;

        /* Check north pos bounds */
        if (!isInBoardBounds(posAfterNorthMove.x, posAfterNorthMove.y))
            return;

        /* Check south pos bounds */
        if (!isInBoardBounds(posAfterSouthMove.x, posAfterSouthMove.y))
            return;

        /* Delta moves */
        Point moveNorth = calculateDeltaMove(piecePos, posAfterNorthMove);
        Point moveSouth = calculateDeltaMove(piecePos, posAfterSouthMove);

        /* North */
        if (board.getPiece(posAfterNorthMove.x, posAfterNorthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveNorth);
        } else if (board.getPiece(posAfterNorthMove.x, posAfterNorthMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveNorth);
        }

        /* South */
        if (board.getPiece(posAfterSouthMove.x, posAfterSouthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveSouth);
        } else if (board.getPiece(posAfterSouthMove.x, posAfterSouthMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveSouth);
        }
    }

    private void populateSouthMoves(Point piecePos, Board board) {
        Point posAfterLeftMove = new Point();
        Point posAfterRightMove = new Point();

        /* New positions are north move */
        posAfterLeftMove.x = piecePos.x + 2;
        posAfterLeftMove.y = piecePos.y - 1;

        posAfterRightMove.x = piecePos.x + 2;
        posAfterRightMove.y = piecePos.y + 1;

        /* Check left pos bounds */
        if (!isInBoardBounds(posAfterLeftMove.x, posAfterLeftMove.y))
            return;

        /* Check right pos bounds */
        if (!isInBoardBounds(posAfterRightMove.x, posAfterRightMove.y))
            return;

        /* Delta moves */
        Point moveLeft = calculateDeltaMove(piecePos, posAfterLeftMove);
        Point moveRight = calculateDeltaMove(piecePos, posAfterRightMove);

        /* Left */
        if (board.getPiece(posAfterLeftMove.x, posAfterLeftMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveLeft);
        } else if (board.getPiece(posAfterLeftMove.x, posAfterLeftMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveLeft);
        }

        /* Right */
        if (board.getPiece(posAfterRightMove.x, posAfterRightMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveRight);
        } else if (board.getPiece(posAfterRightMove.x, posAfterRightMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveRight);
        }
    }

    private void populateWestMoves(Point piecePos, Board board) {
        Point posAfterNorthMove = new Point();
        Point posAfterSouthMove = new Point();

        /* New positions are north move */
        posAfterNorthMove.x = piecePos.x - 1;
        posAfterNorthMove.y = piecePos.y - 2;

        posAfterSouthMove.x = piecePos.x + 1;
        posAfterSouthMove.y = piecePos.y - 2;

        /* Check north pos bounds */
        if (!isInBoardBounds(posAfterNorthMove.x, posAfterNorthMove.y))
            return;

        /* Check south pos bounds */
        if (!isInBoardBounds(posAfterSouthMove.x, posAfterSouthMove.y))
            return;

        /* Delta moves */
        Point moveNorth = calculateDeltaMove(piecePos, posAfterNorthMove);
        Point moveSouth = calculateDeltaMove(piecePos, posAfterSouthMove);

        /* North */
        if (board.getPiece(posAfterNorthMove.x, posAfterNorthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveNorth);
        } else if (board.getPiece(posAfterNorthMove.x, posAfterNorthMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveNorth);
        }

        /* South */
        if (board.getPiece(posAfterSouthMove.x, posAfterSouthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveSouth);
        } else if (board.getPiece(posAfterSouthMove.x, posAfterSouthMove.y).getColor().equals(getOpponentColor())) {
            /* Legal slay move! */
            possibleMoves.add(moveSouth);
        }
    }

}