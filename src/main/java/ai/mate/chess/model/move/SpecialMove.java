package ai.mate.chess.model.move;

import java.awt.*;

public abstract class SpecialMove extends Move {

    public SpecialMove(Point start, Point end, MoveType type) {
        super(start, end, type);
    }

}
