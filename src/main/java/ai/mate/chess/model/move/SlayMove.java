package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

public class SlayMove extends Move {

    public SlayMove(Point start, Point end) {
        super(start, end, MoveType.ATTACK);
    }

    public SlayMove(Point start, Point end, MoveType type) {
        super(start, end, type);
    }

    @Override
    public void handleMove(BoardOld boardOld) {
        Piece attackedPiece = boardOld.getTile(end).getPiece();
        GameManager.getInstance().removePieceFromGame(this, attackedPiece);
        super.handleMove(boardOld);
    }

    @Override
    public void undo(BoardOld boardOld) {
       Piece deadPiece = GameManager.getInstance().getDeadPieceFromMove(this);
       Piece movedPiece = boardOld.getTile(end).getPiece();

       deadPiece.setPosition(end);
       boardOld.getTile(end).setPiece(deadPiece);
       boardOld.getTile(start).setPiece(movedPiece);
    }

    @Override
    public Move copy() {
        return new SlayMove(new Point(start), new Point(end));
    }

}