package ai.mate.chess.model;

import ai.mate.chess.model.piece.*;

public final class Board {

    private IPiece[][] pos = {
            {new Rook(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Queen(IPiece.Color.BLACK), new King(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Rook(IPiece.Color.BLACK)},
            {new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK)},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
            {new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE)},
            {new Rook(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Queen(IPiece.Color.WHITE), new King(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Rook(IPiece.Color.WHITE)}
    };

    public Board() {

    }

    public boolean movePiece(IPiece.Color playerColor, BoardPosition from, BoardPosition to) {
        /* Get both pieces */
        IPiece fromPiece = getPiece(from.arrayX, from.arrayY);
        IPiece toPiece = getPiece(to.arrayX, to.arrayY);

        if (!fromPiece.getColor().equals(playerColor)) {
            System.out.println("Can't move opponents piece");
            return false;
        }


        System.out.println("fromPiece: " + fromPiece.toString());
        System.out.println("toPiece: " + toPiece.toString());
        System.out.println("Player: " + playerColor);
        System.out.println();


        System.out.println("isValidMove: " + fromPiece.isValidMove(from, to));


        if (fromPiece instanceof Empty) {
            System.out.println("No piece at: " + fromPiece.toString());
            return false;
        }

        if (toPiece instanceof Empty) {
            /* Move fromPiece to to's x and y coordinates */
            setPiece(fromPiece, to.arrayX, to.arrayY);
        } else if (toPiece.getColor().equals(playerColor)) {
            /* If the player is trying to move a piece onto one of his own, it is illegal
             *  and false is returned. */
            System.out.println("Illegal move: Cannot move to one's own piece.");
            return false;
        } else if (!toPiece.getColor().equals(playerColor)) {
            /* kill piece :) */
            // add the slain piece to a list / keep track of the slain piece
            setPiece(fromPiece, to.arrayX, to.arrayY);
        }

        /* Put new empty piece at the previous position of from. Aka remove old from piece. */
        setPiece(new Empty(), from.arrayX, from.arrayY);

        return true;
    }

    public final IPiece[][] getPositionMatrix() {
        return pos;
    }

    public final String getBoard() {
        return "\n┌───────────────────────────────────────────────────────────────┐\n" +
                "│          A     B     C     D     E     F     G     H          │\n" +
                "│                                                               │\n" +
                "│       ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐       │\n" +
                "│   8   │ " + pos[0][0] + " │ " + pos[0][1] + " │ " + pos[0][2] + " │ " + pos[0][3] + " │ " + pos[0][4] + " │ " + pos[0][5] + " │ " + pos[0][6] + " │ " + pos[0][7] + " │   8   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   7   │ " + pos[1][0] + " │ " + pos[1][1] + " │ " + pos[1][2] + " │ " + pos[1][3] + " │ " + pos[1][4] + " │ " + pos[1][5] + " │ " + pos[1][6] + " │ " + pos[1][7] + " │   7   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   6   │ " + pos[2][0] + " │ " + pos[2][1] + " │ " + pos[2][2] + " │ " + pos[2][3] + " │ " + pos[2][4] + " │ " + pos[2][5] + " │ " + pos[2][6] + " │ " + pos[2][7] + " │   6   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   5   │ " + pos[3][0] + " │ " + pos[3][1] + " │ " + pos[3][2] + " │ " + pos[3][3] + " │ " + pos[3][4] + " │ " + pos[3][5] + " │ " + pos[3][6] + " │ " + pos[3][7] + " │   5   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   4   │ " + pos[4][0] + " │ " + pos[4][1] + " │ " + pos[4][2] + " │ " + pos[4][3] + " │ " + pos[4][4] + " │ " + pos[4][5] + " │ " + pos[4][6] + " │ " + pos[4][7] + " │   4   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   3   │ " + pos[5][0] + " │ " + pos[5][1] + " │ " + pos[5][2] + " │ " + pos[5][3] + " │ " + pos[5][4] + " │ " + pos[5][5] + " │ " + pos[5][6] + " │ " + pos[5][7] + " │   3   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   2   │ " + pos[6][0] + " │ " + pos[6][1] + " │ " + pos[6][2] + " │ " + pos[6][3] + " │ " + pos[6][4] + " │ " + pos[6][5] + " │ " + pos[6][6] + " │ " + pos[6][7] + " │   2   │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │\n" +
                "│   1   │ " + pos[7][0] + " │ " + pos[7][1] + " │ " + pos[7][2] + " │ " + pos[7][3] + " │ " + pos[7][4] + " │ " + pos[7][5] + " │ " + pos[7][6] + " │ " + pos[7][7] + " │   1   │\n" +
                "│       └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘       │\n" +
                "│                                                               │\n" +
                "│          A     B     C     D     E     F     G     H          │\n" +
                "└───────────────────────────────────────────────────────────────┘\n";
    }

    private IPiece getPiece(int x, int y) {
        return pos[x][y];
    }

    private void setPiece(IPiece piece, int x, int y) {
        pos[x][y] = piece;
    }

}