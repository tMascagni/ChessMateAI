package ai.mate.chess.model.player;

import ai.mate.chess.model.piece.Piece;

import java.util.ArrayList;

public final class Player {

    Piece.PlayerColor playerColor;
    public ArrayList<Piece> pieces;

    public Player(Piece.PlayerColor playerColor) {
        this.pieces = new ArrayList<>();
        this.playerColor = playerColor;
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public Piece.PlayerColor getPlayerColor() {
        return playerColor;
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
    }

    @Override
    public String toString() {
        return playerColor.toString();
    }

}