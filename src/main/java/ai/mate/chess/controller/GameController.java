package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IGameController;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.IPiece;
import ai.mate.chess.ui.ITui;
import ai.mate.chess.ui.Tui;

public class GameController implements IGameController {

    private final ITui tui = Tui.getInstance();

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
        IPiece.Color playerColor = tui.getPlayerColorInput();

        tui.printHumanPlayer(playerColor);

        while (true) {
            tui.printBoard(board);

            BoardPosition fromPos = tui.getBoardPositionInput();
            BoardPosition toPos = tui.getBoardPositionInput();

            board.movePiece(fromPos, toPos);
        }
    }

}