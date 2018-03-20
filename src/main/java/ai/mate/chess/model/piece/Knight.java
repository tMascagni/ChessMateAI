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

    }

    private void populateEastMoves(Point piecePos, Board board) {

    }

    private void populateSouthMoves(Point piecePos, Board board) {

    }

    private void populateWestMoves(Point piecePos, Board board) {

    }

}