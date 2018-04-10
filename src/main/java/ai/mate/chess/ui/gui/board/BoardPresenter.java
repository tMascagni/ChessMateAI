package ai.mate.chess.ui.gui.board;

import ai.mate.chess.algorithm.AlphaBetaPruning;
import ai.mate.chess.controller.Game;
import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Pawn;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.model.piece.Queen;
import ai.mate.chess.model.player.Player;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import static ai.mate.chess.model.move.Move.MoveType.ATTACK;
import static ai.mate.chess.model.move.Move.MoveType.PAWN_PROMOTION;
import static ai.mate.chess.model.piece.Piece.PieceType.KING;

public class BoardPresenter implements BoardGUIContract.Presenter {

    private final BoardGUIContract.View view;
    private final GameController gameController;
    private final Game game;
    private final AlphaBetaPruning alphaBetaPruning;

    private int elapsedSeconds;
    private Timer timer;

    private static final int MAX_TURN_SECONDS = 10;

    public BoardPresenter(BoardGUIContract.View view, GameController gameController, Game game, Piece.PlayerColor humanPlayerColor) {
        this.gameController = gameController;
        this.view = view;
        this.game = game;
        this.alphaBetaPruning = new AlphaBetaPruning(4, MAX_TURN_SECONDS);
        view.setPresenter(this);
        timer = new Timer();

        /*
         * Let white make its first AI move
         * if the human plays as black.
         */
        if (humanPlayerColor == Piece.PlayerColor.BLACK) {
            handleAIMove(Piece.PlayerColor.WHITE);
            view.updateBoard(gameController.getBoard());
        }
    }

    @Override
    public void start() {
        loadBoard();
    }

    @Override
    public void loadBoard() {
        view.setBoard(gameController.getBoard());
        view.showView();
    }

    @Override
    public void pause() {
        view.setEnabled(false);
        view.showOverlay();
    }

    @Override
    public void unpause() {
        view.setEnabled(true);
        view.hideOverlay();
        view.updateBoard(gameController.getBoard());
    }

    @Override
    public void handleClickedTile(Tile tile) {
        if (gameController.isGameOver())
            return;

        timer = new Timer();
        elapsedSeconds = 0;

        if (!tile.isEmpty() && isClickablePiece(tile)) {
            startTimer(gameController.getCurrentPlayer().getPlayerColor());
            // It means they clicked a tile with a piece and it's not highlighted
            // Or they clicked the king, who is in check
            boolean inCheck = tile.getTileHighlight() == Tile.TileHighlight.ORANGE;
            gameController.unhighlightBoard();
            Player currentPlayer = gameController.getCurrentPlayer();
            Piece piece = tile.getPiece();

            //System.out.println("InCheck: " + tile + ": " + inCheck);
            if (inCheck) {
                System.out.println(gameController.getCurrentPlayer().getPlayerColor() + " KING IS IN CHECK!");
                if (piece.getPieceType() != KING) {
                    // If they clicked a piece thats not a king while in check, don't show the moves
                    System.out.println("You can't move Piece other pieces except your King while in check!");
                    return;
                } else if (piece.getPieceType() == KING && piece.getAvailableMoves(gameController.getBoard()).isEmpty()) {
                    handleGameOver(gameController.getOpponentColor());
                    return;
                }
            }

            if (currentPlayer.getPlayerColor() == piece.getPlayerColor()) {
                // Only show the current user their moves
                showAvailableMoves(piece);
                gameController.setSelectedPiece(piece);
            }

            view.updateBoard(gameController.getBoard());
        } else if (tile.isHighlighted() && isMove(tile)) {
            // It means they clicked a tile with a piece that is highlighted, i.e an attacking move.
            // Or they clicked a tile without a piece that is highlighted
            Move move = tile.getMove();

            if (handleKingCaptureGameOver(move)) {
                handleGameOver(gameController.getCurrentPlayer().getPlayerColor());
                move.handleMove(gameController.getBoard());
                return;
            }

            move.handleMove(gameController.getBoard());

            // Check for pawn promotion
            if (move.getMoveType() == PAWN_PROMOTION) {
                Pawn pawn = (Pawn) gameController.getSelectedPiece();
                if (pawn.promotePawn()) {
                    game.showPawnPromotionView();
                }
            }

            gameController.unhighlightBoard();

            handleAIMove();

            if (gameController.isGameOver()) {
                // Check if the AI ended the game already
                return;
            }

            gameController.nextTurn();

            /*
            *
            *  THIS IS NOT QUITE WORKING.
             */
            // Check if the AI put the user in check.
            if (gameController.isInCheck(gameController.getCurrentPlayer().getPlayerColor())) {
                // If so, highlight the tile orange
                // Orange is a special color, only used to signify in check
                Tile kingTile = gameController.getTile(gameController.getKingPosition(gameController.getCurrentPlayer().getPlayerColor()));
                kingTile.setTileHighlight(Tile.TileHighlight.ORANGE);
            }

            view.updateBoard(gameController.getBoard());
        } else {
            gameController.unhighlightBoard();
            view.updateBoard(gameController.getBoard());
        }

    }

    private boolean handleKingCaptureGameOver(Move move) {
        if (move.getMoveType() == ATTACK) {
            if (gameController.getTile(move.getTo()).getPiece().getPieceType() == KING) {
                return true;
            }
        }

        return false;
    }

    private void handleGameOver(Piece.PlayerColor winner) {
        gameController.endGame(winner);
        gameController.printWinner();
        view.showOverlay();
    }

    private boolean isClickablePiece(Tile tile) {
        return !tile.isHighlighted() || tile.getTileHighlight() == Tile.TileHighlight.ORANGE;
    }

    private void handleAIMove() {
        Move aiMove = alphaBetaPruning.run(gameController.getBoard(), gameController.getOpponentColor());

        if (handleKingCaptureGameOver(aiMove)) {
            // The AI has captured the king
            handleGameOver(gameController.getOpponentColor());
            aiMove.handleMove(gameController.getBoard());
            return;
        } else {
            aiMove.handleMove(gameController.getBoard());
        }

        if (aiMove.getMoveType() == PAWN_PROMOTION) {
            Piece pawnToPromote = gameController.getTile(aiMove.getFrom()).getPiece();
            Piece queen = new Queen(gameController.getOpponentColor(), pawnToPromote.getPosition());
            queen.setPosition(pawnToPromote.getPosition());
            gameController.getTile(pawnToPromote.getPosition()).setPiece(queen);
        }

        gameController.nextTurn();
    }

    private void handleAIMove(Piece.PlayerColor playerColor) {
        Move aiMove = alphaBetaPruning.run(gameController.getBoard(), playerColor);

        if (handleKingCaptureGameOver(aiMove)) {
            // The AI has captured the king
            handleGameOver(playerColor);
            aiMove.handleMove(gameController.getBoard());
            return;
        } else {
            aiMove.handleMove(gameController.getBoard());
        }

        if (aiMove.getMoveType() == PAWN_PROMOTION) {
            Piece pawnToPromote = gameController.getTile(aiMove.getFrom()).getPiece();
            Piece queen = new Queen(playerColor, pawnToPromote.getPosition());
            queen.setPosition(pawnToPromote.getPosition());
            gameController.getTile(pawnToPromote.getPosition()).setPiece(queen);
        }

        gameController.nextTurn();
    }

    private void showAvailableMoves(Piece piece) {
        for (Move move : piece.getAvailableMoves(gameController.getBoard())) {
            Point movePoint = new Point(move.getTo().x, move.getTo().y);
            Tile startTile = gameController.getTile(piece.getPosition());
            startTile.setTileHighlight(Tile.TileHighlight.GREEN);
            Tile target = gameController.getTile(movePoint);
            target.setTileHighlight(move.getTileHighlight());
            target.setMove(move);
            gameController.setTile(movePoint, target);
        }
    }

    public boolean isMove(Tile tile) {
        return tile.getTileHighlight() != Tile.TileHighlight.GREEN && tile.getTileHighlight() != Tile.TileHighlight.ORANGE;
    }

    private void startTimer(Piece.PlayerColor playerColor) {
        System.out.println("HUMAN TIMER: New timer for " + playerColor + "!");
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                elapsedSeconds++;
                System.out.println("HUMAN TIMER: " + elapsedSeconds);

                if (elapsedSeconds == MAX_TURN_SECONDS) {
                    System.out.println("HUMAN TIMER: Time's up for " + playerColor + "!");
                    System.out.println("You lost!");
                    handleGameOver(GameController.getInstance().getOpponentColor());
                    this.cancel();
                }
            }
        }, 0, 1000);
    }

}