package ai.mate.chess.util;

import ai.mate.chess.model.piece.IPiece;

public final class Utils {

    public static final String ARROW = "»";

    public static final int BISHOP_SCORE = 3;
    public static final int ROOK_SCORE = 5;
    public static final int QUEEN_SCORE = 9;
    public static final int PAWN_SCORE = 1;
    public static final int KNIGHT_SCORE = 3;
    public static final int KING_SCORE = 100;
    public static final int EMPTY_SCORE = 0;

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

    public static IPiece.Color getOpponentColor(IPiece.Color color) {
        if (color.equals(IPiece.Color.EMPTY))
            return IPiece.Color.EMPTY;

        if (color.equals(IPiece.Color.WHITE))
            return IPiece.Color.BLACK;

        return IPiece.Color.WHITE;
    }

}