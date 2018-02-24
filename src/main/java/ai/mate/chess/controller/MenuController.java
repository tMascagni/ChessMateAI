package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IGameController;
import ai.mate.chess.controller.interfaces.IMenuController;
import ai.mate.chess.ui.ITui;
import ai.mate.chess.ui.Tui;

public final class MenuController implements IMenuController {

    private final ITui tui = Tui.getInstance();
    private final IGameController gameController = GameController.getInstance();

    private static IMenuController instance;

    static {
        try {
            instance = new MenuController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton MenuController instance!");
        }
    }

    private MenuController() {

    }

    public static synchronized IMenuController getInstance() {
        return instance;
    }

    @Override
    public final void start() {
        tui.printStartScreen();
        tui.printPressEnter();

        while (true) {
            tui.printMenu();
            char command = tui.getUserInput();
            executeCommand(command);
        }

    }

    private void executeCommand(char command) {
        switch (command) {
            case '0':
                System.exit(0);
                break;
            case '1':
                gameController.start();
                break;
            case '2':
                System.out.println("TWO!");
                break;
            default:
                tui.printUnrecognizedCommand();
                break;
        }
    }

}