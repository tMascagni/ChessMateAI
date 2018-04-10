package ai.mate.chess.controller;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.model.player.Player;
import ai.mate.chess.ui.tui.Tui;

import java.awt.*;
import java.util.ArrayList;

public final class GameController {

    private static GameController instance;
    public Point whiteKingPosition;
    public Point blackKingPosition;
    private Player currentPlayer;

    private Player white;
    private Player black;

    private final Board board;

    private Piece selectedPiece;
    private ArrayList<AttackMoveLog> deadPieces;
    private boolean gameOver;
    private Piece.PlayerColor winner;

    private final Tui tui = Tui.getInstance();

    static {
        try {
            instance = new GameController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton GameController instance!");
        }
    }

    private GameController() {
        this.white = new Player(Piece.PlayerColor.WHITE);
        this.black = new Player(Piece.PlayerColor.BLACK);
        this.currentPlayer = null;
        this.board = new Board(white, black);
        this.blackKingPosition = new Point(4, 0);
        this.whiteKingPosition = new Point(4, 7);
        this.gameOver = false;
        this.winner = null;
        this.deadPieces = new ArrayList<>();
        this.selectedPiece = null;
    }

    private GameController(Board board) {
        this.white = new Player(Piece.PlayerColor.WHITE);
        this.black = new Player(Piece.PlayerColor.BLACK);
        this.currentPlayer = null;
        this.board = board;
        findAndUpdateKingPosition(this.board);
        this.selectedPiece = null;
        this.gameOver = false;
        this.winner = null;
        this.deadPieces = new ArrayList<>();
    }

    public static synchronized GameController getInstance() {
        return instance;
    }

    public void start() {
        tui.printChoosePlayer();
        Piece.PlayerColor playerColor = tui.getPlayerColorInput();

        tui.printHumanPlayer(playerColor);
        tui.printAIPlayer(getOpponentColor());


        while (true) {
            tui.printBoard(board);

            /*
             * Check whether the fromPosition is valid.
             */
            while (true) {
                Point fromPos = tui.getBoardPosition("From");

                // evaulate whether this is a correct from position or not.

                if (!isValidFromPosition(fromPos))
                    continue;

                // Select the piece
                Tile fromTile = board.getTile(fromPos);
                setSelectedPiece(fromTile.getPiece());

                // then get the to Position
                Point toPos = tui.getBoardPosition("To");
                Tile toTile = board.getTile(toPos);

                // get 'to' move

                Move toMove = toTile.getMove();

                board.handleMove(toMove);

/*
                if (board.isValidFromPos(fromPos))
                    break;

                tui.printIllegalAction("Try again!");
                System.out.println();
  */
            }

            //        BoardPosition toPos = tui.getBoardPositionInput("To");
            //      board.movePiece(fromPos, toPos);
        }

    }

    public void setCurrentPlayer(Piece.PlayerColor playerColor) {
        if (playerColor == Piece.PlayerColor.WHITE)
            this.currentPlayer = white;
        else
            this.currentPlayer = black;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    /**
     * Finds and updates the kings position
     * Only used initially
     *
     * @param board
     */
    private void findAndUpdateKingPosition(Board board) {
        for (Tile[] tiles : board.getBoard()) {
            for (Tile tile : tiles) {
                if (!tile.isEmpty()) {
                    Piece piece = tile.getPiece();
                    if (piece.getPieceType() == Piece.PieceType.KING) {
                        updateKingPosition(piece);
                    }
                }
            }
        }
    }

    /**
     * Checks if a player is in check
     *
     * @param playerColor
     * @return
     */
    public boolean isInCheck(Piece.PlayerColor playerColor) {
        Point kingPosition = playerColor == Piece.PlayerColor.WHITE ? whiteKingPosition : blackKingPosition;
        // If the tile of the players king is threatened, then we are in check.
        return board.tileIsThreatened(playerColor, board.getTile(kingPosition));
    }

    /**
     * Removes the piece from the game
     *
     * @param move  Attack updatePiece that killed the piece
     * @param piece Piece to remove
     */
    public void removePieceFromGame(Move move, Piece piece) {
        if (white.getPieceList().contains(piece)) {
            white.removePiece(piece);
        } else if (black.getPieceList().contains(piece)) {
            black.removePiece(piece);
        }

        deadPieces.add(new AttackMoveLog(move, piece));
        piece.setPosition(new Point(-1, -1));
    }

    /**
     * Gets a killed piece from updatePiece. Used to undo attack moves
     *
     * @param move Attack updatePiece
     * @return Killed piece
     */
    public Piece getDeadPieceFromMove(Move move) {
        for (AttackMoveLog logs : deadPieces) {
            if (logs.attackMove == move) {
                return logs.getPiece();
            }
        }

        return null;
    }

    /**
     * Unhighlights the board
     */
    public void unhighlightBoard() {
        board.unhighlightBoard();
    }

    /**
     * Sets tile at a specific point
     *
     * @param point Point to change tile
     * @param tile  Tile to change to
     */
    public void setTile(Point point, Tile tile) {
        board.setTile(point, tile);
    }

    /**
     * Changes the current player
     */
    public void nextTurn() {
        currentPlayer = currentPlayer == white ? black : white;
    }

    /**
     * Gets a tile given a pair of x and y coordinates
     *
     * @param x x-coord
     * @param y y-coord
     * @return Tile at x, y
     */
    public Tile getTile(int x, int y) {
        return getBoard().getTile(x, y);
    }

    /**
     * Gets a tile given a point
     *
     * @param pos Point on the board
     * @return Tile at point
     */
    public Tile getTile(Point pos) {
        return getBoard().getTile(pos);
    }

    /**
     * Updates the kings position, as we use game manager to track the kings when they updatePiece
     *
     * @param king King updated
     */
    public void updateKingPosition(Piece king) {
        if (king.getPlayerColor() == Piece.PlayerColor.WHITE) {
            this.whiteKingPosition = king.getPosition();
        } else {
            this.blackKingPosition = king.getPosition();
        }
    }

    /**
     * Checks whether the game is over
     *
     * @return true if game is over, false otherwise
     */
    public boolean isGameOver() {
        return gameOver;
    }

    public Piece.PlayerColor getOpponentColor() {
        return getCurrentPlayer().getPlayerColor() == Piece.PlayerColor.WHITE ? Piece.PlayerColor.BLACK : Piece.PlayerColor.WHITE;
    }

    public Player getOpponentPlayer() {
        return getCurrentPlayer().getPlayerColor() == Piece.PlayerColor.WHITE ? black : white;
    }

    /**
     * Ends the game, sets the winner
     *
     * @param winner Winner of the game
     */
    public void endGame(Piece.PlayerColor winner) {
        this.gameOver = true;
        this.winner = winner;
    }

    /**
     * Prints the winner to the console
     */
    public void printWinner() {
        System.out.println("The winner is TEAM " + winner);
    }

    private class AttackMoveLog {
        Move attackMove;
        Piece piece;

        public AttackMoveLog(Move move, Piece piece) {
            this.attackMove = move;
            this.piece = piece;
        }

        public Piece getPiece() {
            return piece;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof AttackMoveLog) {
                AttackMoveLog log = (AttackMoveLog) obj;
                return log.attackMove.getFrom() == this.attackMove.getFrom() &&
                        log.attackMove.getTo() == this.attackMove.getTo() &&
                        log.piece.getPieceType() == this.piece.getPieceType() &&
                        log.piece.getPlayerColor() == this.piece.getPlayerColor();
            }

            return false;
        }
    }

    private boolean isValidFromPosition(Point fromPos) {
        // 1. The tile has to have a piece.

        Piece fromPosPiece = board.getTile(fromPos).getPiece();

        if (fromPosPiece == null) {
            System.out.println("DEBUG: The fromPosition has no piece.");
            return false;
        }

        // 2. The piece has to be the same players color as the current player
        if (getCurrentPlayer().getPlayerColor() != fromPosPiece.getPlayerColor()) {
            System.out.println("DEBUG: You are not able to move the other players piece!");
            return false;
        }

        // 3. The king of this player can not be in check
        // Lav noget logik som gør at man kun kan flytte brikker der beskytter den der konge
        if (isInCheck(currentPlayer.getPlayerColor())) {
            System.out.println("DEBUG: You cant move this piece when your king is in check!");
            return false;
        }

        return true;
    }

    public Point getKingPosition(Piece.PlayerColor playerColor) {
        if (playerColor == Piece.PlayerColor.WHITE) {
            return whiteKingPosition;
        } else {
            return blackKingPosition;
        }
    }

}