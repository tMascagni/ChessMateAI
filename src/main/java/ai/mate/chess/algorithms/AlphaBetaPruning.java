package ai.mate.chess.algorithms;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.BoardPosition;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;
import java.util.List;

public final class AlphaBetaPruning {

    private static double maxPly;
    private static BoardPosition[] bestMove;

    private AlphaBetaPruning() {

    }

    public static void run(Piece.Color playerColor, Board board, double maxPly) {
        alphaBetaPruning(playerColor, board, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0);
        /*
         * Gets the best move, aka.
         * the best 'from' and 'to'
         * positions.
         */
        BoardPosition from = bestMove[0];
        BoardPosition to = bestMove[1];
        //board.movePiece(from, to);
    }

    private static int alphaBetaPruning(Piece.Color playerColor, Board board, double alpha, double beta, int currentPly) {
        /*
         * Black: maximizer
         * White: minimizer
         */
        boolean maximizer = playerColor == Piece.Color.BLACK;

        if (currentPly++ == maxPly) {
            return score(playerColor, board, currentPly);
        }

        /*
         * Get all the possible moves for this player.
         */
        List<Point> possibleMovesPlayer = board.getPossibleMovesFromPlayer(playerColor);


        if (maximizer) {

        } else {

        }



       /*
        if (currentPly++ == maxPly || board.isGameOver()) {
            return score(player, board, currentPly);
        }
        */
        return 0;
    }

    private static int getMax(Piece.Color playerColor, Board board, double alpha, double beta, int currentPly) {
        return 0;
    }

    private static int getMin(Piece.Color playerColor, Board board, double alpha, double beta, int currentPly) {
        return 0;
    }

    private static int score(Piece.Color playerColor, Board board, int currentPly) {
        if (playerColor == Piece.Color.EMPTY)
            throw new IllegalArgumentException("Player must be BLACK or WHITE!.");

        /*
        Board.SquareState opponent = (player == Board.SquareState.X) ? Board.SquareState.O : Board.SquareState.X;

        if (board.isGameOver() && board.getWinner() == player) {
            return 10 - currentPly;
        } else if (board.isGameOver() && board.getWinner() == opponent) {
            return -10 + currentPly;
        } else {
            return 0;
        }
  */
        return 0;
    }

}