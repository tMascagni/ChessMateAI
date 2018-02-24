package ai.mate.chess.model;

public final class BoardPosition {

    public final char file;
    public final int rank;
    public final int x;
    public final int y;

    private final int X_OFFSET = 8;

    public BoardPosition(char file, int rank) {
        this.file = Character.toLowerCase(file);
        this.rank = rank;
        this.x = calculateX(this.rank);
        this.y = calculateY(this.file);
    }

    private int calculateY(char file) {
        switch (file) {
            case 'a':
                return 0;
            case 'b':
                return 1;
            case 'c':
                return 2;
            case 'd':
                return 3;
            case 'e':
                return 4;
            case 'f':
                return 5;
            case 'g':
                return 6;
            case 'h':
                return 7;
            default:
                return -1;
        }
    }

    private int calculateX(int rank) {
        return Math.abs(rank - X_OFFSET);
    }

}