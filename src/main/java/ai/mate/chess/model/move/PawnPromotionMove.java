package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Pawn;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class PawnPromotionMove extends NormalMove {

    public PawnPromotionMove(Point start, Point end) {
        super(start, end, MoveType.PAWN_PROMOTION);
    }

    @Override
    public Tile.TILE_HIGHLIGHT getTileHighlight() {
        return Tile.TILE_HIGHLIGHT.YELLOW;
    }

    @Override
    public void undo(Board board) {
        Tile starting = board.getTile(start);
        Tile ending = board.getTile(end);

        // Get the promoted piece
        Piece promoted = ending.getPiece();

        // Clear the ending tile
        ending.setPiece(null);

        // Set starting to the pawn
        Pawn pawn = new Pawn(promoted.getPlayerColor(), promoted.getPosition());
        starting.setPiece(pawn);
    }

    @Override
    public Move copy() {
        return new PawnPromotionMove(new Point(this.start), new Point(this.end));
    }
}
