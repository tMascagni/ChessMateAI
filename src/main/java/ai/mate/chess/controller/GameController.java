package ai.mate.chess.controller;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.model.player.Player;

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

    private GameController() {
        this.white = new Player(Piece.PlayerColor.WHITE);
        this.black = new Player(Piece.PlayerColor.BLACK);
        this.currentPlayer = white;
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
        this.currentPlayer = white;
        this.board = board;
        findAndUpdateKingPosition(this.board);
        this.selectedPiece = null;
        this.gameOver = false;
        this.winner = null;
        this.deadPieces = new ArrayList<>();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }

        return instance;
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
    public boolean inCheck(Piece.PlayerColor playerColor) {
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
        if (white.pieces.contains(piece)) {
            white.removePiece(piece);
        } else if (black.pieces.contains(piece)) {
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
                return log.attackMove.start == this.attackMove.start &&
                        log.attackMove.end == this.attackMove.end &&
                        log.piece.getPieceType() == this.piece.getPieceType() &&
                        log.piece.getPlayerColor() == this.piece.getPlayerColor();
            }

            return false;
        }
    }
}
