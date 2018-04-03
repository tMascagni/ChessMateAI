package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.board.BoardOld;
import ai.mate.chess.model.board.BoardPosition;


import java.awt.*;

/*
 * Haest
 */
public final class Knight extends Piece {

    public Knight(Color color) {
        super(color);
        this.score = KNIGHT_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_KNIGHT;
        else
            name = TextHandler.BLACK_KNIGHT;
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
        Point posAfterLeftMove = new Point();
        Point posAfterRightMove = new Point();

        /* New positions are north move */
        posAfterLeftMove.x = piecePos.x - 2;
        posAfterLeftMove.y = piecePos.y - 1;

        posAfterRightMove.x = piecePos.x - 2;
        posAfterRightMove.y = piecePos.y + 1;

        /* Check left pos bounds */
        if (isOutOfBounds(posAfterLeftMove.x, posAfterLeftMove.y))
            return;

        /* Check right pos bounds */
        if (isOutOfBounds(posAfterRightMove.x, posAfterRightMove.y))
            return;

        /* Delta moves */
        Point moveLeft = calculateDeltaMove(piecePos, posAfterLeftMove);
        Point moveRight = calculateDeltaMove(piecePos, posAfterRightMove);

        /* Left */
        if (boardOld.getPiece(posAfterLeftMove.x, posAfterLeftMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveLeft);
        } else if (boardOld.getPiece(posAfterLeftMove.x, posAfterLeftMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveLeft);
        }

        /* Right */
        if (boardOld.getPiece(posAfterRightMove.x, posAfterRightMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveRight);
        } else if (boardOld.getPiece(posAfterRightMove.x, posAfterRightMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveRight);
        }
    }

    private void populateEastMoves(Point piecePos, BoardOld boardOld) {
        Point posAfterNorthMove = new Point();
        Point posAfterSouthMove = new Point();

        /* New positions are north move */
        posAfterNorthMove.x = piecePos.x - 1;
        posAfterNorthMove.y = piecePos.y + 2;

        posAfterSouthMove.x = piecePos.x + 1;
        posAfterSouthMove.y = piecePos.y + 2;

        /* Check north pos bounds */
        if (isOutOfBounds(posAfterNorthMove.x, posAfterNorthMove.y))
            return;

        /* Check south pos bounds */
        if (isOutOfBounds(posAfterSouthMove.x, posAfterSouthMove.y))
            return;

        /* Delta moves */
        Point moveNorth = calculateDeltaMove(piecePos, posAfterNorthMove);
        Point moveSouth = calculateDeltaMove(piecePos, posAfterSouthMove);

        /* North */
        if (boardOld.getPiece(posAfterNorthMove.x, posAfterNorthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveNorth);
        } else if (boardOld.getPiece(posAfterNorthMove.x, posAfterNorthMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveNorth);
        }

        /* South */
        if (boardOld.getPiece(posAfterSouthMove.x, posAfterSouthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveSouth);
        } else if (boardOld.getPiece(posAfterSouthMove.x, posAfterSouthMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveSouth);
        }
    }

    private void populateSouthMoves(Point piecePos, BoardOld boardOld) {
        Point posAfterLeftMove = new Point();
        Point posAfterRightMove = new Point();

        /* New positions are north move */
        posAfterLeftMove.x = piecePos.x + 2;
        posAfterLeftMove.y = piecePos.y - 1;

        posAfterRightMove.x = piecePos.x + 2;
        posAfterRightMove.y = piecePos.y + 1;

        /* Check left pos bounds */
        if (isOutOfBounds(posAfterLeftMove.x, posAfterLeftMove.y))
            return;

        /* Check right pos bounds */
        if (isOutOfBounds(posAfterRightMove.x, posAfterRightMove.y))
            return;

        /* Delta moves */
        Point moveLeft = calculateDeltaMove(piecePos, posAfterLeftMove);
        Point moveRight = calculateDeltaMove(piecePos, posAfterRightMove);

        /* Left */
        if (boardOld.getPiece(posAfterLeftMove.x, posAfterLeftMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveLeft);
        } else if (boardOld.getPiece(posAfterLeftMove.x, posAfterLeftMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveLeft);
        }

        /* Right */
        if (boardOld.getPiece(posAfterRightMove.x, posAfterRightMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveRight);
        } else if (boardOld.getPiece(posAfterRightMove.x, posAfterRightMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveRight);
        }
    }

    private void populateWestMoves(Point piecePos, BoardOld boardOld) {
        Point posAfterNorthMove = new Point();
        Point posAfterSouthMove = new Point();

        /* New positions are north move */
        posAfterNorthMove.x = piecePos.x - 1;
        posAfterNorthMove.y = piecePos.y - 2;

        posAfterSouthMove.x = piecePos.x + 1;
        posAfterSouthMove.y = piecePos.y - 2;

        /* Check north pos bounds */
        if (isOutOfBounds(posAfterNorthMove.x, posAfterNorthMove.y))
            return;

        /* Check south pos bounds */
        if (isOutOfBounds(posAfterSouthMove.x, posAfterSouthMove.y))
            return;

        /* Delta moves */
        Point moveNorth = calculateDeltaMove(piecePos, posAfterNorthMove);
        Point moveSouth = calculateDeltaMove(piecePos, posAfterSouthMove);

        /* North */
        if (boardOld.getPiece(posAfterNorthMove.x, posAfterNorthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveNorth);
        } else if (boardOld.getPiece(posAfterNorthMove.x, posAfterNorthMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveNorth);
        }

        /* South */
        if (boardOld.getPiece(posAfterSouthMove.x, posAfterSouthMove.y) instanceof Empty) {
            /* Legal move! */
            possibleMoves.add(moveSouth);
        } else if (boardOld.getPiece(posAfterSouthMove.x, posAfterSouthMove.y).getColor().equals(Piece.getOpponentColor(getColor()))) {
            /* Legal slay move! */
            possibleMoves.add(moveSouth);
        }
    }

}