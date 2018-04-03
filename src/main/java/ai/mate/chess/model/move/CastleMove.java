package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;

import java.awt.*;

/* Måske burde den bare extende move? */
public final class CastleMove extends SpecialMove {

    public CastleMove(Point start, Point end) {
        super(start, end, MoveType.CASTLE);
    }

    @Override
    public void handleMove(BoardOld boardOld) {
        Tile target = boardOld.getTile(end);
        Piece rook;
        Point rookFinalPosition;

        if (target.getPosition.x > start.x) {
            // rook on the right side
            rook = boardOld.getTile(7, target.getPosition.y).getPiece();
            rookFinalPosition = new Point(target.getPosition.x - 1, target.getPosition.y);
        } else {
            // rook on the left side
            rook = boardOld.getTile(0, target.getPosition.y).getPiece();
            rookFinalPosition = new Point(target.getPosition.x + 1, target.getPosition.y);
        }

        Piece king = boardOld.getTile(start).getPiece();
        king.move(this);
        boardOld.clearTile(rook.getPosition());
        rook.setPosition(rookFinalPosition);

        // Clear positions
        boardOld.clearTile(start);
        boardOld.clearTile(target.getPosition());
        boardOld.clearTile(rookFinalPosition);

        // Kan alt dette gøres bedre? Virker lidt klunset. :D
        boardOld.getTile(target.getPosition()).setPiece(king);
        boardOld.getTile(rookFinalPosition).setPiece(rook);

        super.handleMove(boardOld);
    }


    @Override
    public void undo(BoardOld boardOld) {
        // Find the king and then move him back to start
        Piece king = boardOld.getTile(end).getPiece();

        // Find the rook, then move him either to the left end or the right end
        Piece rook;

        Tile leftFinalPosition = boardOld.getTile(new Point(end.x + 1, end.y));
        Tile rightFinalPosition = boardOld.getTile(new Point(end.x - 1 ,end.y));

        if (!leftFinalPosition.isEmpty() && leftFinalPosition.getPiece().getType == PieceType.ROOK) {
            rook = leftFinalPosition.getPiece();
            boardOld.clearTile(leftFinalPosition.getPosition());
            rook.setPosition(new Point(0, end.y));
            boardOld.getTile(0, end.y).setPiece(rook);
        } else if (!rightFinalPosition.isEmpty() && rightFinalPosition.getPiece().getType == PieceType.ROOK) {
            rook = rightFinalPosition.getPiece();
            boardOld.clearTile(rightFinalPosition.getPosition());
            rook.setPosition(new Point(7, end.y));
            boardOld.getTile(7, end.y).setPiece(rook);
        }

        boardOld.clearTile(end);
        king.setPosition(start);
        boardOld.getTile(start).setPiece(king);
    }

    @Override
    public Move copy() {
        return new CastleMove(new Point(start), new Point(end));
    }

}