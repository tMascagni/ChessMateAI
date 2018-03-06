package ai.mate.chess.model;

import ai.mate.chess.model.piece.*;

public final class Board {

    private double moveCounter = 0;

    private IPiece.Color playerColor = IPiece.Color.WHITE;
    private IPiece[] whiteLossList, blackLossList;
    private IPiece[][] board;

    public Board() {
        whiteLossList = new IPiece[16];
        blackLossList = new IPiece[16];
        reset();
    }

    public boolean movePiece(BoardPosition from, BoardPosition to) {
        /* Get both pieces */
        IPiece fromPiece = getPiece(from.arrayX, from.arrayY);
        IPiece toPiece = getPiece(to.arrayX, to.arrayY);

        if (!fromPiece.getColor().equals(playerColor)) {
            System.out.println("Can't move opponents piece!");
            return false;
        }

        System.out.println("fromPiece: " + fromPiece.toString());
        System.out.println("toPiece: " + toPiece.toString());
        System.out.println("Player: " + playerColor);
        System.out.println();

        boolean isValidMove = fromPiece.isValidMove(from, to, board);

        if (!isValidMove) {
            System.out.println("Illegal move!");
            return false;
        }

        if (fromPiece instanceof Empty) {
            System.out.println("No piece at position: [" + from.file + ", " + from.rank + "]!");
            return false;
        }

        if (toPiece instanceof Empty) {
            /* Move fromPiece to to's x and y coordinates */
            setPiece(fromPiece, to.arrayX, to.arrayY);
        } else if (toPiece.getColor().equals(playerColor)) {
            /* If the player is trying to move a piece onto one of his own, it is illegal
             *  and false is returned. */
            System.out.println("Illegal move: Cannot move to own piece!");
            return false;
        } else if (!toPiece.getColor().equals(playerColor)) {
            /* kill piece :) */
            /* add the slain piece to a list / keep track of the slain piece */
            addPieceToLossList(toPiece);

            /* Increase piece's slay count */
            fromPiece.incSlayCount();

            setPiece(fromPiece, to.arrayX, to.arrayY);
        }

        /* Increase move count for this piece */
        toPiece.incMoveCount();
        moveCounter += 0.5;

        /* Put new empty piece at the previous position of from. Aka remove old from piece. */
        setPiece(new Empty(), from.arrayX, from.arrayY);

        /* Since the move have been a success, we shall switch the player. */
        switchPlayer();

        return true;
    }

    public final void reset() {
        board = new IPiece[][]{
                {new Rook(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Queen(IPiece.Color.BLACK), new King(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Rook(IPiece.Color.BLACK)},
                {new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK)},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE)},
                {new Rook(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Queen(IPiece.Color.WHITE), new King(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Rook(IPiece.Color.WHITE)}};

        moveCounter = 0;
        playerColor = IPiece.Color.WHITE;

        for (int i = 0; i < whiteLossList.length; i++) {
            whiteLossList[i] = new Empty();
            blackLossList[i] = new Empty();
        }
    }

    public final String getBoard() {
        return "\n┌───────────────────────────────────────────────────────────────┐    ┌────────────────────────────────────────────────────────────┐\n" +
                "│          A     B     C     D     E     F     G     H          │    │ Player: " + playerColor + "           White Loss:    Black Loss:         │\n" +
                "│                                                               │    │                             " + whiteLossList[0] + "            " + blackLossList[0] + "             │\n" +
                "│       ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐       │    │ Move: " + moveCounter + "                     " + whiteLossList[1] + "            " + blackLossList[1] + "           │\n" +
                "│   8   │ " + board[0][0] + " │ " + board[0][1] + " │ " + board[0][2] + " │ " + board[0][3] + " │ " + board[0][4] + " │ " + board[0][5] + " │ " + board[0][6] + " │ " + board[0][7] + " │   8   │    │                             " + whiteLossList[2] + "            " + blackLossList[2] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ White Clock:                " + whiteLossList[3] + "            " + blackLossList[3] + "             │\n" +
                "│   7   │ " + board[1][0] + " │ " + board[1][1] + " │ " + board[1][2] + " │ " + board[1][3] + " │ " + board[1][4] + " │ " + board[1][5] + " │ " + board[1][6] + " │ " + board[1][7] + " │   7   │    │                             " + whiteLossList[4] + "            " + blackLossList[4] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Black Clock:                " + whiteLossList[5] + "            " + blackLossList[5] + "             │\n" +
                "│   6   │ " + board[2][0] + " │ " + board[2][1] + " │ " + board[2][2] + " │ " + board[2][3] + " │ " + board[2][4] + " │ " + board[2][5] + " │ " + board[2][6] + " │ " + board[2][7] + " │   6   │    │                             " + whiteLossList[6] + "            " + blackLossList[6] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ White Strength:             " + whiteLossList[7] + "            " + blackLossList[7] + "             │\n" +
                "│   5   │ " + board[3][0] + " │ " + board[3][1] + " │ " + board[3][2] + " │ " + board[3][3] + " │ " + board[3][4] + " │ " + board[3][5] + " │ " + board[3][6] + " │ " + board[3][7] + " │   5   │    │                             " + whiteLossList[8] + "            " + blackLossList[8] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Black Strength:             " + whiteLossList[9] + "            " + blackLossList[9] + "             │\n" +
                "│   4   │ " + board[4][0] + " │ " + board[4][1] + " │ " + board[4][2] + " │ " + board[4][3] + " │ " + board[4][4] + " │ " + board[4][5] + " │ " + board[4][6] + " │ " + board[4][7] + " │   4   │    │                             " + whiteLossList[10] + "            " + blackLossList[10] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ White Pieces:               " + whiteLossList[11] + "            " + blackLossList[11] + "             │\n" +
                "│   3   │ " + board[5][0] + " │ " + board[5][1] + " │ " + board[5][2] + " │ " + board[5][3] + " │ " + board[5][4] + " │ " + board[5][5] + " │ " + board[5][6] + " │ " + board[5][7] + " │   3   │    │                             " + whiteLossList[12] + "            " + blackLossList[12] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Black Pieces:               " + whiteLossList[13] + "            " + blackLossList[13] + "             │\n" +
                "│   2   │ " + board[6][0] + " │ " + board[6][1] + " │ " + board[6][2] + " │ " + board[6][3] + " │ " + board[6][4] + " │ " + board[6][5] + " │ " + board[6][6] + " │ " + board[6][7] + " │   2   │    │                             " + whiteLossList[14] + "            " + blackLossList[14] + "             │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                             " + whiteLossList[15] + "            " + blackLossList[15] + "             │\n" +
                "│   1   │ " + board[7][0] + " │ " + board[7][1] + " │ " + board[7][2] + " │ " + board[7][3] + " │ " + board[7][4] + " │ " + board[7][5] + " │ " + board[7][6] + " │ " + board[7][7] + " │   1   │    │                                                            │\n" +
                "│       └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘       │    │                                                            │\n" +
                "│                                                               │    │                                                            │\n" +
                "│          A     B     C     D     E     F     G     H          │    │                                                            │\n" +
                "└───────────────────────────────────────────────────────────────┘    └────────────────────────────────────────────────────────────┘\n";
    }

    private IPiece getPiece(int x, int y) {
        return board[x][y];
    }

    private void setPiece(IPiece piece, int x, int y) {
        board[x][y] = piece;
    }

    private void switchPlayer() {
        if (playerColor.equals(IPiece.Color.WHITE))
            playerColor = IPiece.Color.BLACK;
        else
            playerColor = IPiece.Color.WHITE;
    }

    public IPiece.Color getPlayerColor() {
        return playerColor;
    }

    private void addPieceToLossList(IPiece piece) {
        if (piece.getColor().equals(IPiece.Color.WHITE)) {
            for (int i = 0; i < whiteLossList.length; i++)
                if (whiteLossList[i] instanceof Empty) {
                    whiteLossList[i] = piece;
                    return;
                }
        } else {
            for (int i = 0; i < blackLossList.length; i++)
                if (blackLossList[i] instanceof Empty) {
                    blackLossList[i] = piece;
                    return;
                }
        }
    }

}