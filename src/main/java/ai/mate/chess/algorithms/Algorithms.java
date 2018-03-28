package ai.mate.chess.algorithms;

import ai.mate.chess.model.Board;

public final class Algorithms {

    private Algorithms() {

    }

    /*
     * This function is a "alpha-beta pruning"-move,
     * so it should be run after EVERY human player move.
     */
    public static void alphaBetaPruningMove(Board board) {
        /*
         * The alpha-beta pruning algorithm is fed the current player,
         * the board (so that it can use it's functions and know things.)
         * and a maxPly of infinity. We want the biggest possible ply, of course.
         */
        AlphaBetaPruning.run(board.getPlayerColor(), board, Double.POSITIVE_INFINITY);
    }

}