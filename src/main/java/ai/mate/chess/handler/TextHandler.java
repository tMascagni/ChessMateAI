package ai.mate.chess.handler;

public final class TextHandler {

    /* Black Pieces */
    private static final String BLACK_ID = "*";

    public static final String BLACK_ROOK = BLACK_ID + "R" + BLACK_ID;
    public static final String BLACK_KNIGHT = BLACK_ID + "N" + BLACK_ID;
    public static final String BLACK_BISHOP = BLACK_ID + "B" + BLACK_ID;
    public static final String BLACK_QUEEN = BLACK_ID + "Q" + BLACK_ID;
    public static final String BLACK_KING = BLACK_ID + "K" + BLACK_ID;
    public static final String BLACK_PAWN = BLACK_ID + "P" + BLACK_ID;

    /* White Pieces */
    private static final String WHITE_ID = " ";

    public static final String WHITE_ROOK = WHITE_ID + "R" + WHITE_ID;
    public static final String WHITE_KNIGHT = WHITE_ID + "N" + WHITE_ID;
    public static final String WHITE_BISHOP = WHITE_ID + "B" + WHITE_ID;
    public static final String WHITE_QUEEN = WHITE_ID + "Q" + WHITE_ID;
    public static final String WHITE_KING = WHITE_ID + "K" + WHITE_ID;
    public static final String WHITE_PAWN = WHITE_ID + "P" + WHITE_ID;

    /* Empty */
    public static final String EMPTY = "   ";

    public static final String LATEST_MOVE_INITIAL = "None                  ";

    private static TextHandler instance;

    static {
        try {
            instance = new TextHandler();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate Singleton TextHandler instance!");
        }
    }

    private TextHandler() {

    }

    public static synchronized TextHandler getInstance() {
        return instance;
    }

}