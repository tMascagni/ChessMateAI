package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.util.Utils;

import java.awt.*;

/*
 * Taarn
 */
public final class Rook extends Piece {

    public Rook(Color color) {
        super(color);
        this.score = Utils.ROOK_SCORE;
    }

    @Override
    protected void initName() {
        if (color.equals(Color.WHITE))
            name = TextHandler.WHITE_ROOK;
        else
            name = TextHandler.BLACK_ROOK;
    }

    @Override
    public boolean isValidMove(BoardPosition from, BoardPosition to, Board board) {
        return true;
    }

    @Override
    public void populateMoves(Board board) {
        possibleMoves.clear();


    }
    public void moveNorth(Point point, Board board){
        /* Reads the position of the current piece and then calculates if its a possible move.
         * reason behind x-1 is because the queens square dosent matter to us.
         */
        System.out.print(getColor()+ ": North - ");
        for (int i = point.x-1; i >= 0; i--){
            if (board.getPiece(i, point.y) instanceof Empty){
                System.out.print(new Point(i, point.y) + ", ");

                possibleMoves.add(new Point(i, point.y));
            } else {
                break;
            }
        }
        System.out.println();
    }


    public void moveEast(Point point, Board board){
        System.out.print(getColor()+ ": East - ");
        for (int i = point.y+1; i <= 7; i++){
            if (board.getPiece(point.x, i) instanceof Empty){
                System.out.print(new Point(point.x, i) + ", ");

                possibleMoves.add(new Point(point.x, i));
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
    public void moveWest(Point point, Board board){
        System.out.print(getColor()+ ": West - ");
        for (int i = point.y-1; i >= 0; i--){
            if (board.getPiece(point.x, i) instanceof Empty){
                System.out.print(new Point(point.x, i) + ", ");

                possibleMoves.add(new Point(point.x, i));
            } else {
                break;
            }
        }
        System.out.println();

    }

}