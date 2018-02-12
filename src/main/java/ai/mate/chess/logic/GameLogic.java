package ai.mate.chess.logic;

public final class GameLogic {

    private static GameLogic instance;

    static {
        try {
            instance = new GameLogic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton GameLogic instance!");
        }
    }

    private GameLogic() {

    }

    public static synchronized GameLogic getInstance() {
        return instance;
    }

}