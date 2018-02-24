package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IMenuController;
import ai.mate.chess.tui.Tui;

public final class MenuController implements IMenuController {

    private static IMenuController instance;

    private final Tui tui = Tui.getInstance();

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
        while (true) {
            System.out.println("INFINITE LOOP LAL");
        }
    }

}