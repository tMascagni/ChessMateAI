package ai.mate.chess.model.move;

import ai.mate.chess.model.board.BoardOld;
import ai.mate.chess.model.board.BoardPosition;
import ai.mate.chess.model.piece.*;

public final class SpecialMoves {

    private SpecialMoves() {

    }

    public static boolean isPromotionAllowed(Piece piece, BoardOld boardOld) {
        /* SpecialMoves is only available for Pawn pieces */
        if (!(piece instanceof Pawn))
            return false;

        /* Get position of the pawn */
        BoardPosition piecePos = boardOld.getPieceBoardPos(piece.getId());

        /* Check if the pawn is at any of the promotion squares */
        if (!(piecePos.rowX == 0 || piecePos.rowX == 7))
            return false;

        /* Since the pawn is on either x position 0 or 7, it is eligible for a promotion */
        return true;
    }

    public static Piece promotePawn(char selection, Piece.Color playerColor) {
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

    public static boolean isWhiteKingSideCastleAllowed(BoardOld boardOld) {
        /* Check if the rook is in position with moveCount of 0 */
        /* Rook pos (7, 7) */
        Piece rook = boardOld.getPiece(7, 7);

        /*
         * Check whether the gotten IPiece instance is actually a rook, and that its color is actually white,
         * and that its moveCount is actually 0.
         */
        if (!(rook instanceof Rook && rook.getColor().equals(Piece.Color.WHITE) && rook.getMoveCount() == 0))
            return false;

        /* Check if the king is in position with moveCount of 0 */
        /* King pos (7, 4) */
        Piece king = boardOld.getPiece(7, 4);

        /*
         * Check whether the gotten IPiece instance is actually a king, and that its color is actually white,
         * and that its moveCount is actually 0.
         */
        if (!(king instanceof King && king.getColor().equals(Piece.Color.WHITE) && king.getMoveCount() == 0))
            return false;


        /* Check whether the two positions between the king and rook is empty or not */
        Piece rookLeft = boardOld.getPiece(7, 6);

        if (!(rookLeft instanceof Empty))
            return false;

        Piece kingRight = boardOld.getPiece(7, 5);

        if (!(kingRight instanceof Empty))
            return false;


        /* Fri for trusler huehue */

        return true;
    }

    public static boolean isWhiteQueenSideCastleAllowed(BoardOld boardOld) {
        /* Check if the rook is in position with moveCount of 0 */
        /* Rook pos (7, 7) */
        Piece rook = boardOld.getPiece(7, 0);

        /*
         * Check whether the gotten IPiece instance is actually a rook, and that its color is actually white,
         * and that its moveCount is actually 0.
         */
        if (!(rook instanceof Rook && rook.getColor().equals(Piece.Color.WHITE) && rook.getMoveCount() == 0))
            return false;

        /* Check if the king is in position with moveCount of 0 */
        /* King pos (7, 4) */
        Piece king = boardOld.getPiece(7, 4);

        /*
         * Check whether the gotten IPiece instance is actually a king, and that its color is actually white,
         * and that its moveCount is actually 0.
         */
        if (!(king instanceof King && king.getColor().equals(Piece.Color.WHITE) && king.getMoveCount() == 0))
            return false;

        /* Check whether the tree positions between the king and rook is empty or not */
        Piece kingLeft1 = boardOld.getPiece(7, 3);
        if (!(kingLeft1 instanceof Empty))
            return false;

        Piece kingLeft2 = boardOld.getPiece(7, 2);
        if (!(kingLeft2 instanceof Empty))
            return false;

        Piece kingLeft3 = boardOld.getPiece(7, 1);
        if (!(kingLeft3 instanceof Empty))
            return false;

        return true;
    }

    public static boolean isBlackKingSideCastleAllowed(BoardOld boardOld) {
        return false;
    }

    public static boolean isBlackQueenSideCastleAllowed(BoardOld boardOld) {
        return false;
    }

   /*
    private void doPromotion(IPiece fromPiece, BoardPosition to) {
        if (SpecialMoves.isPromotionAllowed(fromPiece, this)) {
            tui.printPromotion();
            char selection = tui.getPromotionSelection();
            IPiece promotedPiece = SpecialMoves.promotePawn(selection, playerColor);
            setPiece(promotedPiece, to.rowX, to.colY);
            tui.printPromotionSuccess(selection, playerColor);
        }
    }
    */

}