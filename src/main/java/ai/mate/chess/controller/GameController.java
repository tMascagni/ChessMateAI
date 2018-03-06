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

    }

    public static synchronized IGameController getInstance() {
        return instance;
    }

    @Override
    public void start() {
        reset();

        IPiece.Color playerColor = IPiece.Color.WHITE;

        while (true) {
            tui.printBoard(board);
            System.out.println("Player: " + playerColor);

            BoardPosition fromPos = tui.getBoardPositionInput();
            BoardPosition toPos = tui.getBoardPositionInput();

            board.movePiece(playerColor, fromPos, toPos);

            playerColor = switchPlayer(playerColor);
        }
    }

    @Override
    public void reset() {
        board = new Board();
    }

    private IPiece.Color switchPlayer(IPiece.Color playerColor) {
        if (playerColor.equals(IPiece.Color.WHITE))
            return IPiece.Color.BLACK;
        return IPiece.Color.WHITE;
    }

}