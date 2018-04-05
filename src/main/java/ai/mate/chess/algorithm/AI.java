package ai.mate.chess.algorithm;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;
import java.util.ArrayList;

public final class AI {

    private final int maxDepth;
    private Move bestMove;

    public AI(int maxDepth) {
        this.maxDepth = maxDepth;
        this.bestMove = null;
    }

    /**
     * Gets the best updatePiece via Alpha Beta
     *
     * @param board Initial board
     * @return Best updatePiece the AI can make
     */
    public Move bestMove(Board board) {
        alphaBetaPruning(board, Piece.PlayerColor.BLACK, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        return bestMove;
    }

    /**
     * Recursive method for alpha beta pruning
     *
     * @param board       Board
     * @param playerColor PlayerColor AI identifies with
     * @param alpha       Alpha value (initially -inf)
     * @param beta        Beta value (initially +inf)
     * @param depth       Current depth (initially 0)
     * @return Alpha when maximizing, beta when minimizing
     */
    private double alphaBetaPruning(Board board, Piece.PlayerColor playerColor, double alpha, double beta, int depth) {
        boolean maximize = playerColor == Piece.PlayerColor.BLACK;

        if (depth++ == maxDepth) {
            return score(maximize, board);
        }
        ArrayList<Move> moves = getAllPossibleMoves(board, playerColor);

        if (maximize) {
            Move localBestMove = null;
            for (Move move : moves) {
                Board copy = new Board(board);
                move.handleMove(copy);
                double score = alphaBetaPruning(copy, switchTeam(playerColor), alpha, beta, depth);
                move.undo(copy);

                if (score > alpha) {
                    alpha = score;
                    localBestMove = move;
                }

                if (beta <= alpha) {
                    break;
                }
            }

            if (localBestMove != null)
                bestMove = localBestMove;

            return alpha;
        } else {
            for (Move move : moves) {
                Board copy = new Board(board);
                move.handleMove(copy);
                double score = alphaBetaPruning(copy, switchTeam(playerColor), alpha, beta, depth);
                move.undo(copy);

                if (score < beta) {
                    beta = score;
                }

                if (beta <= alpha) {
                    break;
                }
            }

            return beta;
        }
    }

    /**
     * Gets all possible moves that can be made on the board for a playerColor
     *
     * @param board       board to make moves
     * @param playerColor playerColor to get moves for
     * @return List of available moves
     */
    private ArrayList<Move> getAllPossibleMoves(Board board, Piece.PlayerColor playerColor) {
        ArrayList<Move> results = new ArrayList<>();

        for (Tile[] tiles : board.getBoard()) {
            for (Tile tile : tiles) {
                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    if (piece.getPlayerColor() == playerColor) {
                        results.addAll(piece.getAvailableMoves(board));
                    }
                }
            }
        }

        return results;
    }

    /**
     * Evaluates the board
     *
     * @param maximize whether to add or subtract from score
     * @param board    board to evaluate
     * @return
     */
    private double score(boolean maximize, Board board) {
        int score = 0;

        for (Tile[] tiles : board.getBoard()) {
            for (Tile tile : tiles) {
                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    Point position = piece.getPosition();
                    int x = (int) piece.getPosition().getX();
                    int y = (int) ((piece.getPlayerColor() == Piece.PlayerColor.WHITE) ? (position.getY()) : (7 - position.getY()));

                    if (maximize) {
                        // remmeber to get the current ply into the score as well
                        score += piece.getScore() + piece.getPositionTable()[y][x];
                    } else {
                        score -= piece.getScore() + piece.getPositionTable()[y][x];
                    }
                }
            }
        }

        return score;
    }

    private Piece.PlayerColor switchTeam(Piece.PlayerColor playerColor) {
        return playerColor == Piece.PlayerColor.WHITE ? Piece.PlayerColor.BLACK : Piece.PlayerColor.WHITE;
    }

}