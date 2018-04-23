package ai.mate.chess.model.move;

import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Pawn;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class PawnPromotionMove extends NormalMove {

    public PawnPromotionMove(Point from, Point to) {
        super(from, to, MoveType.PAWN_PROMOTION);
    }

    @Override
    public Tile.TileHighlight getTileHighlight() {
        return Tile.TileHighlight.YELLOW;
    }

    @Override
    public void undo(Board board) {
        Tile from = board.getTile(getFrom());
        Tile to = board.getTile(getTo());

        // Get the promoted piece
        Piece promoted = to.getPiece();

        // Clear the to tile
        to.setPiece(null);

        // Set starting to the pawn
        Pawn pawn = new Pawn(promoted.getPlayerColor(), promoted.getPosition());
        from.setPiece(pawn);
    }

    @Override
    public Move copy() {
        return new PawnPromotionMove(new Point(getFrom()), new Point(getTo()));
    }

}