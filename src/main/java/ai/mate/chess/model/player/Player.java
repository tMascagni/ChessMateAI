package ai.mate.chess.model.player;

import ai.mate.chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private Piece.PlayerColor playerColor;
    private List<Piece> pieceList;
    private List<Piece> deadPieceList;

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

    public void addDeadPiece(Piece deadPiece) {
        deadPieceList.add(deadPiece);
    }

    public void removeDeadPiece(Piece deadPiece) {
        deadPieceList.remove(deadPiece);
    }

    public Piece.PlayerColor getPlayerColor() {
        return playerColor;
    }

    public int getPieceCount() {
        return pieceList.size();
    }

    public int getDeadPieceCount() {
        return deadPieceList.size();
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    public List<Piece> getDeadPieceList() {
        return deadPieceList;
    }

    @Override
    public String toString() {
        return playerColor.toString();
    }

}