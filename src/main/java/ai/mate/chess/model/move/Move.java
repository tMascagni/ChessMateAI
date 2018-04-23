package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;

import java.awt.*;

public abstract class Move {

    private final Point from;
    private final Point to;
    private final MoveType moveType;

    public enum MoveType {
        NORMAL,
        NORMAL_DOUBLE,
        ATTACK,
        EN_PASSANT,
        CASTLE,
        PAWN_PROMOTION
    }

    public Move(Point from, Point to, MoveType moveType) {
        this.from = from;
        this.to = to;
        this.moveType = moveType;
    }

    public void handleMove(Board board) {
        board.handleMove(this);
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

    public MoveType getMoveType() {
        return this.moveType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            Move move = (Move) obj;
            return from == move.from && to == move.to && moveType == move.moveType;
        }
        return false;
    }

    @Override
    public String toString() {
        return "From: (" + from.x + ", " + from.y + ") To: (" + to.x + ", " + to.y + ")";
    }

    public abstract Tile.TileHighlight getTileHighlight();

    public abstract void undo(Board board);

    public abstract Move copy();

}