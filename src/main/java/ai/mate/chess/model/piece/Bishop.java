package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Loeber
 */
public final class Bishop extends Piece {

    public Bishop(Color color) {
        super(color);
        this.score = Utils.BISHOP_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_BISHOP;
        else
            name = TextHandler.BLACK_BISHOP;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        return true;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();


    }


    public void moveNorthWest(Point point, Board board) {
        int y = point.y - 1;

        System.out.print(getColor() + ": North West - ");
        for (int i = point.x - 1; i >= 0; i--) {


            if (y < 0) break;

            if (board.getPiece(i, y) instanceof Empty) {
                System.out.print(new Point(i, y) + ", ");

                possibleMoves.add(new Point(i, y));
                y--;
            } else {
                break;
            }

        }
        System.out.println();
    }

    public void moveSouthWest(Point point, Board board) {
        int y = point.y - 1;

        System.out.print(getColor() + ": South West - ");
        for (int i = point.x + 1; i <= 7; i++) {


            if (y < 0) break;

            if (board.getPiece(i, y) instanceof Empty) {
                System.out.print(new Point(i, y) + ", ");

                possibleMoves.add(new Point(i, y));
                y--;
            } else {
                break;
            }
        }
        System.out.println();
    }

    public void moveSouthEast(Point point, Board board) {
        int y = point.y + 1;

        System.out.print(getColor() + ": South East - ");
        for (int i = point.x + 1; i <= 7; i++) {


            if (y > 7) break;

            if (board.getPiece(i, y) instanceof Empty) {
                System.out.print(new Point(i, y) + ", ");

                possibleMoves.add(new Point(i, y));
                y++;
            } else {
                break;
            }

        }
        System.out.println();
    }

    public void moveNorthEast(Point point, Board board) {
        int y = point.y + 1;

        System.out.print(getColor() + ": North East - ");
        for (int i = point.x - 1; i >= 0; i--) {


            if (y > 7) break;

            if (board.getPiece(i, y) instanceof Empty) {
                System.out.print(new Point(i, y) + ", ");

                possibleMoves.add(new Point(i, y));
                y++;
            } else {
                break;
            }

        }
        System.out.println();
    }
}