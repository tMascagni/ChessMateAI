package ai.mate.chess.model;

public final class BoardPosition {

    public final char file;
    public final char rank;
    public final int rowX;
    public final int colY;

    private final int ROW_X_OFFSET = 8;

    public BoardPosition(char file, char rank) {
        this.file = Character.toLowerCase(file);
        this.rank = rank;
        this.rowX = calculateRowX(this.rank);
        this.colY = calculateColY(this.file);
    }

    public BoardPosition(int rowX, int colY) {
        this.rowX = rowX;
        this.colY = colY;
        this.file = calculateFile(colY);
        this.rank = calculateRank(rowX);
    }

    private char calculateFile(int colY) {
        switch (colY) {
            case 0:
                return 'a';
            case 1:
                return 'b';
            case 2:
                return 'c';
            case 3:
                return 'd';
            case 4:
                return 'e';
            case 5:
                return 'f';
            case 6:
                return 'g';
            case 7:
                return 'h';
            default:
                return 'X';
        }
    }

    private char calculateRank(int rowX) {
        int decRank = (ROW_X_OFFSET - rowX);
        return (char) (decRank + 48);
    }

    private int calculateRowX(char rank) {
        return Math.abs(Character.getNumericValue(rank) - ROW_X_OFFSET);
    }

    private int calculateColY(char file) {
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

    @Override
    public String toString() {
        return "BoardPosition [" +
                "file=" + file +
                ", rank=" + rank +
                ", rowX=" + rowX +
                ", colY=" + colY +
                ']';
    }

}