package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

/**
 * Class which represents a updatePiece to updatePiece a piece to an empty tile
 */

public class NormalMove extends Move {

    public NormalMove(Point start, Point end) {
        super(start, end, MoveType.NORMAL);
    }

    public NormalMove(Point start, Point end, MoveType type) {
        super(start, end, type);
    }

    @Override
    public Tile.TILE_HIGHLIGHT getTileHighlight() {
        return Tile.TILE_HIGHLIGHT.BLUE;
    }

    @Override
    public void undo(Board board) {
        Tile to = board.getTile(end);
        Piece movedPiece = to.getPiece();
        movedPiece.setPosition(start);
        board.getTile(start).setPiece(movedPiece);
        to.setPiece(null);
    }

    @Override
    public Move copy() {
        return new NormalMove(new Point(this.start), new Point(this.end), this.getType());
    }
}
