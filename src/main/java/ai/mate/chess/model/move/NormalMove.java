package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

public class NormalMove extends Move {

    public NormalMove(Point start, Point end) {
        super(start, end, MoveType.NORMAL);
    }

    public NormalMove(Point start, Point end, MoveType type) {
        super(start, end, type);
    }

    @Override
    public void undo(BoardOld boardOld) {
        Tile to = boardOld.getTile(end);
        Piece movedPiece = to.getPiece();
        movedPiece.setPosition(start);
        boardOld.getTile(start).setPiece(movedPiece);
        to.setPiece(null); // pls change, make new Empty piece for this.
    }

    @Override
    public Move copy() {
        return new NormalMove(new Point(start), new Point(end), type);
    }

}
