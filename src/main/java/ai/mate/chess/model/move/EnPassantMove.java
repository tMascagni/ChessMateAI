package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

public final class EnPassantMove extends SlayMove {

    public EnPassantMove(Point start, Point end) {
        super(start, end, MoveType.EN_PASSANT);
    }

    @Override
    public void handleMove(BoardOld boardOld) {
        int direction = boardOld.getTile(start).getPiece().getTeam() == Team.White ? 1 : -1;
        Piece attackedPiece = boardOld.getTile(end.x, end.y + direction).getPiece();
        boardOld.getTile(attackedPiece.getPosition).setPiece(null); // Evt lav en Empty piece i stedet for null
        GameManager.getInstance().removePieceFromGame(this.attackedPiece);
        boardOld.handleMove(this);
    }

    @Override
    public void undo(BoardOld boardOld) {
        // 1. Move the pawn that killed the other pawn to the start position
        Piece attacker = boardOld.getTile(end).getPiece();
        boardOld.clearTile(end);
        attacker.setPosition(start);
        boardOld.getTile(start).setPiece(attacker);

        // 1. Move the attacked pawn back
        Piece attacked = GameManager.getInstance().getDeadPieceFromMove(this);
        int direction = boardOld.getTile(start).getPiece().getTeam() == Team.White ? 1 : -1;
        attacked.setPosition(new Point(end.x, end.y + direction));
        boardOld.getTile(end.x, end.y + direction).setPiece(attacked);
    }

    @Override
    public Move copy() {
        return new EnPassantMove(new Point(start), new Point(end));
    }

}