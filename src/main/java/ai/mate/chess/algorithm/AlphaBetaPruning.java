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

    private int moveCountPly0 = 1;
    private int moveCountPly1;
    private int moveCountPly2;
    private int moveCountPly3;
    private int moveCountPly4;

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

        this.moveCountPly0 = 0;
        this.moveCountPly1 = 0;
        this.moveCountPly2 = 0;
        this.moveCountPly3 = 0;
        this.moveCountPly4 = 0;

        this.elapsedSeconds = 0;
        this.staticEvalCount = 0;

        if (IS_AI_TIMER_ENABLED) {
            timer = new Timer();
            startTimer(AIColor);
        }

        //for (int i = 1; i <= maxPly; i++) {
        //    targetPly = i;
        //    System.out.println("i: " + i + " Static eval: " + staticEvalCount);
        alphaBetaPruning(board, AIColor, AIColor, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 0);
        //}

        if (IS_AI_TIMER_ENABLED) {
            timer.cancel();
        }

        System.out.println("AI PLAYER: Ply 0 Move Count: " + moveCountPly0);
        System.out.println("AI PLAYER: Ply 1 Move Count: " + moveCountPly1);
        System.out.println("AI PLAYER: Ply 2 Move Count: " + moveCountPly2);
        System.out.println("AI PLAYER: Ply 3 Move Count: " + moveCountPly3);
        System.out.println("AI PLAYER: Ply 4 Move Count: " + moveCountPly4);

        System.out.println("AI PLAYER: Best move found!");
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
    private double alphaBetaPruning(Board board, Piece.PlayerColor playerToMove, Piece.PlayerColor AIColor, double alpha, double beta, int currentPly) {
        boolean isMaximizer = playerToMove == AIColor;

        /*
         * Returns the score whether we've reached
         * the maximum ply or if the time is up for the
         * algorithm.
         */
        if (currentPly++ == maxPly || timeIsUp)
            return getScore(playerToMove, AIColor, board, currentPly);

        switch (currentPly) {
            case 0:
                moveCountPly0 += getPossibleMoveCount(board, AIColor);
                break;
            case 1:
                moveCountPly1 += getPossibleMoveCount(board, AIColor);
                break;
            case 2:
                moveCountPly2 += getPossibleMoveCount(board, AIColor);
                break;
            case 3:
                moveCountPly3 += getPossibleMoveCount(board, AIColor);
                break;
            case 4:
                moveCountPly4 += getPossibleMoveCount(board, AIColor);
                break;
        }

        List<Move> moves = getAllPossibleMoves(board, playerToMove);

        if (isMaximizer) {
            Move localBestMoveMaximizer = null;
            for (Move move : moves) {
                Board copyBoard = new Board(board);
                move.handleMove(copyBoard);
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerToMove), AIColor, alpha, beta, currentPly);
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
                double score = alphaBetaPruning(copyBoard, ChessUtils.changePlayer(playerToMove), AIColor, alpha, beta, currentPly);
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
                System.out.println("AI TIMER: " + elapsedSeconds);

                if (elapsedSeconds == timerSeconds) {
                    System.out.println("AI TIMER: Time's up for ChessMateAI, " + playerColor + "!");
                    timeIsUp = true;
                }
            }
        }, 0, 1000);
    }

    private int getPossibleMoveCount(Board board, Piece.PlayerColor playerColor) {
        int sumMoves = 0;
        for (Tile[] tiles : board.getBoard())
            for (Tile tile : tiles)
                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    if (piece.getPlayerColor() == playerColor)
                        sumMoves += piece.getAvailableMoves(board).size();
                }
        return sumMoves;
    }

}