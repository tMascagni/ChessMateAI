package ai.mate.chess.model.move;

import ai.mate.chess.controller.GameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.board.Tile;
import ai.mate.chess.model.piece.Piece;

import java.awt.*;

public class AttackMove extends Move {

    public AttackMove(Point from, Point to) {
        super(from, to, MoveType.ATTACK);
    }

    public AttackMove(Point from, Point to, MoveType moveType) {
        super(from, to, moveType);
    }

    @Override
    public void handleMove(Board board) {
        Piece attackedPiece = board.getTile(getTo()).getPiece();
        GameController.getInstance().removePieceFromGame(this, attackedPiece);
        super.handleMove(board);
    }

    @Override
    public Tile.TileHighlight getTileHighlight() {
        return Tile.TileHighlight.RED;
    }

    @Override
    public void undo(Board board) {
        Piece deadPiece = GameController.getInstance().getDeadPieceFromMove(this);
        Piece moved = board.getTile(getTo()).getPiece();

        deadPiece.setPosition(getTo());
        board.getTile(getTo()).setPiece(deadPiece);
        board.getTile(getFrom()).setPiece(moved);
    }

    @Override
    public Move copy() {
        return new AttackMove(new Point(getFrom()), new Point(getTo()), getMoveType());
    }

}