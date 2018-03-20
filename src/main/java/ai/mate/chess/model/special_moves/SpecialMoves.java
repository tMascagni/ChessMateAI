package ai.mate.chess.model.special_moves;

import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.*;

public final class SpecialMoves {

    private SpecialMoves() {

    }

    public static boolean isPromotionAllowed(IPiece piece, Board board) {
        /* SpecialMoves is only available for Pawn pieces */
        if (!(piece instanceof Pawn))
            return false;

        /* Get position of the pawn */
        BoardPosition piecePos = board.getPieceBoardPos(piece.getId());

        /* Check if the pawn is at any of the promotion squares */
        if (!(piecePos.arrayX == 0 || piecePos.arrayX == 7))
            return false;

        /* Since the pawn is on either x position 0 or 7, it is eligible for a promotion */
        return true;
    }

    public static IPiece promotePawn(char selection, IPiece.Color playerColor) {
        switch (selection) {
            case '1':
                return new Queen(playerColor);
            case '2':
                return new Knight(playerColor);
            case '3':
                return new Rook(playerColor);
            case '4':
                return new Bishop(playerColor);
            default:
                return new Empty();
        }
    }

}