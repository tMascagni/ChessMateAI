package ai.mate.chess.model.player;

import ai.mate.chess.model.piece.Piece;

import java.util.ArrayList;
import java.util.List;

public final class Player {

    private final Piece.PlayerColor color;
    private final List<Piece> pieceList;

    public Player(Piece.PlayerColor color) {
        this.color = color;
        this.pieceList = new ArrayList<>();
    }

    public void addPiece(Piece piece) {
        pieceList.add(piece);
    }

    public void removePiece(Piece piece) {
        pieceList.remove(piece);
    }

    public Piece.PlayerColor getColor() {
        return color;
    }

    public List<Piece> getPieceList() {
        return pieceList;
    }

    @Override
    public String toString() {
        return "Player " + color.name();
    }

}