package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
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
    public boolean isValidMove(BoardPosition from, BoardPosition to, IPiece[][] board) {
        int deltaX = calculateDeltaX(from.arrayX, to.arrayX);
        int deltaY = calculateDeltaY(from.arrayY, to.arrayY);

        for (Point p : possibleMoves) {
            if (p.x == deltaX && p.y == deltaY)
                return true;
        }

        return false;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_PAWN;
        else
            name = TextHandler.BLACK_PAWN;
    }

    @Override
    public void populateMoves() {
        if (getColor().equals(Color.WHITE)) {
            if (getMoveCount() == 0)
                possibleMoves.add(new Point(2, 0));
            possibleMoves.add(new Point(1, 0));
            possibleMoves.add(new Point(1, 1));
            possibleMoves.add(new Point(1, -1));
        } else {
            if (getMoveCount() == 0)
                possibleMoves.add(new Point(-2, 0));
            possibleMoves.add(new Point(-1, 0));
            possibleMoves.add(new Point(-1, 1));
            possibleMoves.add(new Point(-1, -1));
        }
    }

}