package ai.mate.chess.controller;

import ai.mate.chess.model.board.BoardOld;
import ai.mate.chess.model.board.BoardPosition;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.ui.Tui;

public class GameController {

    private final Tui tui = Tui.getInstance();

    private BoardOld boardOld;

    private static GameController instance;

    static {
        try {
            instance = new GameController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton GameController instance!");
        }
    }

    private GameController() {
        boardOld = new BoardOld();
    }

    public static synchronized GameController getInstance() {
        return instance;
    }

    public void start() {
        boardOld.reset();

        tui.printChoosePlayer();
        Piece.Color playerColor = tui.getPlayerColorInput();

        tui.printHumanPlayer(playerColor);
        tui.printAIPlayer(Piece.getOpponentColor(playerColor));

        while (true) {
            tui.printBoard(boardOld);

            BoardPosition fromPos;

            /*
             * Check whether the fromPosition is valid.
             */
            while (true) {
                fromPos = tui.getBoardPositionInput("From");

                if (boardOld.isValidFromPos(fromPos))
                    break;

                tui.printIllegalAction("Try again!");
                System.out.println();
            }

            BoardPosition toPos = tui.getBoardPositionInput("To");
            boardOld.movePiece(fromPos, toPos);
        }
    }

}