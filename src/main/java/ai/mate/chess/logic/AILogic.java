package ai.mate.chess.logic;

public final class AILogic {

    private static AILogic instance;

    static {
        try {
            instance = new AILogic();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton AILogic instance!");
        }
    }

    private AILogic() {

    }

    public static synchronized AILogic getInstance() {
        return instance;
    }

}