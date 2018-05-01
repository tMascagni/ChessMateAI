package ai.mate.chess.algorithm;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.utils.ChessUtils;

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
    private volatile boolean timeIsUp = false;

    private boolean IS_AI_TIMER_ENABLED = true;

    /**
     * Object used for timer functionality.
     */
    private Timer timer;

    private int staticEvalCount = 0;

    /**
     * Constructor of the AlphaBetaPruning algorithm class.
     *
     * @param maxPly       The absolute maximum ply.
     * @param timerSeconds Amount of seconds the algorithm is allowed to run.
     */
    public AlphaBetaPruning(double maxPly, int timerSeconds) {
        this.maxPly = maxPly;
        this.timerSeconds = timerSeconds;
    }

    /**
     * This is the starting point of the algorithm, of which
     * will return the best move.
     */
    public Move run(Board board, Piece.PlayerColor AIColor) {
        if (IS_AI_TIMER_ENABLED)
            this.timeIsUp = false;

        this.bestMove = null;

        this.elapsedSeconds = 0;
        this.staticEvalCount = 0;

        if (IS_AI_TIMER_ENABLED) {
            timer = new Timer();
            startTimer(AIColor);
        }

        for (int i = 1; i <= maxPly; i++) {
            alphaBetaPruning(board, AIColor, AIColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0, i);
            if(!timeIsUp) {
                System.out.println("Static evaluations at " + i + " ply: " + staticEvalCount);
                if (i < 5) {
                    System.out.println("Node count at " + i + " ply: " + Perft(board, AIColor, i));
                }
            }
        }

        if (IS_AI_TIMER_ENABLED) {
            timer.cancel();
        }

        System.out.println("AI PLAYER: Best move found!" + bestMove.toString());
        System.out.println("AI PLAYER: " + staticEvalCount + " static evaluations at maxPly " + maxPly + " was made.");
        return bestMove;
    }

    /**
     * Recursive alpha-beta pruning algorithm.
     *
     * @param board        The current board (Current state)
     * @param playerToMove The current player
     * @param alpha        Alpha value (Initially -infinity)
     * @param beta         Beta value (Initially +infinity)
     * @param currentPly   Current ply (Initially 0)
     * @return Alpha when maximizing, beta when minimizing
     */
    private double alphaBetaPruning(Board board, Piece.PlayerColor playerToMove, Piece.PlayerColor AIColor, double alpha, double beta, int currentPly, int targetPly) {
        boolean isMaximizer = playerToMove == AIColor;

        /*
         * Returns the score whether we've reached
         * the maximum ply or if the time is up for the
         * algorithm.
         */
        if (currentPly++ == targetPly || timeIsUp)
            return getScore(playerToMove, AIColor, board, currentPly);

        List<Move> moves = getAllPossibleMoves(board, playerToMove);

        if (isMaximizer) {
            Move localBestMoveMaximizer = null;
            for (Move move : moves) {
                Board copyBoard = new Board(board);
                move.handleMove(copyBoard);
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerToMove), AIColor, alpha, beta, currentPly, targetPly);
                move.undo(copyBoard);

                if (score > alpha) {
                    alpha = score;
                    localBestMoveMaximizer = move;
                }

                // Cut off
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
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerToMove), AIColor, alpha, beta, currentPly, targetPly);
                move.undo(copyBoard);

                if (score < beta) {
                    beta = score;
                    localBestMoveMinimizer = move;
                }

                // Cut off
                if (beta <= alpha)
                    break;
            }

            if (localBestMoveMinimizer != null)
                bestMove = localBestMoveMinimizer;

            return beta;
        }
    }

    private long Perft(Board board, Piece.PlayerColor playerToMove, int depth) {

        long nodes = 0;

        if(depth == 0 || timeIsUp) {
            return 1;
        }

        List<Move> moves = getAllPossibleMoves(board, playerToMove);
        for (Move move : moves) {
            Board copyBoard = new Board(board);
            move.handleMove(copyBoard);
            nodes += Perft(copyBoard, ChessUtils.changePlayer(playerToMove), depth - 1);
            move.undo(copyBoard);
        }
        return nodes;
    }

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

    private double getScore(Piece.PlayerColor playerToMove, Piece.PlayerColor AIColor, Board board, int currentPly) {
        /*
         * A static evaluation has been made, so increase the count.
         */
        staticEvalCount++;

        int score = 0;
        int threatCount = 0;
        int threatScore = 0;

        for (Tile[] tiles : board.getBoard()) {
            for (Tile tile : tiles) {
                if (!tile.isEmpty()) {
                    if (tile.getPiece().getPlayerColor() == AIColor) {
                        score += tile.getPiece().getScore(board);
                    } else {
                        score -= tile.getPiece().getScore(board);
                    }
                    if (tile.getPiece().getPlayerColor() == playerToMove && tile.getPiece().threatensHigherRank(board)) {
                        threatCount++;
                    }
                }
            }
        }

        if (threatCount == 0) {
            threatScore = ChessUtils.THREATENED_BY_NO_MINORS;
        } else if (threatCount == 1) {
            threatScore = ChessUtils.THREATENED_BY_ONE_MINOR;
        } else {
            threatScore = ChessUtils.THREATENED_BY_SEVERAL_MINORS;
        }
        if (AIColor != playerToMove) {
            score += threatScore;
        } else {
            score -= threatScore;
        }

        return score;
    }

    private void startTimer(Piece.PlayerColor playerColor) {
        System.out.println("AI TIMER: New timer for ChessMateAI playing as " + playerColor + "!");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
               // System.out.println("AI TIMER: " + elapsedSeconds);

                if (elapsedSeconds == timerSeconds) {
                    System.out.println("AI TIMER: Time's up for ChessMateAI, " + playerColor + "!");
                    timeIsUp = true;
                }
            }
        }, 0, 1000);
    }
}