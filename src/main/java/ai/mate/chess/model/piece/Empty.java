package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

import java.awt.*;

public final class Empty extends Piece {

    public Empty() {
        super(Color.WHITE);
    }

    protected boolean isValidMove(Point from, Point to) {
        return false;
    }

    @Override
    protected void initName() {
        name = TextHandler.EMPTY;
    }

}