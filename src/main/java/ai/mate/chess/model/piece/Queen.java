package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Dronning
 */
public final class Queen extends Piece {

    public Queen(Color color) {
        super(color);
        this.score = Utils.QUEEN_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_QUEEN;
        else
            name = TextHandler.BLACK_QUEEN;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        return true;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();

        /* Firstly, we need to get this piece's board position */
        BoardPosition piecePos = board.getPieceBoardPos(this.ID);

        Point p = new Point(piecePos.arrayX, piecePos.arrayY);

        moveNorth(p, board);
        moveSouth(p, board);
        moveEast(p, board);
        moveWest(p, board);
        moveNorthEast(p, board);
        moveSouthEast(p, board);
        moveSouthWest(p, board);
        moveNorthWest(p, board);

    }

    public void moveNorth(Point point, Board board) {
        /* Reads the position of the current piece and then calculates if its a possible move.
         * reason behind x-1 is because the queens square dosent matter to us.
         */
        System.out.print(getColor() + ": North - ");
        for (int i = point.x - 1; i >= 0; i--) {
            if (board.getPiece(i, point.y) instanceof Empty) {
                System.out.print(new Point(i, point.y) + ", ");

                possibleMoves.add(new Point(i, point.y));
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

    public void moveEast(Point point, Board board) {
        System.out.print(getColor() + ": East - ");
        for (int i = point.y + 1; i <= 7; i++) {
            if (board.getPiece(point.x, i) instanceof Empty) {
                System.out.print(new Point(point.x, i) + ", ");

                possibleMoves.add(new Point(point.x, i));
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

    public void moveSouth(Point point, Board board) {
        /* Reads the position of the current piece and then calculates if its a possible move */
        System.out.print(getColor() + ": South - ");
        for (int i = point.x + 1; i <= 7; i++) {
            if (board.getPiece(i, point.y) instanceof Empty) {
                System.out.print(new Point(i, point.y) + ", ");

                possibleMoves.add(new Point(i, point.y));
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

    public void moveWest(Point point, Board board) {
        System.out.print(getColor() + ": West - ");
        for (int i = point.y - 1; i >= 0; i--) {
            if (board.getPiece(point.x, i) instanceof Empty) {
                System.out.print(new Point(point.x, i) + ", ");

                possibleMoves.add(new Point(point.x, i));
            } else {
                break;
            }
        }
        System.out.println();

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
}
