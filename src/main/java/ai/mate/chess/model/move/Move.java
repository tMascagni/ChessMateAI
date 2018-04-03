package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

public abstract class Move {

    protected Point start;
    protected Point end;
    protected MoveType type;

    public enum MoveType {
        NORMAL,
        NORMAL_DOUBLE,
        ATTACK,
        EN_PASSANT,
        CASTLE,
        PAWN_PROMOTION
    }

    public Move(Point start, Point end, MoveType type) {
        this.start = start;
        this.end = end;
        this.type = type;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public MoveType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Move) {
            Move move = (Move) obj;
            return start == move.start && end == move.end && type == move.type;
        }
        return false;
    }

    public void handleMove(BoardOld boardOld) {
        boardOld.handleMove(this);
    }

    @Override
    public String toString() {
        return "Move{" +
                "start=" + start +
                ", end=" + end +
                ", type=" + type +
                '}';
    }

    public abstract void undo(BoardOld boardOld);

    public abstract Move copy();

}