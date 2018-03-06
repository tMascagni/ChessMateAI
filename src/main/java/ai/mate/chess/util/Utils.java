package ai.mate.chess.util;

public final class Utils {

    public static final String ARROW = "»";

    public static final int BISHOP_SCORE = 3;
    public static final int ROOK_SCORE = 5;
    public static final int QUEEN_SCORE = 9;
    public static final int PAWN_SCORE = 1;
    public static final int KNIGHT_SCORE = 3;
    public static final int KING_SCORE = 100;
    public static final int EMPTY_SCORE = Integer.MAX_VALUE;

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