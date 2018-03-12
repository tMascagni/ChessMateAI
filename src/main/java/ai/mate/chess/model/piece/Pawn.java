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
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        int deltaX = calculateDeltaX(from.arrayX, to.arrayX);
        int deltaY = calculateDeltaY(from.arrayY, to.arrayY);

        for (Point p : possibleMoves)
            if (p.x == deltaX && p.y == deltaY)
                return true;

        return false;
    }

    /*
     * We populate the moves in array basis.
     * REMEMBER THIS! A white pawn can move -2 up in the array
     * on its first move. -1 when its not its first move.
     */
    @Override
    public void populateMoves(Board board) {
        /* Remove old possible moves */
        possibleMoves.clear();

        /* White moves */
        if (getColor().equals(Color.WHITE)) {

            /* First move (can walk 2 squares) */
            if (getMoveCount() == 0 && isStandardMoveAllowed(-2, 0, board))
                possibleMoves.add(new Point(-2, 0));

            /* Standard move (only allowed if the square is empty of opponent) */
            if (isStandardMoveAllowed(-1, 0, board))
                possibleMoves.add(new Point(-1, 0));

            /*
             * First, we need to actually check where there is an opponent piece at
             * these positions. Because these moves are only legal if an opponent
             * is on these fields.
             * s = START
             * e = END
             *             |   | e |
             * (1, 1) ->   | s |   |
             *
             *             | e |   |
             * (1, -1) ->  |   | s |
             */

            /* Slay opponents piece */
            if (isSlayMoveAllowed(-1, 1, board))
                possibleMoves.add(new Point(-1, 1));

            if (isSlayMoveAllowed(-1, -1, board))
                possibleMoves.add(new Point(-1, -1));

            /* Black moves */
        } else {
            /* First move (can walk 2 squares) */
            if (getMoveCount() == 0 && isStandardMoveAllowed(2, 0, board))
                possibleMoves.add(new Point(2, 0));

            /* Standard move (only allowed if the square is empty of opponent) */
            if (isStandardMoveAllowed(1, 0, board))
                possibleMoves.add(new Point(1, 0));

            /* Slay opponents piece */
            if (isSlayMoveAllowed(1, 1, board))
                possibleMoves.add(new Point(1, 1));

            if (isSlayMoveAllowed(1, -1, board))
                possibleMoves.add(new Point(1, -1));
        }
    }

    private boolean isSlayMoveAllowed(int x, int y, Board board) {
        /* Firstly, we need to get this piece's board position */
        BoardPosition piecePos = board.getPieceBoardPos(this.ID);

        /* Now we need to create the new position where the piece would slay */
        int xKill = piecePos.arrayX + x;
        int yKill = piecePos.arrayY + y;

        /* If we go out of bounds, we know that its a bad move. */
        if ((xKill < 0 || xKill > 7) || (yKill < 0 || yKill > 7))
            return false;

        /* Now we have the slay position. Now check if there's an opponent there! */
        IPiece pieceToBeSlain = board.getPiece(xKill, yKill);

        /* If the pieceToBeSlain is an opponent, return true, if not, return false */
        return pieceToBeSlain.getColor().equals(getOpponentColor());
    }

    private boolean isStandardMoveAllowed(int x, int y, Board board) {
        BoardPosition piecePos = board.getPieceBoardPos(ID);

        int xRel = piecePos.arrayX + x;
        int yRel = piecePos.arrayY + y;

        /* If we go out of bounds, we know that its a bad move. */
        if ((xRel < 0 || xRel > 7) || (yRel < 0 || yRel > 7))
            return false;

        IPiece pieceAtRelPosition = board.getPiece(xRel, yRel);

        if (pieceAtRelPosition.getColor().equals(getOpponentColor()))
            return false;

        if (pieceAtRelPosition.getColor().equals(getColor()))
            return false;

        return true;
    }

}