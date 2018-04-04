package ai.mate.chess.model.board;

import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class Tile {

    private final TileColor tileColor;
    private TileHighlight tileHighlight;

    private final Point position;
    private Move move;
    private Piece piece;

    public enum TileColor {
        WHITE,
        BLACK,
    }

    public enum TileHighlight {
        NONE,
        BLUE,
        YELLOW,
        RED,
        GREEN,
        ORANGE
    }

    public Tile(Point position) {
        this.tileColor = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TileColor.WHITE : TileColor.BLACK;
        this.tileHighlight = TileHighlight.NONE;
        this.position = position;
        this.move = null;
        this.piece = null;
    }

    public Tile(Tile tile) {
        this.tileColor = tile.tileColor;
        this.tileHighlight = tile.tileHighlight;
        this.position = new Point(tile.position);

        if (tile.move != null)
            move = tile.move.copy();

        if (!tile.isEmpty())
            piece = tile.getPiece().copy();
    }

    public Tile(Piece piece) {
        this.piece = piece;
        this.position = piece.getPosition();
        this.tileColor = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TileColor.WHITE : TileColor.BLACK;
        this.tileHighlight = TileHighlight.NONE;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    public boolean isHighlighted() {
        return tileHighlight != TileHighlight.NONE;
    }

    public TileColor getTileColor() {
        return tileColor;
    }

    public TileHighlight getTileHighlight() {
        return tileHighlight;
    }

    public Point getPosition() {
        return position;
    }

    public Move getMove() {
        return move;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setTileHighlight(TileHighlight tileHighlight) {
        this.tileHighlight = tileHighlight;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    @Override
    public String toString() {
        String team = " ";

        if (!isEmpty())
            team = piece.getPlayerColor() == Piece.PlayerColor.BLACK ? "b" : "w";

        return piece == null ? team + "NULL" : team + piece.getPieceType().toString();
    }

    public Tile copy() {
        return new Tile(this);
    }

}