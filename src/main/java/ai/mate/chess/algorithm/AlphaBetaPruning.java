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

    /**
     * This is the absolute max ply that the algorithm can achieve.
     * It is set via the class constructor.
     */
    private double maxPly;

    /**
     * This is the amount of seconds that the algorithm is allowed to run.
     * If the algorithm exceeds this amount of seconds, the current best move
     * is returned.
     */
    private int timerSeconds;

    /**
     * The current best move of the algorithm.
     */
    private Move bestMove;

    /**
     * This is the elapsedSeconds variable used to keep
     * track of the elapsed seconds of the current timer.
     */
    private int elapsedSeconds = 0;

    /**
     * This boolean variable will be set to true
     * whenever the elapsedSecond reaches the timerSeconds value.
     */
    private volatile boolean timeIsUp;

    /**
     * Object used for timer functionality.
     */
    private Timer timer;

    /**
     * Constructor of the AlphaBetaPruning algorithm class.
     *
     * @param maxPly       The absolute maximum ply.
     * @param timerSeconds Amount of seconds the algorithm is allowed to run.
     */
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
        this.bestMove = null;
        this.elapsedSeconds = 0;
        timer = new Timer();

        startTimer(playerColor);
        alphaBetaPruning(board, playerColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);

        timer.cancel();
        return bestMove;
    }

    /**
     * Recursive alpha-beta pruning algorithm.
     *
     * @param board       The current board (Current state)
     * @param playerColor The current player
     * @param alpha       Alpha value (Initially -infinity)
     * @param beta        Beta value (Initially +infinity)
     * @param currentPly  Current ply (Initially 0)
     * @return Alpha when maximizing, beta when minimizing
     */
    private double alphaBetaPruning(Board board, Piece.PlayerColor playerColor, double alpha, double beta, int currentPly) {
        boolean isMaximizer = playerColor == Piece.PlayerColor.BLACK;

        /*
         * Returns the score whether we've reached
         * the maximum ply or if the time is up for the
         * algorithm.
         */
        if (currentPly++ == maxPly || timeIsUp)
            return getScore(isMaximizer, board, currentPly);

        List<Move> moves = getAllPossibleMoves(board, playerColor);

        // BLACK
        if (isMaximizer) {
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
            // WHITE
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

            if (localBestMoveMinimizer != null)
                bestMove = localBestMoveMinimizer;

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
        for (Tile[] tiles : board.getBoard())
            for (Tile tile : tiles)
                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    if (piece.getPlayerColor() == playerColor)
                        results.addAll(piece.getAvailableMoves(board));
                }
        return results;
    }

    /**
     * Evaluates the board
     *
     * @param isMaximizer whether to add or subtract from getScore
     * @param board       board to evaluate
     * @return Returns evaluated score
     */
    private double getScore(boolean isMaximizer, Board board, int currentPly) {
        int score = 0;

        for (Tile[] tiles : board.getBoard()) {

            for (Tile tile : tiles) {

                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    Point position = piece.getPosition();

                    int x = piece.getPosition().x;
                    int y = piece.getPlayerColor() == Piece.PlayerColor.WHITE ? position.x : 7 - position.y;

                    if (isMaximizer)
                        score += (piece.getScore() + piece.getPositionTable()[y][x] + currentPly);
                    else
                        score -= (piece.getScore() + piece.getPositionTable()[y][x] + currentPly);
                }
            }
        }

        return score;
    }

    private void startTimer(Piece.PlayerColor playerColor) {
        System.out.println("AI TIMER: New timer for ChessMateAI playing as " + playerColor + "!");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
                System.out.println("AI TIMER: " + elapsedSeconds);

                if (elapsedSeconds == timerSeconds) {
                    System.out.println("AI TIMER: Time's up for ChessMateAI, " + playerColor + "!");
                    timeIsUp = true;
                }
            }
        }, 0, 1000);
    }

}