package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IGameController;
import ai.mate.chess.model.Board;
import ai.mate.chess.model.BoardPosition;
import ai.mate.chess.model.piece.IPiece;
import ai.mate.chess.ui.ITui;
import ai.mate.chess.ui.Tui;
import ai.mate.chess.util.ChessTimer;

import java.awt.event.ActionEvent;

public class GameController implements IGameController, ChessTimer.EndOfTurnListener {

    private final ITui tui = Tui.getInstance();

    private Board board;
    private ChessTimer timer;

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
        timer = new ChessTimer(15000, this);
        //timer.start();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("15 seconds has passed!");
    }

}