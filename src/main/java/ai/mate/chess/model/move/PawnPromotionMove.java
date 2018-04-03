package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

public final class PawnPromotionMove extends NormalMove {

    public PawnPromotionMove(Point start, Point end) {
        super(start, end, MoveType.PAWN_PROMOTION);
    }

    @Override
    public void undo(BoardOld boardOld) {
        Tile starting = boardOld.getTile(start);
        Tile ending = boardOld.getTile(end);

        // Get the promoted piece
        Piece promoted = ending.getPiece();

        // Clear the ending tile
        ending.setPiece(null);
        // make this empty and not null :)

        // Set starting to the pawn
        Pawn pawn = new Pawn(promoted.getTeam, promoted.getPosition());
        starting.setPiece(pawn);
    }

    @Override
    public Move copy() {
        return new PawnPromotionMove(new Point(start), new Point(end));
    }

}