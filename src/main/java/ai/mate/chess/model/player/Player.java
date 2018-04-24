package ai.mate.chess.model.player;

import ai.mate.chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private Piece.PlayerColor playerColor;
    private List<Piece> pieceList;

    public Player(Piece.PlayerColor playerColor) {
        this.pieceList = new ArrayList<>();
        this.playerColor = playerColor;
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public void removePiece(Piece piece) {
        pieceList.remove(piece);
    }

    public Piece.PlayerColor getPlayerColor() {
        return playerColor;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    @Override
    public String toString() {
        return playerColor.toString();
    }

}