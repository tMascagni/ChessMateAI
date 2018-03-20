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
        Point left = new Point();
        Point right = new Point();

        /* New positions are north move */
        left.x = piecePos.x - 2;
        left.y = piecePos.y - 1;

        right.x = piecePos.x - 2;
        right.y = piecePos.y + 1;

        /* Check left pos bounds */
        if ((left.x < 0 || left.x > 7) || (left.y < 0 || left.y > 7))
            return;

        /* Check right pos bounds */
        if ((right.x < 0 || right.x > 7) || (right.y < 0 || right.y > 7))
            return;

        /* Delta moves */
        Point moveLeft = calculateDeltaMove(piecePos, left);
        Point moveRight = calculateDeltaMove(piecePos, right);


        System.out.println("NORTH: Moves LEFT: (" + left.x + ", " + left.y + ") RIGHT: (" + right.x + ", " + right.y + ") Position after move: LEFT: (" + moveLeft.x + ", " + moveLeft.y + ") RIGHT: (" + moveRight.x + ", " + moveRight.y + ")");

    }

    private void populateEastMoves(Point piecePos, Board board) {

    }

    private void populateSouthMoves(Point piecePos, Board board) {

    }

    private void populateWestMoves(Point piecePos, Board board) {

    }

}