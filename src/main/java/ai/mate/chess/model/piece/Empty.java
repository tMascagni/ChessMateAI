package ai.mate.chess.model.piece;

import ai.mate.chess.handler.TextHandler;

public final class Empty extends Piece {

    public Empty() {
        super(Color.WHITE);
    }

    @Override
    protected void initName() {
        name = TextHandler.EMPTY;
    }

}