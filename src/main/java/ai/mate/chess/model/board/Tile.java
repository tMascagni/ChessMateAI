package ai.mate.chess.model.board;

import ai.mate.chess.model.move.Move;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class Tile {

    public Tile copy() {
        return new Tile(this);
    }

    public enum TILE_TYPE {
        LIGHT,
        DARK,
    }

    public enum TILE_HIGHLIGHT {
        NONE,
        BLUE,
        YELLOW,
        RED,
        GREEN,
        ORANGE
    }

    private final Point position;
    private TILE_TYPE color;
    private TILE_HIGHLIGHT highlight;
    private Move move;

    private Piece piece;

    public Tile(Point position) {
        this.piece = null;
        this.position = position;
        this.color = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TILE_TYPE.LIGHT : TILE_TYPE.DARK;
        this.highlight = TILE_HIGHLIGHT.NONE;
        this.move = null;
    }

    public Tile(Tile tile) {
        if (!tile.isEmpty()) {
            this.piece = tile.getPiece().copy();
        }
        if (tile.move != null) {
            this.move = tile.move.copy();
        }
        this.color = tile.color;
        this.position = new Point(tile.position);
        this.highlight = tile.highlight;
    }

    public Tile(Piece piece) {
        this.piece = piece;
        this.position = piece.getPosition();
        this.color = (position.x % 2 == 0 && position.y % 2 == 0
                || position.x % 2 == 1 && position.y % 2 == 1) ? TILE_TYPE.LIGHT : TILE_TYPE.DARK;
        this.highlight = TILE_HIGHLIGHT.NONE;
    }

    public TILE_TYPE getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }

    public boolean isEmpty() {
        return this.piece == null;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public TILE_HIGHLIGHT getHighlight() {
        return highlight;
    }

    public void setHighlight(TILE_HIGHLIGHT highlight) {
        this.highlight = highlight;
    }

    public boolean isHighlighted() {
        return this.highlight != TILE_HIGHLIGHT.NONE;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    @Override
    public String toString() {
        String team = " ";
        if (!isEmpty()) {
            team = this.piece.getPlayerColor() == Piece.PlayerColor.BLACK ? "b" : "w";
        }
        return this.piece == null ? team + "NULL" : team + this.piece.getPieceType().toString();
    }

}