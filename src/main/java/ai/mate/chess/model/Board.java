package ai.mate.chess.model;

import ai.mate.chess.handler.TextHandler;
import ai.mate.chess.model.piece.Empty;
import ai.mate.chess.model.piece.IPiece;
import ai.mate.chess.model.piece.King;
import ai.mate.chess.model.piece.Queen;
import ai.mate.chess.ui.ITui;
import ai.mate.chess.ui.Tui;
import ai.mate.chess.util.Utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public final class Board {

    private final static int PLAYER_PIECE_COUNT = 16;

    private double globalMoveCount = 0;
    private int whitePieceCount = PLAYER_PIECE_COUNT;
    private int blackPieceCount = PLAYER_PIECE_COUNT;

    private IPiece.Color playerColor = IPiece.Color.WHITE;
    private IPiece[] whiteLossList, blackLossList;
    private IPiece[][] board;
    private boolean[][] opponentMoveBoard;

    private final ITui tui = Tui.getInstance();

    private String latestMove = TextHandler.LATEST_MOVE_INITIAL;

    public Board() {
        whiteLossList = new IPiece[PLAYER_PIECE_COUNT];
        blackLossList = new IPiece[PLAYER_PIECE_COUNT];
        reset();
    }

    public boolean movePiece(BoardPosition from, BoardPosition to) {
        /* Get both positions 'to' and 'from' pieces. */
        IPiece fromPiece = getPiece(from.rowX, from.colY);
        IPiece toPiece = getPiece(to.rowX, to.colY);

        /* Check if the move is valid */
        if (!isValidMove(fromPiece, toPiece, to, from))
            return false;

        if (toPiece instanceof Empty) {
            /*
             * If the 'to' piece is instance of a 'Empty' piece,
             * we then know that the toPiece is free to move there.
             * (Since we are at this point in the code, we already know
             * that it must be a valid move.)
             *
             * Move fromPiece to "to's" x and y coordinates
             */
            setPiece(fromPiece, to.rowX, to.colY);
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
            addToLossList(toPiece);

            /* Increase 'from' piece's slay count */
            fromPiece.incSlayCount();

            /* Set the 'from' piece to 'to's coordinates */
            setPiece(fromPiece, to.rowX, to.colY);
        }

        /* Increase move count for this 'from' piece */
        fromPiece.incMoveCount();

        /* Increase global move count */
        incGlobalMoveCount();

        /*
         * Put a new 'Empty' piece at the previous position
         * of the 'from' piece.
         */
        setPiece(new Empty(), from.rowX, from.colY);

        /* Set latest move String */
        latestMove = tui.getLatestMoveText(playerColor, from, to);

        /* Check if promotion is available */
        //doPromotion(fromPiece, to);

        /* Populate moves for this player */
        populateMoves(playerColor);

        /* After this point, we can see 1 possible move in the future for playerColor. */




        /* Since the move have been a success, we shall switch the player. */
        switchPlayer();

        /* Success move, yay! */
        return true;
    }

    public final void reset() {

        /* Initialize opponentMoveBoard */
        opponentMoveBoard = new boolean[][]{
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false},
        };

        /* Initialize board */

        /*
        board = new IPiece[][]{
                {new Bishop(IPiece.Color.BLACK), new Bishop(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Pawn(IPiece.Color.WHITE), new Empty(), new Empty()},
                {new Empty(), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Rook(IPiece.Color.WHITE), new Empty(), new Knight(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new King(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Queen(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Pawn(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()}};
*/

        board = new IPiece[][]{
                {new Empty(), new Empty(), new Empty(), new King(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Queen(IPiece.Color.WHITE)},
                {new Queen(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new King(IPiece.Color.WHITE), new Empty(), new Empty(), new Empty()},
                {new Queen(IPiece.Color.BLACK), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()}};

       /*
        board = new IPiece[][]{
                {new Rook(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Queen(IPiece.Color.BLACK), new King(IPiece.Color.BLACK), new Bishop(IPiece.Color.BLACK), new Knight(IPiece.Color.BLACK), new Rook(IPiece.Color.BLACK)},
                {new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK), new Pawn(IPiece.Color.BLACK)},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty(), new Empty()},
                {new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE), new Pawn(IPiece.Color.WHITE)},
                {new Rook(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Queen(IPiece.Color.WHITE), new King(IPiece.Color.WHITE), new Bishop(IPiece.Color.WHITE), new Knight(IPiece.Color.WHITE), new Rook(IPiece.Color.WHITE)}};
*/
        globalMoveCount = 0;
        whitePieceCount = PLAYER_PIECE_COUNT;
        blackPieceCount = PLAYER_PIECE_COUNT;

        playerColor = IPiece.Color.WHITE;

        for (int i = 0; i < whiteLossList.length; i++) {
            whiteLossList[i] = new Empty();
            blackLossList[i] = new Empty();
        }

        latestMove = TextHandler.LATEST_MOVE_INITIAL;

        populateMoves();
    }

    public final String getBoardText() {
        return "\n┌───────────────────────────────────────────────────────────────┐    ┌────────────────────────────────────────────────────────────┐\n" +
                "│          A     B     C     D     E     F     G     H          │    │ Player: " + playerColor + "           White Loss:    Black Loss:         │\n" +
                "│                                                               │    │                            " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 0) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 0) + "           │\n" +
                "│       ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐       │    │ Move: " + globalMoveCount + "                  " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 1) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 1) + "           │\n" +
                "│   8   │ " + board[0][0] + " │ " + board[0][1] + " │ " + board[0][2] + " │ " + board[0][3] + " │ " + board[0][4] + " │ " + board[0][5] + " │ " + board[0][6] + " │ " + board[0][7] + " │   8   │    │                            " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 2) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 2) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Latest Move:               " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 3) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 3) + "           │\n" +
                "│   7   │ " + board[1][0] + " │ " + board[1][1] + " │ " + board[1][2] + " │ " + board[1][3] + " │ " + board[1][4] + " │ " + board[1][5] + " │ " + board[1][6] + " │ " + board[1][7] + " │   7   │    │ " + latestMove + "     " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 4) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 4) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                            " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 5) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 5) + "           │\n" +
                "│   6   │ " + board[2][0] + " │ " + board[2][1] + " │ " + board[2][2] + " │ " + board[2][3] + " │ " + board[2][4] + " │ " + board[2][5] + " │ " + board[2][6] + " │ " + board[2][7] + " │   6   │    │ Possible Moves: " + tui.getPossibleMoveCountText(board) + " " +
                "       " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 6) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 6) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                            " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 7) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 7) + "           │\n" +
                "│   5   │ " + board[3][0] + " │ " + board[3][1] + " │ " + board[3][2] + " │ " + board[3][3] + " │ " + board[3][4] + " │ " + board[3][5] + " │ " + board[3][6] + " │ " + board[3][7] + " │   5   │    │                            " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 8) + "         " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 8) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                           " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 9) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 9) + "           │\n" +
                "│   4   │ " + board[4][0] + " │ " + board[4][1] + " │ " + board[4][2] + " │ " + board[4][3] + " │ " + board[4][4] + " │ " + board[4][5] + " │ " + board[4][6] + " │ " + board[4][7] + " │   4   │    │                           " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 10) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 10) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │                           " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 11) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 11) + "           │\n" +
                "│   3   │ " + board[5][0] + " │ " + board[5][1] + " │ " + board[5][2] + " │ " + board[5][3] + " │ " + board[5][4] + " │ " + board[5][5] + " │ " + board[5][6] + " │ " + board[5][7] + " │   3   │    │                           " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 12) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 12) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ White Clock:              " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 13) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 13) + "           │\n" +
                "│   2   │ " + board[6][0] + " │ " + board[6][1] + " │ " + board[6][2] + " │ " + board[6][3] + " │ " + board[6][4] + " │ " + board[6][5] + " │ " + board[6][6] + " │ " + board[6][7] + " │   2   │    │                           " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 14) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 14) + "           │\n" +
                "│       ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤       │    │ Black Clock:              " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.WHITE, 15) + "        " + tui.getLossPieceText(whiteLossList, blackLossList, IPiece.Color.BLACK, 15) + "           │\n" +
                "│   1   │ " + board[7][0] + " │ " + board[7][1] + " │ " + board[7][2] + " │ " + board[7][3] + " │ " + board[7][4] + " │ " + board[7][5] + " │ " + board[7][6] + " │ " + board[7][7] + " │   1   │    │                                                            │\n" +
                "│       └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘       │    │ White Pieces: " + tui.getPieceCountText(whitePieceCount, blackPieceCount, IPiece.Color.WHITE) + "                                           │\n" +
                "│                                                               │    │                                                            │\n" +
                "│          A     B     C     D     E     F     G     H          │    │ Black Pieces: " + tui.getPieceCountText(whitePieceCount, blackPieceCount, IPiece.Color.BLACK) + "                                           │\n" +
                "└───────────────────────────────────────────────────────────────┘    └────────────────────────────────────────────────────────────┘\n";
    }

    /*********************
     * Getters
     *********************/
    public IPiece getPiece(int rowX, int colY) {
        return board[rowX][colY];
    }

    public IPiece getPiece(BoardPosition boardPos) {
        return board[boardPos.rowX][boardPos.colY];
    }

    public IPiece getPiece(int id) {
        for (IPiece[] boardRow : board)
            for (IPiece piece : boardRow)
                if (piece.getId() == id)
                    return piece;
        return new Empty();
    }

    public BoardPosition getPieceBoardPos(int id) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[0].length; j++)
                if (board[i][j].getId() == id)
                    return new BoardPosition(i, j);
        return new BoardPosition(-1, -1);
    }

    /*********************
     * Setters
     *********************/
    private void setPiece(IPiece piece, int rowX, int colY) {
        board[rowX][colY] = piece;
    }

    private void setPiece(IPiece piece, BoardPosition pos) {
        board[pos.rowX][pos.colY] = piece;
    }

    public boolean isValidFromPos(BoardPosition boardPosition) {
        IPiece piece = getPiece(boardPosition);
        return piece.getColor().equals(playerColor);
    }

    private void incGlobalMoveCount() {
        globalMoveCount += 0.5;
    }

    private void decGlobalMoveCount() {
        globalMoveCount -= 0.5;
    }

    private void switchPlayer() {
        if (playerColor.equals(IPiece.Color.WHITE))
            playerColor = IPiece.Color.BLACK;
        else
            playerColor = IPiece.Color.WHITE;
    }

    private void addToLossList(IPiece piece) {
        if (piece.getColor().equals(IPiece.Color.WHITE)) {
            for (int i = 0; i < whiteLossList.length; i++)
                if (whiteLossList[i] instanceof Empty) {
                    whiteLossList[i] = piece;
                    whitePieceCount--;
                    return;
                }
        } else {
            for (int i = 0; i < blackLossList.length; i++) {
                if (blackLossList[i] instanceof Empty) {
                    blackLossList[i] = piece;
                    blackPieceCount--;
                    return;
                }
            }
        }
    }

    private boolean isValidMove(IPiece fromPiece, IPiece toPiece, BoardPosition to, BoardPosition from) {
        /*
         * If the 'from' piece is instance of a 'Empty' piece,
         * then return false, since the player is not allowed to
         * move an 'Empty' piece.
         */
        if (fromPiece instanceof Empty) {
            tui.printIllegalAction("No piece resides at the given position!");
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

        if (isKingInCheck(playerColor)) {
            tui.printIllegalAction(playerColor + " KING IN CHECK!");
        }

        /* Populate moves for this player */
        boolean isKingInCheck = isKingInCheck(playerColor);
        populateMoves(playerColor);

        /* Check to see whether this is a valid move for the fromPiece. */
        boolean isValidMove = fromPiece.isValidMove(from, to);

        /* If the move is not valid, let the player know and then return false. */
        if (!isValidMove) {
            tui.printIllegalAction("Illegal move!");
            return false;
        }

        /*
         * If the move is valid, and current player's king is in check
         * create boolean map of opponents moves.
         */
        if (isKingInCheck)
            updateOpponentMoveBoard(playerColor);

        /* Move is legal, yay! */
        return true;
    }

    private boolean isKingInCheck(IPiece.Color playerColor) {
        /* Get all possible move coordinates of the opponent color */
        List<Point> possibleMoveCoordinates = getAllPossibleMoveCoordinates(Utils.getOpponentColor(playerColor));

        /* Now we have to check whether playerColor's King is in the move coordinates */

        /* White King ID: 156 */
        /* Black King ID: 100 */

        BoardPosition kingPos = null;

        if (playerColor.equals(IPiece.Color.WHITE)) {
            kingPos = getPieceBoardPos(156);
        } else if (playerColor.equals(IPiece.Color.BLACK)) {
            kingPos = getPieceBoardPos(100);
        }

        for (Point coord : possibleMoveCoordinates)
            if (coord.x == kingPos.rowX && coord.y == kingPos.colY)
                return true;

        return false;
    }

    private void populateMoves() {
        for (IPiece[] boardRow : board)
            for (IPiece piece : boardRow)
                piece.populateMoves(this);
    }

    private List<Point> getAllPossibleMoveCoordinates(IPiece.Color playerColor) {
        List<Point> possibleMoveCoordinates = new ArrayList<>();
        for (IPiece[] boardRow : board)
            for (IPiece piece : boardRow)
                if (piece.getColor().equals(playerColor))
                    possibleMoveCoordinates.addAll(piece.getPossibleMovesCoordinates(this));
        return possibleMoveCoordinates;
    }

    private void populateMoves(IPiece.Color playerColor) {
        if (playerColor.equals(IPiece.Color.WHITE)) {
            for (IPiece[] boardRow : board)
                for (IPiece piece : boardRow)
                    if (piece.getColor().equals(IPiece.Color.WHITE))
                        piece.populateMoves(this);
        } else if (playerColor.equals(IPiece.Color.BLACK)) {
            for (IPiece[] boardRow : board)
                for (IPiece piece : boardRow)
                    if (piece.getColor().equals(IPiece.Color.BLACK))
                        piece.populateMoves(this);
        }
    }

    private List<Point> getPossibleMoves(IPiece.Color playerColor) {
        List<Point> specificPossibleMoves = new ArrayList<>();
        for (IPiece[] boardRow : board)
            for (IPiece piece : boardRow)
                if (piece.getColor().equals(playerColor))
                    specificPossibleMoves.addAll(piece.getPossibleMoves());
        return specificPossibleMoves;
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

    private void updateOpponentMoveBoard(IPiece.Color playerColor) {
        /* Get all possible move coordinates from opponent */
        List<Point> opponentMoveCoordinates = getAllPossibleMoveCoordinates(Utils.getOpponentColor(playerColor));

        for (Point coord : opponentMoveCoordinates)
            opponentMoveBoard[coord.x][coord.y] = true;

        for (int row = 0; row < opponentMoveBoard.length; row++) {
            for (int col = 0; col < opponentMoveBoard[row].length; col++) {
                System.out.print(opponentMoveBoard[row][col] + " ");
            }
            System.out.println();
        }
    }

}