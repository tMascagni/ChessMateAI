package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.BoardPosition;

import java.awt.*;


/*
 * Bonde
 */
public final class Pawn extends Piece {

    public Pawn(Color color) {
        super(color);
        this.score = 1;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to) {
        int new_y = Math.abs(from.arrayY - to.arrayY);
        int new_x = Math.abs(from.arrayX - to.arrayX);

        System.out.println("new_y: " + new_y);
        System.out.println("new_x: " + new_x);

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