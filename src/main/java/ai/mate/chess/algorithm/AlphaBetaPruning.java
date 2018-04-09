package ai.mate.chess.algorithm;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.utils.ChessUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class AlphaBetaPruning {

    private double maxPly;
    private int timerSeconds;
    private Move bestMove;
    private int counter = 0;
    private volatile boolean timeIsUp;
    private Timer timer;

    public AlphaBetaPruning(double maxPly, int timerSeconds) {
        this.maxPly = maxPly;
        this.timerSeconds = timerSeconds;
        this.timeIsUp = false;
    }

    /**
     * This is the starting point of the algorithm, of which
     * will return the best move.
     */
    public Move run(Board board, Piece.PlayerColor playerColor) {
        this.timeIsUp = false;
        this.counter = 0;
        timer = new Timer();

        startTimer(playerColor);
        alphaBetaPruning(board, playerColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);

        timer.cancel();
        return bestMove;
    }

    /**
     * Recursive alpha-beta pruning algorithm.
     *
     * @param board       Board
     * @param playerColor PlayerColor AI identifies with
     * @param alpha       Alpha value (initially -infinity)
     * @param beta        Beta value (initially +infinity)
     * @param currentPly  Current ply (depth, initially 0)
     * @return Alpha when maximizing, beta when minimizing
     */
    private double alphaBetaPruning(Board board, Piece.PlayerColor playerColor, double alpha, double beta, int currentPly) {
        boolean maximizer = (playerColor == Piece.PlayerColor.BLACK);

        if (currentPly++ == maxPly || timeIsUp)
            return score(maximizer, board, currentPly);

        List<Move> moves = getAllPossibleMoves(board, playerColor);

        if (maximizer) {
            Move localBestMoveMaximizer = null;
            for (Move move : moves) {
                Board copyBoard = new Board(board);
                move.handleMove(copyBoard);
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerColor), alpha, beta, currentPly);
                move.undo(copyBoard);

                if (score > alpha) {
                    alpha = score;
                    localBestMoveMaximizer = move;
                }

                if (beta <= alpha)
                    break;
            }

            if (localBestMoveMaximizer != null)
                bestMove = localBestMoveMaximizer;

            return alpha;

        } else {
            Move localBestMoveMinimizer = null;
            for (Move move : moves) {
                Board copyBoard = new Board(board);
                move.handleMove(copyBoard);
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerColor), alpha, beta, currentPly);
                move.undo(copyBoard);

                if (score < beta) {
                    beta = score;
                    localBestMoveMinimizer = move;
                }

                if (beta <= alpha)
                    break;
            }

            /*
            if (localBestMoveMinimizer != null) {
                bestMove = localBestMoveMinimizer;
            }
            */

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
    private List<Move> getAllPossibleMoves(Board board, Piece.PlayerColor playerColor) {
        List<Move> results = new ArrayList<>();

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
     * @param maximizer whether to add or subtract from score
     * @param board     board to evaluate
     * @return
     */
    private double score(boolean maximizer, Board board, int currentPly) {
        int score = 0;

        for (Tile[] tiles : board.getBoard()) {

            for (Tile tile : tiles) {

                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    Point position = piece.getPosition();

                    int x = (int) piece.getPosition().getX();
                    int y = (int) ((piece.getPlayerColor() == Piece.PlayerColor.WHITE) ? (position.getY()) : (7 - position.getY()));

                    if (maximizer)
                        score += piece.getScore() + piece.getPositionTable()[y][x] + currentPly;
                    else
                        score -= piece.getScore() + piece.getPositionTable()[y][x] - currentPly;
                }
            }
        }

        return score;
    }

    private void startTimer(Piece.PlayerColor playerColor) {
        System.out.println("TIMER: New timer for " + playerColor + "!");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                counter++;
                System.out.println("TIMER: " + counter);

                if (counter == timerSeconds) {
                    System.out.println("TIMER: Time's up for " + playerColor + "!");
                    timeIsUp = true;
                }
            }
        }, 0, 1000);
    }

}