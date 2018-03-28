package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Bonde
 */
public final class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        this.score = Utils.PAWN_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_PAWN;
        else
            name = TextHandler.BLACK_PAWN;
    }

    @Override
    public void populateMoves(Board board) {
        /* Remove old possible moves and slay moves */
        resetMoves();

        /* Populate moves */
        if (getColor().equals(Color.WHITE))
            populateWhiteMoves(board);
        else if (getColor().equals(Color.BLACK))
            populateBlackMoves(board);
    }

    private void populateWhiteMoves(Board board) {
        /* First move (can walk 2 squares) */
        if (isFirstMoveAllowed(new Point(-2, 0), board))
            possibleMoves.add(new Point(-2, 0));

        /* Standard move (only allowed if the square is empty of opponent) */
        if (isStandardMoveAllowed(new Point(-1, 0), board))
            possibleMoves.add(new Point(-1, 0));

        /* Slay opponents piece */
        if (isSlayMoveAllowed(new Point(-1, 1), board)) {
            possibleMoves.add(new Point(-1, 1));
        }

        if (isSlayMoveAllowed(new Point(-1, -1), board)) {
            possibleMoves.add(new Point(-1, -1));
        }
    }

    private void populateBlackMoves(Board board) {
        /* First move (can walk 2 squares) */
        if (isFirstMoveAllowed(new Point(2, 0), board))
            possibleMoves.add(new Point(2, 0));

        /* Standard move (only allowed if the square is empty of opponent) */
        if (isStandardMoveAllowed(new Point(1, 0), board))
            possibleMoves.add(new Point(1, 0));

        /* Slay opponents piece */
        if (isSlayMoveAllowed(new Point(1, 1), board)) {
            possibleMoves.add(new Point(1, 1));
        }

        if (isSlayMoveAllowed(new Point(1, -1), board)) {
            possibleMoves.add(new Point(1, -1));
        }
    }

    private boolean isSlayMoveAllowed(Point p, Board board) {
        /* Firstly, we need to get this piece's board position */
        BoardPosition piecePos = board.getPieceBoardPos(this.ID);

        /* Now we need to create the new position where the piece would slay */
        int xKill = piecePos.rowX + p.x;
        int yKill = piecePos.colY + p.y;

        /* If we go out of bounds, we know that its a bad move. */
        if (!isInBoardBounds(xKill, yKill))
            return false;

        /* Now we have the slay position. Now check if there's an opponent there! */
        IPiece pieceToBeSlain = board.getPiece(xKill, yKill);

        /* If the pieceToBeSlain is an opponent, return true, if not, return false */
        return pieceToBeSlain.getColor().equals(Utils.getOpponentColor(getColor()));
    }

    private boolean isStandardMoveAllowed(Point p, Board board) {
        BoardPosition piecePos = board.getPieceBoardPos(ID);

        int xRel = piecePos.rowX + p.x;
        int yRel = piecePos.colY + p.y;

        /* If we go out of bounds, we know that its a bad move. */
        if (!isInBoardBounds(xRel, yRel))
            return false;

        IPiece pieceAtRelPosition = board.getPiece(xRel, yRel);

        if (pieceAtRelPosition.getColor().equals(Utils.getOpponentColor(getColor())))
            return false;

        return !pieceAtRelPosition.getColor().equals(getColor());
    }

    private boolean isFirstMoveAllowed(Point p, Board board) {
        if (Math.abs(p.x) != 2) // Is it really the first move?
            return false;

        if (getMoveCount() != 0)
            return false;

        BoardPosition piecePos = board.getPieceBoardPos(ID);

        int xRel = piecePos.rowX + p.x;
        int yRel = piecePos.colY + p.y;

        /* If we go out of bounds, we know that its a bad move. */
        if (!isInBoardBounds(xRel, yRel))
            return false;

        IPiece pieceAtRelPosition = board.getPiece(xRel, yRel);

        if (!(pieceAtRelPosition instanceof Empty))
            return false;

        int dec = -1;

        if (getColor().equals(Color.WHITE))
            dec = 1;

        xRel = piecePos.rowX + p.x + dec;
        yRel = piecePos.colY + p.y;

        /* If we go out of bounds, we know that its a bad move. */
        if (!isInBoardBounds(xRel, yRel))
            return false;

        pieceAtRelPosition = board.getPiece(xRel, yRel);

        return pieceAtRelPosition instanceof Empty;
    }

}