package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IGameController;
import ai.mate.chess.model.board.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.Piece;
import ai.mate.chess.ui.Tui;

public class GameController implements IGameController {

    private final Tui tui = Tui.getInstance();

    private Board board;

    private static IGameController instance;

    static {
        try {
            instance = new GameController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton GameController instance!");
        }
    }

    private GameController() {
        board = new Board();
    }

    public static synchronized IGameController getInstance() {
        return instance;
    }

    @Override
    public void start() {
        board.reset();

        tui.printChoosePlayer();
        Piece.Color playerColor = tui.getPlayerColorInput();

        tui.printHumanPlayer(playerColor);
        tui.printAIPlayer(Piece.getOpponentColor(playerColor));

        while (true) {
            tui.printBoard(board);

            BoardPosition fromPos;

            /*
             * Check whether the fromPosition is valid.
             */
            while (true) {
                fromPos = tui.getBoardPositionInput("From");

                if (board.isValidFromPos(fromPos))
                    break;

                tui.printIllegalAction("Try again!");
                System.out.println();
            }

            BoardPosition toPos = tui.getBoardPositionInput("To");
            board.movePiece(fromPos, toPos);
        }
    }

}