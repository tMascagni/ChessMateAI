package ai.mate.chess.model;

public final class BoardPosition {

    public final char file;
    public final char rank;
    public final int arrayX;
    public final int arrayY;

    private final int X_OFFSET = 8;

    public BoardPosition(char file, char rank) {
        this.file = Character.toLowerCase(file);
        this.rank = rank;
        this.arrayX = calculateX(this.rank);
        this.arrayY = calculateY(this.file);
    }

    public BoardPosition(int arrayX, int arrayY) {
        this.arrayX = arrayX;
        this.arrayY = arrayY;
        this.file = calculateFile(arrayY);
        this.rank = calculateRank(arrayX);
    }

    private char calculateFile(int arrayY) {
        switch (arrayY) {
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

    private char calculateRank(int arrayX) {
        int decRank = (X_OFFSET - arrayX);
        return (char) (decRank + 48);
    }

    private int calculateX(char rank) {
        return Math.abs(Character.getNumericValue(rank) - X_OFFSET);
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

    @Override
    public String toString() {
        return "BoardPosition [" +
                "file=" + file +
                ", rank=" + rank +
                ", arrayX=" + arrayX +
                ", arrayY=" + arrayY +
                ']';
    }

}