package ai.mate.chess.model;

import ai.mate.chess.model.piece.*;
import ai.mate.chess.ui.ITui;
import ai.mate.chess.ui.Tui;

public final class Board {

    private final static int PLAYER_PIECE_COUNT = 16;

    private double globalMoveCount = 0;
    private int whitePieceCount = PLAYER_PIECE_COUNT;
    private int blackPieceCount = PLAYER_PIECE_COUNT;

    private IPiece.Color playerColor = IPiece.Color.WHITE;
    private IPiece[] whiteLossList, blackLossList;
    private IPiece[][] board;

    private final ITui tui = Tui.getInstance();

    private String latestMove = "                      ";

    /* Normal constructor */
    public Board() {
        whiteLossList = new IPiece[PLAYER_PIECE_COUNT];
        blackLossList = new IPiece[PLAYER_PIECE_COUNT];
        reset();
    }

    /* Deep copy constructor */
    public Board(Board board) {
        this.globalMoveCount = board.globalMoveCount;
        this.whitePieceCount = board.whitePieceCount;
        this.blackPieceCount = board.blackPieceCount;
        this.playerColor = board.playerColor;
        this.whiteLossList = board.whiteLossList;
        this.blackLossList = board.blackLossList;
        this.board = board.board;
    }

    public boolean movePiece(BoardPosition from, BoardPosition to) {
        /* Get both pieces 'to' and 'from' pieces. */
        IPiece fromPiece = getPiece(from.arrayX, from.arrayY);
        IPiece toPiece = getPiece(to.arrayX, to.arrayY);

        /*
         * If the 'from' piece is instance of a 'Empty' piece,
         * then return false, since the player is not allowed to
         * move an 'Empty' piece.
         */
        if (fromPiece instanceof Empty) {
            tui.printIllegalAction("No piece at position: [" + from.file + ", " + from.rank + "]!");
            return false;
        }

        /*
         * If the WHITE player tries to move a BLACK piece,
         * then return false, and vice versa.
         */
        if (!fromPiece.getColor().equals(playerColor)) {
            tui.printIllegalAction("Can't move opponents piece!");
            return false;
        }

        /*
         * You are not allowed to move onto yourself.
         */
        if (to.rank == from.rank && to.file == from.file) {
            tui.printIllegalAction("Can't move onto the same piece!");
            return false;
        }

        /*
         * Populate moves to pieces every single move, so that they
         * are always up to date with the their surroundings.
         */
        for (IPiece[] boardRow : board)
            for (IPiece piece : boardRow)
                piece.populateMoves(this);

        /* Check to see whether this is a valid move for the fromPiece. */
        boolean isValidMove = fromPiece.isValidMove(from, to, this);

        // Debugging purposes
        /*
        System.out.println();
        System.out.println("fromPiece: " + fromPiece.toString());
        System.out.println("toPiece: " + toPiece.toString());
        System.out.println("Player: " + playerColor);
        System.out.println("isValidMove: " + isValidMove);
        System.out.println();
        */

        /* If the move is not valid, let the player know and then return false. */
        if (!isValidMove) {
            tui.printIllegalAction("Illegal move!");
            return false;
        }

        if (toPiece instanceof Empty) {
            /*
             * If the 'to' piece is instance of a 'Empty' piece,
             * we then know that the toPiece is free to move there.
             * (Since we are at this point in the code, we already know
             * that it must be a valid move.)
             *
             * Move fromPiece to "to's" x and y coordinates
             */
            setPiece(fromPiece, to.arrayX, to.arrayY);
        } else if (toPiece.getColor().equals(playerColor)) {
            /*
             * If the player is trying to move a piece onto one of its own,
             * it is illegal and false is returned.
             */
            tui.printIllegalAction("Can't move onto own piece!");
            return false;
        } else if (!toPiece.getColor().equals(playerColor)) {
            /*
             * Since we know that the 'to' piece is different from the player's own colour,
             * and that its not instance of a 'Empty' piece, we know that a opponent piece must be there.
             * Therefore we need to slay that piece, of which we do by adding it to a loss list.
             * The piece will then we overwritten by the setPiece method, so all is good.
             */

            /* Add the slain piece to the loss list */
            addLossList(toPiece);

            /* Increase 'from' piece's slay count */
            fromPiece.incSlayCount();

            setPiece(fromPiece, to.arrayX, to.arrayY);
        }

        /* Increase move count for this 'from' piece */
        fromPiece.incMoveCount();

        /* Increase global move count */
        incGlobalMoveCount();

        /*
         * Put a new 'Empty' piece at the previous position
         * of the 'from' piece.
         */
        setPiece(new Empty(), from.arrayX, from.arrayY);

        /* Set latest move String */
        latestMove = playerColor.name() + ": [" + from.file + ", " + from.rank + "] → [" + to.file + ", " + to.rank + "]";

        /* Since the move have been a success, we shall switch the player. */
        switchPlayer();

        /* Success move, yay! */
        return true;
    }

    public final void reset() {
        /*
        board = new IPiece[][]{
                {new Bishop(IPiece.Color.BLACK), new Bishop(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Rook(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Queen(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Rook(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()}};
*/
        board = new IPiece[][]{
                {new Rook(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Queen(IPiece.Color.BLACK), new King(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Rook(IPiece.Color.BLACK)},
                {new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK)},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE)},
                {new Rook(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Queen(IPiece.Color.WHITE), new King(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Rook(IPiece.Color.WHITE)}};

        globalMoveCount = 0;
        playerColor = IPiece.Color.WHITE;

        for (int i = 0; i < whiteLossList.length; i++) {
            whiteLossList[i] = new Empty();
            blackLossList[i] = new Empty();
        }

        whitePieceCount = PLAYER_PIECE_COUNT;
        blackPieceCount = PLAYER_PIECE_COUNT;
    }

    public final String getBoard() {
        return "\n┌───────────────────────────────────────────────────────────────┐    ┌────────────────────────────────────────────────────────────┐\n" +
                "│          A     B     C     D     E     F     G     H          │    │ Player: " + playerColor + "           White Loss:    Black Loss:         │\n" +
                "│                                                               │    │                            " + getLossPieceText(IPiece.Color.WHITE, 0) + "         " + getLossPieceText(IPiece.Color.BLACK, 0) + "           │\n" +
                "│       ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐       │    │ Move: " + globalMoveCount + "                  " + getLossPieceText(IPiece.Color.WHITE, 1) + "         " + getLossPieceText(IPiece.Color.BLACK, 1) + "           │\n" +
                "│   8   │ " + board[0][0] + " │ " + board[0][1] + " │ " + board[0][2] + " │ " + board[0][3] + " │ " + board[0][4] + " │ " + board[0][5] + " │ " + board[0][6] + " │ " + board[0][7] + " │   8   │    │                            " + getLossPieceText(IPiece.Color.WHITE, 2) + "         " + getLossPieceText(IPiece.Color.BLACK, 2) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Latest Move:               " + getLossPieceText(IPiece.Color.WHITE, 3) + "         " + getLossPieceText(IPiece.Color.BLACK, 3) + "           │\n" +
                "│   7   │ " + board[1][0] + " │ " + board[1][1] + " │ " + board[1][2] + " │ " + board[1][3] + " │ " + board[1][4] + " │ " + board[1][5] + " │ " + board[1][6] + " │ " + board[1][7] + " │   7   │    │ " + latestMove + "     " + getLossPieceText(IPiece.Color.WHITE, 4) + "         " + getLossPieceText(IPiece.Color.BLACK, 4) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                            " + getLossPieceText(IPiece.Color.WHITE, 5) + "         " + getLossPieceText(IPiece.Color.BLACK, 5) + "           │\n" +
                "│   6   │ " + board[2][0] + " │ " + board[2][1] + " │ " + board[2][2] + " │ " + board[2][3] + " │ " + board[2][4] + " │ " + board[2][5] + " │ " + board[2][6] + " │ " + board[2][7] + " │   6   │    │                            " + getLossPieceText(IPiece.Color.WHITE, 6) + "         " + getLossPieceText(IPiece.Color.BLACK, 6) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                            " + getLossPieceText(IPiece.Color.WHITE, 7) + "         " + getLossPieceText(IPiece.Color.BLACK, 7) + "           │\n" +
                "│   5   │ " + board[3][0] + " │ " + board[3][1] + " │ " + board[3][2] + " │ " + board[3][3] + " │ " + board[3][4] + " │ " + board[3][5] + " │ " + board[3][6] + " │ " + board[3][7] + " │   5   │    │                            " + getLossPieceText(IPiece.Color.WHITE, 8) + "         " + getLossPieceText(IPiece.Color.BLACK, 8) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                           " + getLossPieceText(IPiece.Color.WHITE, 9) + "        " + getLossPieceText(IPiece.Color.BLACK, 9) + "           │\n" +
                "│   4   │ " + board[4][0] + " │ " + board[4][1] + " │ " + board[4][2] + " │ " + board[4][3] + " │ " + board[4][4] + " │ " + board[4][5] + " │ " + board[4][6] + " │ " + board[4][7] + " │   4   │    │                           " + getLossPieceText(IPiece.Color.WHITE, 10) + "        " + getLossPieceText(IPiece.Color.BLACK, 10) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                           " + getLossPieceText(IPiece.Color.WHITE, 11) + "        " + getLossPieceText(IPiece.Color.BLACK, 11) + "           │\n" +
                "│   3   │ " + board[5][0] + " │ " + board[5][1] + " │ " + board[5][2] + " │ " + board[5][3] + " │ " + board[5][4] + " │ " + board[5][5] + " │ " + board[5][6] + " │ " + board[5][7] + " │   3   │    │                           " + getLossPieceText(IPiece.Color.WHITE, 12) + "        " + getLossPieceText(IPiece.Color.BLACK, 12) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ White Clock:              " + getLossPieceText(IPiece.Color.WHITE, 13) + "        " + getLossPieceText(IPiece.Color.BLACK, 13) + "           │\n" +
                "│   2   │ " + board[6][0] + " │ " + board[6][1] + " │ " + board[6][2] + " │ " + board[6][3] + " │ " + board[6][4] + " │ " + board[6][5] + " │ " + board[6][6] + " │ " + board[6][7] + " │   2   │    │                           " + getLossPieceText(IPiece.Color.WHITE, 14) + "        " + getLossPieceText(IPiece.Color.BLACK, 14) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Black Clock:              " + getLossPieceText(IPiece.Color.WHITE, 15) + "        " + getLossPieceText(IPiece.Color.BLACK, 15) + "           │\n" +
                "│   1   │ " + board[7][0] + " │ " + board[7][1] + " │ " + board[7][2] + " │ " + board[7][3] + " │ " + board[7][4] + " │ " + board[7][5] + " │ " + board[7][6] + " │ " + board[7][7] + " │   1   │    │                                                            │\n" +
                "│       └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘       │    │ White Pieces: " + getPieceCount(IPiece.Color.WHITE) + "                                           │\n" +
                "│                                                               │    │                                                            │\n" +
                "│          A     B     C     D     E     F     G     H          │    │ Black Pieces: " + getPieceCount(IPiece.Color.BLACK) + "                                           │\n" +
                "└───────────────────────────────────────────────────────────────┘    └────────────────────────────────────────────────────────────┘\n";
    }

    public IPiece getPiece(int x, int y) {
        return board[x][y];
    }

    public IPiece getPiece(BoardPosition boardPos) {
        return board[boardPos.arrayX][boardPos.arrayY];
    }

    public BoardPosition getPieceBoardPos(int id) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j].getId() == id)
                    return new BoardPosition(i, j);
        return new BoardPosition(-1, -1);
    }

    public boolean isValidFromPos(BoardPosition boardPosition) {
        IPiece piece = getPiece(boardPosition);
        return piece.getColor().equals(playerColor);
    }

    private void setPiece(IPiece piece, int x, int y) {
        board[x][y] = piece;
    }

    private void incGlobalMoveCount() {
        globalMoveCount += 0.5;
    }

    private void switchPlayer() {
        if (playerColor.equals(IPiece.Color.WHITE))
            playerColor = IPiece.Color.BLACK;
        else
            playerColor = IPiece.Color.WHITE;
    }

    private void addLossList(IPiece piece) {
        if (piece.getColor().equals(IPiece.Color.WHITE)) {
            whitePieceCount--;
            for (int i = 0; i < whiteLossList.length; i++)
                if (whiteLossList[i] instanceof Empty) {
                    whiteLossList[i] = piece;
                    return;
                }
        } else {
            for (int i = 0; i < blackLossList.length; i++) {
                blackPieceCount--;
                if (blackLossList[i] instanceof Empty) {
                    blackLossList[i] = piece;
                    return;
                }
            }
        }
    }

    private String getPieceCount(IPiece.Color color) {
        if (color.equals(IPiece.Color.WHITE)) {
            if (whitePieceCount < 10)
                return " " + whitePieceCount;
            return String.valueOf(whitePieceCount);
        } else {
            if (blackPieceCount < 10)
                return " " + blackPieceCount;
            return String.valueOf(blackPieceCount);
        }
    }

    private String getLossPieceText(IPiece.Color color, int index) {
        if (color.equals(IPiece.Color.WHITE)) {
            if (!(whiteLossList[index] instanceof Empty))
                return (index + 1) + ". " + whiteLossList[index].toString();
        } else if (color.equals(IPiece.Color.BLACK)) {
            if (!(blackLossList[index] instanceof Empty))
                return (index + 1) + ". " + blackLossList[index].toString();
        }
        if (index + 1 < 10)
            return "      ";
        else
            return "       ";
    }

}