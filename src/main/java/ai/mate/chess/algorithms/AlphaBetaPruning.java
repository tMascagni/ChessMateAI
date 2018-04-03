package ai.mate.chess.algorithms;

import ai.mate.chess.model.board.BoardOld;
import ai.mate.chess.model.board.BoardPosition;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;
import java.util.List;

public final class AlphaBetaPruning {

    private static double maxPly;
    private static BoardPosition[] bestMove;

    private AlphaBetaPruning() {

    }

    public static void run(Piece.Color playerColor, BoardOld boardOld, double maxPly) {
        alphaBetaPruning(playerColor, boardOld, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY, 0);
        /*
         * Gets the best move, aka.
         * the best 'from' and 'to'
         * positions.
         */
        BoardPosition from = bestMove[0];
        BoardPosition to = bestMove[1];
        //boardOld.movePiece(from, to);
    }

    private static int alphaBetaPruning(Piece.Color playerColor, BoardOld boardOld, double alpha, double beta, int currentPly) {
        /*
         * Black: maximizer
         * White: minimizer
         */
        boolean maximizer = playerColor == Piece.Color.BLACK;

        if (currentPly++ == maxPly) {
            return score(playerColor, boardOld, currentPly);
        }

        /*
         * Get all the possible moves for this player.
         */
        List<Point> possibleMovesPlayer = boardOld.getPossibleMovesFromPlayer(playerColor);


        if (maximizer) {

        } else {

        }



       /*
        if (currentPly++ == maxPly || boardOld.isGameOver()) {
            return score(player, boardOld, currentPly);
        }
        */
        return 0;
    }

    private static int getMax(Piece.Color playerColor, BoardOld boardOld, double alpha, double beta, int currentPly) {
        return 0;
    }

    private static int getMin(Piece.Color playerColor, BoardOld boardOld, double alpha, double beta, int currentPly) {
        return 0;
    }

    private static int score(Piece.Color playerColor, BoardOld boardOld, int currentPly) {
        if (playerColor == Piece.Color.EMPTY)
            throw new IllegalArgumentException("Player must be BLACK or WHITE!.");

        /*
        BoardOld.SquareState opponent = (player == BoardOld.SquareState.X) ? BoardOld.SquareState.O : BoardOld.SquareState.X;

        if (boardOld.isGameOver() && boardOld.getWinner() == player) {
            return 10 - currentPly;
        } else if (boardOld.isGameOver() && boardOld.getWinner() == opponent) {
            return -10 + currentPly;
        } else {
            return 0;
        }
  */
        return 0;
    }

}