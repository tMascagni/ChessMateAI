package ai.mate.chess.controller;

import ai.mate.chess.controller.interfaces.IGameController;

public class GameController implements IGameController {

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

}