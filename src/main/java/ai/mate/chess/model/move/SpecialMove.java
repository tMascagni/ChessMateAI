package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Tile;

import java.awt.*;

public abstract class SpecialMove extends Move {

    SpecialMove(Point from, Point to, MoveType moveType) {
        super(from, to, moveType);
    }

    @Override
    public Tile.TileHighlight getTileHighlight() {
        return Tile.TileHighlight.YELLOW;
    }

}