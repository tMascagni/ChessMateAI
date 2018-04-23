package ai.mate.chess.model.move;

import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public final class EnPassantMove extends AttackMove {

    public EnPassantMove(Point from, Point to) {
        super(from, to, MoveType.EN_PASSANT);
    }

    @Override
    public void handleMove(Board board) {
        int direction = board.getTile(getFrom()).getPiece().getPlayerColor() == Piece.PlayerColor.WHITE ? 1 : -1;
        Piece attackedPiece = board.getTile(getTo().x, getTo().y + direction).getPiece();
        board.getTile(attackedPiece.getPosition()).setPiece(null);
        GameController.getInstance().removePieceFromGame(this, attackedPiece);
        board.handleMove(this);
    }

    @Override
    public Tile.TileHighlight getTileHighlight() {
        return Tile.TileHighlight.YELLOW;
    }

    @Override
    public void undo(Board board) {
        Piece attacker = board.getTile(getTo()).getPiece();
        board.clearTile(getTo());
        attacker.setPosition(getFrom());
        board.getTile(getFrom()).setPiece(attacker);

        Piece attacked = GameController.getInstance().getDeadPieceFromMove(this);
        int direction = board.getTile(getFrom()).getPiece().getPlayerColor() == Piece.PlayerColor.WHITE ? 1 : -1;
        attacked.setPosition(new Point(getTo().x, getTo().y + direction));
        board.getTile(getTo().x, getTo().y + direction).setPiece(attacked);
    }

    @Override
    public Move copy() {
        return new EnPassantMove(new Point(getFrom()), new Point(getTo()));
    }

}