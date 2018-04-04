package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public class NormalMove extends Move {

    public NormalMove(Point from, Point to) {
        super(from, to, MoveType.NORMAL);
    }

    public NormalMove(Point from, Point to, MoveType moveType) {
        super(from, to, moveType);
    }

    @Override
    public Tile.TileHighlight getTileHighlight() {
        return Tile.TileHighlight.BLUE;
    }

    @Override
    public void undo(Board board) {
        Tile to = board.getTile(getTo());
        Piece movedPiece = to.getPiece();
        movedPiece.setPosition(getFrom());
        board.getTile(getFrom()).setPiece(movedPiece);
        to.setPiece(null);
    }

    @Override
    public Move copy() {
        return new NormalMove(new Point(getFrom()), new Point(getTo()), this.getMoveType());
    }

}