package ai.mate.chess.model.board;

import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class Tile {

    private final Point position;
    private final TileType type;

    private Piece piece;
    private Move move;

    public enum TileType {
        WHITE, BLACK
    }

    public Tile(Point position) {
        this.position = position;
        this.type = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TileType.WHITE : TileType.BLACK;
        this.piece = null; // Change this to empty piece.
        this.move = null;
    }

    public Tile(Tile tile) {
        if (!tile.isEmpty())
            this.piece = tile.getPiece().copy;

        if (tile.move != null)
            this.move = tile.move.copy();

        this.type = tile.type;
        this.position = new Point(tile.position);
    }

    public Tile(Piece piece) {
        this.piece = piece;
        this.position = piece.getPosition();
        this.type = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TileType.WHITE : TileType.BLACK;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public Point getPosition() {
        return position;
    }

    public TileType getType() {
        return type;
    }

    public Piece getPiece() {
        return piece;
    }

    public Move getMove() {
        return move;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                ", type=" + type +
                ", piece=" + piece +
                ", move=" + move +
                '}';
    }

    public Tile copy() {
        return new Tile(this);
    }

}