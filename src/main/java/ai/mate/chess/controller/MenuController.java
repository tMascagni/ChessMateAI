package ai.mate.chess.controller;

import ai.mate.chess.ui.tui.Tui;

public final class MenuController {

    private final Tui tui = Tui.getInstance();
    private final GameController gameController = GameController.getInstance();

    private static MenuController instance;

    static {
        try {
            instance = new MenuController();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton MenuController instance!");
        }
    }

    private MenuController() {

    }

    public static synchronized MenuController getInstance() {
        return instance;
    }

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
                tui.printExit();
                System.exit(0);
                break;
            case '1':
                gameController.start();
                break;
            default:
                tui.printUnrecognizedCommand();
                break;
        }
    }

}