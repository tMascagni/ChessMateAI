package ai.mate.chess.model.move;

import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class EnPassantMove extends AttackMove {

    public EnPassantMove(Point start, Point end) {
        super(start, end, MoveType.ENPASSANT);
    }

    @Override
    public void handleMove(Board board) {
        int direction = board.getTile(start).getPiece().getPlayerColor() == Piece.PlayerColor.WHITE ? 1 : -1;
        Piece attackedPiece = board.getTile(end.x, end.y + direction).getPiece();
        board.getTile(attackedPiece.getPosition()).setPiece(null);
        GameController.getInstance().removePieceFromGame(this, attackedPiece);
        board.handleMove(this);
    }

    @Override
    public void undo(Board board) {
        // 1. Move the pawn that killed the other pawn to the start.
        Piece attacker = board.getTile(end).getPiece();
        board.clearTile(end);
        attacker.setPosition(start);
        board.getTile(start).setPiece(attacker);

        // 2. Move the attacked pawn back.
        Piece attacked = GameController.getInstance().getDeadPieceFromMove(this);
        int direction = board.getTile(start).getPiece().getPlayerColor() == Piece.PlayerColor.WHITE ? 1 : -1;
        attacked.setPosition(new Point(end.x, end.y + direction));
        board.getTile(end.x, end.y + direction).setPiece(attacked);
    }

    @Override
    public Move copy() {
        return new EnPassantMove(new Point(this.start), new Point(this.end));
    }

    @Override
    public Tile.TILE_HIGHLIGHT getTileHighlight() {
        return Tile.TILE_HIGHLIGHT.YELLOW;
    }

}