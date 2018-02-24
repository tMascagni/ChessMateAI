package ai.mate.chess.util;

public final class Utils {

    public static final String ARROW = "»";

    private static Utils instance;

    static {
        try {
            instance = new Utils();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton Utils instance!");
        }
    }

    private Utils() {

    }

    public static synchronized Utils getInstance() {
        return instance;
    }

}